package apps;

import core.server.commands.SignCommand;
import core.pojos.*;
import core.server.commands.api.GetCommandsCommand;
import core.server.commands.api.GetElements;
import core.server.commands.api.ServerUpdateCommand;
import core.server.database.*;
import util.*;
import core.packet.CommandContextPack;
import core.server.EncapsulatedCollection;
import core.server.ServerCommandManager;
import core.server.commands.*;

import java.io.IOException;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * Server app for 7th lab
 * var 3142
 */
public class Server implements Runnable{
    private final EncapsulatedCollection collection = new EncapsulatedCollection();
    private final static Logger logger = Logger.getLogger(Server.class.getName());

    public Server(){
    }

    @Override
    public void run() {

        SocketAddress address = null;

        try {
            address = AddressValidator.getAddress(System.getProperty("hostAddress"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        Config config = new Config(Paths.get("config.properties"));
        System.out.println(config);
        try (ServerDataSource serverDataSource = new ServerDataSource(config);
             UserDAOImpl userServerDAO = new UserDAOImpl(serverDataSource);
             TicketDAOImpl ticketDAOImpl = new TicketDAOImpl(serverDataSource, userServerDAO);
             IDGeneratorSeq sequence = new IDGeneratorSeq(serverDataSource);
             DatagramChannel channel = DatagramChannel.open();
             SerializationHelper serializationHelper = new SerializationHelper();
             ServerCommandManager commandManager = new ServerCommandManager(collection, channel, ticketDAOImpl, userServerDAO, sequence)){


            ticketDAOImpl.createSchema();
            userServerDAO.createSchema();
            sequence.createSchema();

            channel.configureBlocking(false);
            channel.bind(address);
            System.out.println("Server running on: " + address);

            commandManager
                    .addCommand(AddCommand::new)
                    .addCommand(AddIfMaxCommand::new)
                    .addCommand(AddIfMinCommand::new)
                    .addCommand(ClearCommand::new)
                    .addCommand(InfoCommand::new)
                    .addCommand(MaxByDiscountCommand::new)
                    .addCommand(PrintFieldAscendingComment::new)
                    .addCommand(RemoveAnyByPriceCommand::new)
                    .addCommand(RemoveById::new)
                    .addCommand(RemoveHeadCommand::new)
                    .addCommand(ShowCommand::new)
                    .addCommand(ServerUpdateCommand::new)
                    .addCommand(GetCommandsCommand::new)
                    .addCommand(SignCommand::new)
                    .addCommand(GetElements::new);

            new Stopper(Thread.currentThread(), System.in).start();

            ExecutorService service = Executors.newFixedThreadPool(5);
            ByteBuffer fromClient = ByteBuffer.allocate(2048);
            loadElements(ticketDAOImpl);

            while (!Thread.currentThread().isInterrupted()){

                SocketAddress clientAddress = channel.receive(fromClient);

                if(clientAddress != null){
                    logger.info("new message");
                    fromClient.flip();
                    DeserializationHelper deserializationHelper = new DeserializationHelper();
                    Object received = deserializationHelper.deSerialization(fromClient);
                    if(received instanceof CommandContextPack){
                        CommandContextPack context = (CommandContextPack) received;
                        logger.info(context.toString());
                        context.setSocketAddress(clientAddress);
                        service.execute(() ->{
                            try {
                                commandManager.handle(context);
                            } catch (IOException e) {
                                logger.severe(e.getMessage());
                            }
                        });
                    }
                    fromClient.clear();
                }

            }

        } catch (SocketException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }catch (IOException e){
            logger.warning(e.getMessage());
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
    }

    private void loadElements(TicketDAO dao){
        List<TicketWrap> allTickets = dao.all();
        synchronized (collection){
            collection.addAll(allTickets);
        }
        System.out.println("Loaded");
    }

}
