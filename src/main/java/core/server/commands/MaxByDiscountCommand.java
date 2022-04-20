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
import java.util.Optional;

/**
 * Class providing command with ticket max by discount
 */
public class MaxByDiscountCommand extends AbstractCommand<ServerCommandManager> {

    public MaxByDiscountCommand(ServerCommandManager manager) {
        super(manager);
    }

    @Override
    public void handle(String[] arguments, CommandContext context) throws IOException {

        InfoPack pack = new InfoPack();

        StringBuilder builder = new StringBuilder();

        if(manager.getCollection().isEmpty()){
            builder.append("Collection is empty");
        }else{
            try {
                Optional<Integer> discount = manager.getCollection().stream().map(Ticket::getDiscount).max(Integer::compareTo);
                discount.ifPresent((x) ->{
                    builder.append("Max discount: ").append(x);
                });
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
        return "Returns max by discount";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("max_by_discount");
    }
}
