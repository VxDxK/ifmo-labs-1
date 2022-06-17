package core.guiclient;

import core.Command;
import core.client.WaitAnswer;
import core.LocalizedHelp;
import core.guiclient.gui.MainView;
import core.guiclient.gui.TicketDialog;
import core.guiclient.commands.GuiCommand;
import core.guiclient.gui.controllers.AppController;
import core.guiclient.gui.locales.loc;
import core.packet.*;
import core.pojos.Ticket;
import core.pojos.TicketWrap;
import core.pojos.UserClient;
import core.readers.TicketReader;
import util.CommandExternalInfo;
import util.DeserializationHelper;
import util.SerializationHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.*;
import java.util.function.Function;

public class GuiCommandManager {
    private ResourceBundle res;
    private final SocketAddress serverAddress;
    private final DatagramChannel channel;
    private final Map<String, CommandExternalInfo> serverCommands = new HashMap<>();
    private String docs;
    protected final Map<String, LocalizedHelp> helpMap = new HashMap<>();
    protected final List<Command> commands = new ArrayList<>();
    protected final Map<String, GuiCommand> commandAliases = new HashMap<>();

    private final List<TicketWrap> ticketWraps = new ArrayList<>();

    private final ByteBuffer inputBuffer;
    private UserClient.UserServer client = null;

    private final MainView frame;

    public GuiCommandManager(SocketAddress serverAddress, MainView mainWindow) throws IOException {
        res = ResourceBundle.getBundle(loc.class.getName(), mainWindow.getLoc());
        channel = DatagramChannel.open();
        channel.configureBlocking(false);
        this.serverAddress = serverAddress;
        serverCommands.put("get_unique_commands", new CommandExternalInfo(false, false));
        inputBuffer = ByteBuffer.allocate(1024 * 8);
        frame = mainWindow;
    }

    public InfoPack handle(String command, String[] args) throws IOException{
        return handle(command, args, null);
    }

    public InfoPack handle(String command, String[] args, Ticket.TicketBuilder builder) throws IOException{
        res = ResourceBundle.getBundle(loc.class.getName(), frame.getLoc());
        inputBuffer.clear();
        if(serverCommands.containsKey(command)){
            CommandContextPack commandContextPack = new CommandContextPack();
            boolean isLoginReq = serverCommands.get(command).needAuth;
            if(isLoginReq && client == null){
                throw new IOException(res.getString("lr"));
            }

            boolean isElementReq = serverCommands.get(command).elementRequire;

            if(isElementReq){
                if(builder == null){
                    TicketDialog ticketDialog = new TicketDialog(frame);
                    ticketDialog.setVisible(true);
                    Optional<Ticket.TicketBuilder> builderOptional = ticketDialog.getValue();
                    if(!builderOptional.isPresent()){
                        return new InfoPack("");
                    }
                    commandContextPack.setElement(builderOptional.get());
                }else{
                    commandContextPack.setElement(builder);
                }
            }


            commandContextPack.setCommandPeek(command);
            commandContextPack.setArgs(args);
            commandContextPack.setUser(client);
            try (SerializationHelper serializationHelper = new SerializationHelper()){
                ByteBuffer serializedObj = serializationHelper.serialize(commandContextPack);
                channel.send(serializedObj, serverAddress);
            } catch (IOException e) {
                e.printStackTrace();
            }
            WaitAnswer waitAnswer = new WaitAnswer();
            boolean isAlive = waitAnswer.waitReceive(channel, inputBuffer);
            if(isAlive){
                inputBuffer.flip();
                Object ans = DeserializationHelper.get().deSerialization(inputBuffer);
                if(!(ans instanceof InfoPack)){
                    return new InfoPack("Null object was returned or not info packet");
                }

                if(ans instanceof StartupPack) {
                    StartupPack startupPack = (StartupPack) ans;
                    updateHelp(startupPack.getString());
                    if(serverCommands.size() <= 1){
                        serverCommands.putAll(startupPack.getMap());
                        serverCommands.entrySet().stream().filter((x) -> x.getValue().neededInGui).forEach(x -> helpMap.put(x.getKey(), x.getValue().localizedHelp));
                    }
                    return new InfoPack(res.getString("fine_startup"));
                }else if(ans instanceof LoginPack){
                    LoginPack loginPack = (LoginPack) ans;
                    System.out.println(loginPack.getString());
                    if(loginPack.getRetCode() == 0){
                        client = loginPack.getUser();
                        return new LoginPack();
                    }
                    return new InfoPack("");
                }else if(ans instanceof UpdatePack){
                    UpdatePack updatePack = (UpdatePack)ans;
                    ticketWraps.clear();
                    ticketWraps.addAll(updatePack.getListOfTickets());
                    return updatePack;
                }
                return (InfoPack) ans;

            }else{
                if(docs == null){
                    throw new IOException(String.format(res.getString("serv_addr_down"), serverAddress));
                }
                return new InfoPack(res.getString("server_down"));
            }

        }else if(commandAliases.containsKey(command)){
            GuiCommand guiCommand = commandAliases.get(command);
            return guiCommand.handle(args);
        }
        return new InfoPack("");
    }

    public InfoPack handle(BufferedReader reader) throws IOException{
        inputBuffer.clear();
        String str = reader.readLine();
        String[] splitted = str.trim().split(" ");
        if(serverCommands.get(str) != null){
            CommandContextPack context = new CommandContextPack()
                    .setCommandPeek(splitted[0]).
                    setArgs(Arrays.copyOfRange(splitted, 1, splitted.length));

            boolean isLoginReq = serverCommands.get(splitted[0]).needAuth;
            if(isLoginReq){
                if(client == null){
                    return new InfoPack("Error in script: You should login to use this command");
                }
            }
            context.setUser(client);

            boolean isElReq = serverCommands.get(splitted[0]).elementRequire;
            if(isElReq){
                TicketReader ticketReader = new TicketReader();
                Ticket.TicketBuilder element = ticketReader.read(reader);
                context.setElement(element);
            }

            inputBuffer.clear();

            try(SerializationHelper serializationHelper = new SerializationHelper()){
                ByteBuffer byteBuffer = serializationHelper.serialize(context);
                channel.send(byteBuffer, serverAddress);
            }

            WaitAnswer answer = new WaitAnswer();
            boolean fl = answer.waitReceive(channel, inputBuffer, 5);
            if(fl){
                inputBuffer.flip();
                Object ans = DeserializationHelper.get().deSerialization(inputBuffer);
                if(ans != null){
                    if (ans instanceof InfoPack){
                        if(ans instanceof StartupPack){
                            StartupPack pack = (StartupPack) ans;
                            updateHelp(pack.getString());
                            serverCommands.putAll(pack.getMap());
                        }else if(ans instanceof LoginPack){
                            LoginPack pack = (LoginPack) ans;
                            System.out.println(pack.getString());
                            if(pack.getRetCode() == 0){
                                client = pack.getUser();
                            }
                        }else if(ans instanceof UpdatePack){
                            UpdatePack updatePack = (UpdatePack)ans;
                            ticketWraps.clear();
                            ticketWraps.addAll(updatePack.getListOfTickets());
                            return updatePack;
                        }else{
                            return (InfoPack) ans;
                        }
                    }
                }
            }else{
                return new InfoPack("no answer; Server can be unavailable");
            }
        }else{
            GuiCommand now = commandAliases.get(splitted[0].toLowerCase(Locale.ROOT));
            if(now != null){
                return now.handle(Arrays.copyOfRange(splitted, 1, splitted.length));
            }else{
                return new InfoPack("Error in script: no such command " + splitted[0]);
            }
        }
        return new InfoPack("Script executed");
    }

    public GuiCommandManager addCommand(Function<GuiCommandManager, GuiCommand> coF){
        GuiCommand command = coF.apply(this);
        commandAliases.put(command.getName(), command);
        helpMap.put(command.getName(), command.getHelpPack());
        frame.getAppWindow().getCommandsComboBox().addItem(command.getName());
        return this;
    }


    public void updateHelp(String s){
        if(docs == null){
            StringBuilder builder = new StringBuilder();
            builder.append(s);
            getCommands().forEach(y -> builder.append(y.getName().concat(" : ")).append(y.getHelp().concat("\n")));
            docs = builder.toString();
        }
    }

    public List<Command> getCommands() {
        return commands;
    }

    public Map<String, CommandExternalInfo> getServerCommands() {
        return serverCommands;
    }

    public List<TicketWrap> getTicketWraps() {
        return ticketWraps;
    }

    public SocketAddress getServerAddress() {
        return serverAddress;
    }

    public DatagramChannel getChannel() {
        return channel;
    }

    public String getDocs() {
        return docs;
    }

    public Map<String, GuiCommand> getCommandAliases() {
        return commandAliases;
    }

    public ByteBuffer getInputBuffer() {
        return inputBuffer;
    }

    public void newList(List<TicketWrap> newList){
        ticketWraps.clear();
        ticketWraps.addAll(newList);
    }

    public Map<String, LocalizedHelp> getHelpMap() {
        return helpMap;
    }

    public MainView getFrame() {
        return frame;
    }

    public void unLogin(){
        client = null;
    }

    public UserClient.UserServer getClient() {
        return client;
    }
}
