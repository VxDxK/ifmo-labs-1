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
 * Class providing add operation
 */
public class AddCommand extends AbstractCommand<ServerCommandManager> {

    public AddCommand(ServerCommandManager manager) {
        super(manager);
    }

    @Override
    public void handle(String[] arguments, CommandContext context) throws IOException {
        InfoPack pack = new InfoPack();

        StringBuilder builder = new StringBuilder();

        if(elementRequire() && context.getElement() == null){
            builder.append("No element was given");
        }else{
            try {
                if(manager.getCollection().add(context.getElement())){
                    builder.append("Element added");
                }else{
                    builder.append("Element wasn`t added");
                }
            } catch (ValidationException e) {
                builder.append("Element is not valid");
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
        return "Adding element Ticket to collection";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("add", "a");
    }

    @Override
    public boolean elementRequire() {
        return true;
    }
}
