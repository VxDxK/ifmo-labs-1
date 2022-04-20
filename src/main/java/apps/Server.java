package apps;

import core.SerializationHelper;
import core.packet.CommandContext;
import core.server.EncapsulatedCollection;
import core.server.ServerCommandManager;
import core.server.commands.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import javax.xml.bind.*;
import java.io.*;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.DatagramChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class Server implements Runnable{
    EncapsulatedCollection collection = new EncapsulatedCollection();
    private final  static Logger logger = Logger.getLogger(Server.class.getName());

    public Server(){
    }

    @Override
    public void run() {
        String fileName = System.getenv("dataFile");
        if(fileName == null){
            System.out.println("No one env variable was set");
            System.exit(-1);
        }
        Path file = Paths.get(fileName);

        if(!Files.isRegularFile(file)){
            System.out.println(file.getFileName() + " not a regular file");
            System.exit(-1);
        }

        if(!Files.isReadable(file) || !Files.isWritable(file)){
            System.out.println("File should be readable and writable");
            System.exit(-1);
        }

        try {
            JAXBContext context = JAXBContext.newInstance(EncapsulatedCollection.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            if(Files.size(file) == 0){
                collection = new EncapsulatedCollection();
            }else{
                collection = (EncapsulatedCollection) unmarshaller.unmarshal(new InputStreamReader(Files.newInputStream(file)));
                if(!collection.validate()){
                    System.out.println("Something wrong with xml model");
                    System.exit(-1);
                }
            }
        } catch (IOException | JAXBException e) {
            System.out.println("Error on data file parsing: " + e.getMessage());
            System.exit(-1);
        }

        SocketAddress address = null;

        try {
            address = getAddress(System.getProperty("hostAddress"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        ByteBuffer fromClient = ByteBuffer.allocate(2048);
        try (DatagramChannel channel = DatagramChannel.open();
             SerializationHelper serializationHelper = new SerializationHelper();
             ServerCommandManager commandManager = new ServerCommandManager(collection, file, channel);
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))){

            channel.configureBlocking(false);

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
                    .addCommand(UpdateCommand::new)
                    .addCommand(GetCommandsCommand::new);

            channel.bind(address);
            System.out.println("Server running on: " + address);

            while (!Thread.currentThread().isInterrupted()){

                SocketAddress clientAddress = channel.receive(fromClient);

                if(System.in.available() > 0){
                    String str = console.readLine();
                    if(str.equalsIgnoreCase("exit") || str.equalsIgnoreCase("save")){
                        try {
                            if(!Files.exists(file)){
                                System.out.println("File is not exists");
                                continue;
                            }
                            if(!Files.isRegularFile(file)){
                                System.out.println("File is not regular file");
                                continue;
                            }
                            if(!Files.isWritable(file)){
                                System.out.println("File is not writable");
                                continue;
                            }
                            PrintWriter fileWriter = new PrintWriter(Files.newOutputStream(file));

                            JAXBContext jaxbContext = JAXBContext.newInstance(EncapsulatedCollection.class);
                            Marshaller marshaller = jaxbContext.createMarshaller();
                            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

                            marshaller.marshal(commandManager.getCollection(), fileWriter);
                            logger.info("SYS: WAS SAVED");
                            if(str.equalsIgnoreCase("exit")){
                                System.exit(0);
                            }

                        } catch (IOException | JAXBException e) {
                            logger.severe("Error on data file operations");
                        }
                    }
                }

                if(clientAddress != null){
                    logger.info("new message");
                    fromClient.flip();
                    try(ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fromClient.array());
                        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)){

                        Object received = objectInputStream.readObject();

                        if(received instanceof CommandContext){
                            CommandContext context = (CommandContext) received;
                            logger.info(context.toString());
                            context.setSocketAddress(clientAddress);
                            commandManager.handle(context);
                        }
                    } catch (ClassNotFoundException e) {
                        logger.severe("Class cast error: " + e.getMessage());
                    }
                    fromClient.clear();
                }

            }

        } catch (SocketException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }catch (IOException e){
            logger.warning(e.getMessage());
        }

    }

    private SocketAddress getAddress(String str) throws IOException{
        if(str == null){
            return new InetSocketAddress("localhost", 8080);
        }
        String[] parsed = str.split(":");
        if(parsed.length != 2){
            throw new IOException("Address format error");
        }

        try {
            Integer.parseInt(parsed[1]);
        }catch (NumberFormatException e){
            throw new IOException("Port should be int");
        }

        if (Integer.parseInt(parsed[1]) < 0 || Integer.parseInt(parsed[1]) > 0xFFFF)
            throw new IOException("port out of range:" + Integer.parseInt(parsed[1]));

        return new InetSocketAddress(parsed[0], Integer.parseInt(parsed[1]));
    }

}
