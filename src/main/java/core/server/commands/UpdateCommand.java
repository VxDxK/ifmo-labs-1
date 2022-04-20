package core.server.commands;

import core.AbstractCommand;
import core.SerializationHelper;
import core.packet.CommandContext;
import core.packet.InfoPack;
import core.server.ServerCommandManager;
import core.server.ValidationException;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
/**
 * Class providing update command
 */
public class UpdateCommand extends AbstractCommand<ServerCommandManager> {

    public UpdateCommand(ServerCommandManager manager) {
        super(manager);
    }
    @Override
    public void handle(String[] arguments, CommandContext context) throws IOException {
        InfoPack pack = new InfoPack();

        StringBuilder builder = new StringBuilder();

        if(arguments.length == 0){
            builder.append("need argument");
        }else{
            try {
                if(manager.getCollection().getById(Integer.parseInt(arguments[0])) == null){
                    builder.append("no such element");
                }else{
                    manager.getCollection().remove(manager.getCollection().getById(Integer.parseInt(arguments[0])));

                    manager.getCollection().add(context.getElement());
                    builder.append("update");
                }

            }catch (NumberFormatException e){
                builder.append("Wrong argument");
            } catch (ValidationException e) {
                builder.append("invalid");
            }
        }

        try(SerializationHelper serializationHelper = new SerializationHelper()) {
            pack.setString(builder.toString());
            ByteBuffer byteBuffer = serializationHelper.serialize(pack);
            System.out.println(Arrays.toString(byteBuffer.array()));
            manager.getChannel().send(byteBuffer, context.getSocketAddress());
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getHelp() {
        return "Update element by specifier";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("update", "u");
    }

    @Override
    public boolean elementRequire() {
        return true;
    }
}
