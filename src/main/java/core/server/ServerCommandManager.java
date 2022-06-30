package core.server;

import core.AbstractCommandManager;
import core.Command;
import core.packet.CommandContextPack;
import core.packet.UpdatePack;
import core.pojos.TicketWrap;
import core.pojos.UserClient;
import core.server.database.*;
import util.SerializationHelper;

import java.io.*;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.UnresolvedAddressException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.logging.Logger;

public final class ServerCommandManager extends AbstractCommandManager implements AutoCloseable {

    private final Logger logger = Logger.getLogger(ServerCommandManager.class.getName());

    private final DatagramChannel channel;
    private final EncapsulatedCollection collection;
    private final Map<Class<?>, DAO<?>> daos = new ConcurrentHashMap<>();
    private TicketDAO ticketDAO;
    private UserDAO userDAO;
    private Sequence seq;
    private final Set<SocketAddress> addressList = new HashSet<>();


    public ServerCommandManager(EncapsulatedCollection collection, DatagramChannel socket, TicketDAO ticketDAO, UserDAO userDAO, Sequence generatorSeq){
        this.collection = collection;
        this.channel = socket;
        this.ticketDAO = ticketDAO;
        this.userDAO = userDAO;
        this.seq = generatorSeq;
    }


    public ServerCommandManager addCommand(Function<ServerCommandManager, Command> command){
        addCommand(command.apply(this));
        return this;
    }

    public void handle(String str) throws IOException{
        throw new IOException("Cannot handle just string");
    }

    public void handle(CommandContextPack context) throws IOException{
        Command now = commandAliases.get(context.getCommandPeek());
        if(now != null){
            if(now.externalInfo().elementRequire){
                System.out.println(context.getElement().toString());
            }
            now.handle(context.getArgs(), context);
            if(now.isModifiable()){
                UpdatePack updatePack = new UpdatePack();
                updatePack.listOfTickets.addAll(collection);
                try(SerializationHelper serializationHelper = new SerializationHelper()) {
                    ByteBuffer byteBuffer = serializationHelper.serialize(updatePack);
                    for (SocketAddress addr: addressList) {
                        try {
                            channel.send(byteBuffer, addr);
                            byteBuffer.flip();
                        }catch (UnresolvedAddressException e){
                            logger.warning(e.getMessage() + " " + addr);
                        }

                    }
                }
            }
        }
    }

    public DatagramChannel getChannel() {
        return channel;
    }

    public EncapsulatedCollection getCollection() {
        return collection;
    }

    @Deprecated
    public <T> void addDao(Class<T> daoClass, DAO<T> dao){
        daos.put(daoClass, dao);
    }

    public TicketDAO getTicketDAO() {
        return ticketDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public Sequence getSeq() {
        return seq;
    }

    @Override
    public void close(){
        if (channel != null){
            try {
                channel.close();
            }catch (IOException e){
                logger.warning(e.getMessage());
            }
        }
    }

    public Set<SocketAddress> getAddressList() {
        return addressList;
    }
}
