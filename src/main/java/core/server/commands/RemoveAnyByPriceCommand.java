package core.server.commands;

import core.AbstractCommand;
import core.SerializationHelper;
import core.packet.CommandContext;
import core.packet.InfoPack;
import core.pojos.Ticket;
import core.server.ServerCommandManager;
import core.server.ValidationException;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class providing removing by price command
 */
public class RemoveAnyByPriceCommand extends AbstractCommand<ServerCommandManager> {

    public RemoveAnyByPriceCommand(ServerCommandManager manager) {
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
                if(manager.getCollection().isEmpty()){
                    builder.append("Collection is empty");
                }else{
                    manager.getCollection().stream().filter((x) -> x.getPrice() == Integer.parseInt(arguments[0])).findFirst().ifPresent((x) ->{
                        manager.getCollection().remove(x);
                        builder.append("deleted");
                    });
                    builder.append(" done");
                }
            }catch (NumberFormatException e){
                builder.append("Wrong argument");
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
        return "Remove element, where price is equals arg";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("remove_any_by_price");
    }
}
