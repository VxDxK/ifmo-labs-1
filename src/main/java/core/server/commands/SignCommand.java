package core.server.commands;

import core.AbstractCommand;
import core.packet.LoginPack;
import core.pojos.UserClient;
import core.server.ServerCommandManager;
import core.packet.CommandContextPack;
import core.server.database.UserDAO;
import core.server.database.UserDAOImpl;
import util.SerializationHelper;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

public class SignCommand extends AbstractCommand<ServerCommandManager> {

    public SignCommand(ServerCommandManager manager) {
        super(manager);
    }

    @Override
    public void handle(String[] arguments, CommandContextPack context) throws IOException {
        StringBuilder builder = new StringBuilder();
        LoginPack pack = new LoginPack();
        pack.setRetCode(1);

        if(arguments.length != 3){
            builder.append("IN/UP argument is required. And then login + password");
        }else{
            UserClient user = new UserClient(arguments[1], arguments[2]);
            UserDAO userDAO = manager.getUserDAO();
            if(arguments[0].equalsIgnoreCase("in")){
                UserClient.UserServer server = userDAO.getByLogin(user.getLogin());
                if(server == null){
                    builder.append("no such user");
                }else{
                    if(server.getHash().equals(user.trans().getHash())){
                        builder.append("You are welcome");
                        pack.setRetCode(0);
                        pack.setUser(server);
                    }else{
                        builder.append("Invalid password");
                    }
                }
            }else if(arguments[0].equalsIgnoreCase("up")){
                UserClient.UserServer server = userDAO.getByLogin(user.getLogin());

                if(server != null){
                    builder.append("Login zanyat");
                }else{
                    builder.append("New user added");
                    userDAO.add(user.trans());
                    pack.setRetCode(0);
                    pack.setUser(user.trans());
                }
            }else {
                builder.append("no such mode");
            }
        }
        pack.setString(builder.toString());
        try(SerializationHelper helper = new SerializationHelper()){
            ByteBuffer byteBuffer = helper.serialize(pack);
            manager.getChannel().send(byteBuffer, context.getSocketAddress());
        }
    }

    @Override
    public String getHelp() {
        return "use sign in or sign up to enter system. And sign out to exit it. Sign stat to check your user";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("sign");
    }
}
