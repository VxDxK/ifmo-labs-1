package core.client;

import core.AbstractCommandManager;
import core.Command;
import core.DeserializationHelper;
import core.SerializationHelper;
import core.packet.CommandContext;
import core.packet.InfoPack;
import core.pojos.Ticket;
import core.readers.TicketReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;

public class ClientCommandManager extends AbstractCommandManager implements AutoCloseable{

    private final Logger logger = Logger.getLogger(ClientCommandManager.class.getName());

    private BufferedReader reader;
    private String docs;
    private Map<String, Boolean> serverCommands;
    private DatagramChannel channel;
    private SocketAddress address;
    private ByteBuffer inputBuffer;
    public ClientCommandManager(BufferedReader reader, SocketAddress address, ByteBuffer serverBuff) {
        this.reader = reader;
        try {
            this.channel = DatagramChannel.open();
            channel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.address = address;
        this.inputBuffer = ByteBuffer.allocate(1024 * 16);
    }

    @Override
    public void handle(String str) throws IOException {
        String[] splitted = str.trim().split(" ");
        if(serverCommands.get(splitted[0]) != null){
            CommandContext context = new CommandContext()
                    .setCommandPeek(splitted[0]).
                    setArgs(Arrays.copyOfRange(splitted, 1, splitted.length));
            boolean isElReq = serverCommands.get(splitted[0]);
            if(isElReq){
                TicketReader ticketReader = new TicketReader();
                Ticket.TicketBuilder element = ticketReader.read(reader, new OutputStreamWriter(System.out));
                context.setElement(element);
            }

            try(SerializationHelper serializationHelper = new SerializationHelper()){
                ByteBuffer byteBuffer = serializationHelper.serialize(context);
                assert channel != null;
                assert byteBuffer != null;
                assert address != null;

                channel.send(byteBuffer, address);
            }

            WaitAnswer answer = new WaitAnswer();
            boolean fl = answer.waitReceive(channel, inputBuffer, 5);
            if(fl){
                inputBuffer.flip();
                Object ans = DeserializationHelper.get().deSerialization(inputBuffer);
                if(ans != null){
                    if (ans instanceof InfoPack){
                        InfoPack pack = (InfoPack) ans;
                        System.out.println(pack.getString());
                    }
                }
                inputBuffer.clear();
            }else{
                System.out.println("no answer; Server can be unavailable");
            }

            return;
        }


        Command now = commandAliases.get(splitted[0].toLowerCase(Locale.ROOT));
        if(now != null){
            now.handle(Arrays.copyOfRange(splitted, 1, splitted.length), null);
        }else{
            System.out.println("No such command");
        }
    }


    public void handle(BufferedReader reader) throws IOException {
        String str = reader.readLine();
        String[] splitted = str.trim().split(" ");
        if(serverCommands.get(splitted[0]) != null){
            CommandContext context = new CommandContext()
                    .setCommandPeek(splitted[0]).
                    setArgs(Arrays.copyOfRange(splitted, 1, splitted.length));
            boolean isElReq = serverCommands.get(splitted[0]);
            if(isElReq){
                TicketReader ticketReader = new TicketReader();
                Ticket.TicketBuilder element = ticketReader.read(reader);
                context.setElement(element);
            }

            try(SerializationHelper serializationHelper = new SerializationHelper()){
                ByteBuffer byteBuffer = serializationHelper.serialize(context);
                assert channel != null;
                assert byteBuffer != null;
                assert address != null;
                channel.send(byteBuffer, address);
            }

            WaitAnswer answer = new WaitAnswer();
            boolean fl = answer.waitReceive(channel, inputBuffer, 5);
            if(fl){
                inputBuffer.flip();
                Object ans = DeserializationHelper.get().deSerialization(inputBuffer);
                if(ans != null){
                    if (ans instanceof InfoPack){
                        InfoPack pack = (InfoPack) ans;
                        System.out.println(pack.getString());
                    }
                }
                inputBuffer.clear();
            }else{
                System.out.println("no answer; Server can be unavailable");
            }

            return;
        }


        Command now = commandAliases.get(splitted[0].toLowerCase(Locale.ROOT));
        if(now != null){
            now.handle(Arrays.copyOfRange(splitted, 1, splitted.length), null);
        }else{
            System.out.println("No such command");
        }
    }

    public ClientCommandManager addCommand(Function<ClientCommandManager, Command> command){
        addCommand(command.apply(this));
        return this;
    }

    @Override
    public void close(){

    }

    public void updateHelp(String s){
        if(docs == null){
            StringBuilder builder = new StringBuilder();
            builder.append(s);
            getCommands().forEach(y -> builder.append(y.getName().concat(" : ")).append(y.getHelp().concat("\n")));
            docs = builder.toString();
        }
    }

    public String getDocs() {
        return docs;
    }

    public void setDocs(String docs) {
        this.docs = docs;
    }

    public void setServerCommands(Map<String, Boolean> serverCommands) {
        this.serverCommands = serverCommands;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public DatagramChannel getChannel() {
        return channel;
    }
}
