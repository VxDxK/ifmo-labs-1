package core.server.commands;

import core.AbstractCommand;
import core.pojos.TicketWrap;
import util.SerializationHelper;
import core.packet.CommandContextPack;
import core.packet.InfoPack;
import core.pojos.Ticket;
import core.server.ServerCommandManager;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Comparator;
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
    public void handle(String[] arguments, CommandContextPack context) throws IOException {

        InfoPack pack = new InfoPack();

        StringBuilder builder = new StringBuilder();

        if(manager.getCollection().isEmpty()){
            builder.append("Collection is empty");
        }else{
            try {
                Optional<TicketWrap> discount = manager.getCollection().stream().max(Comparator.comparingInt(x -> x.getTicket().getDiscount()));
                discount.ifPresent((x) ->{
                    builder.append("Max by discount: ").append(x.getTicket().toString());
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
