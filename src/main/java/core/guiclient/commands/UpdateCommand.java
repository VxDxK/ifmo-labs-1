package core.guiclient.commands;

import core.LocalizedHelp;
import core.guiclient.GuiCommandManager;
import core.guiclient.commands.GuiCommandImpl;
import core.guiclient.gui.locales.loc;
import core.packet.InfoPack;
import core.pojos.TicketWrap;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class UpdateCommand extends GuiCommandImpl {
    private final ResourceBundle res = ResourceBundle.getBundle(loc.class.getName(), Locale.getDefault());
    public UpdateCommand(GuiCommandManager commandManager) {
        super(commandManager);
    }

    @Override
    public InfoPack handle(String[] args) throws IOException {
        if(commandManager.getClient() == null){
            throw new IOException(res.getString("lr"));
        }
        if(args.length < 1){
            return new InfoPack("Argument, id is required");
        }
        try {
            int id = Integer.parseInt(args[0]);
            List<TicketWrap> ticketWrapList = commandManager.getTicketWraps();
            Optional<TicketWrap> wrap = ticketWrapList.stream().filter(x -> x.getTicket().getId() == id).findFirst();
            if(!wrap.isPresent()){
                throw new IOException("No such id");
            }
            TicketWrap ticketWrap = wrap.get();
            if(!ticketWrap.getUser().equals(commandManager.getClient())){
                throw new IOException("That`s not your element");
            }
            return commandManager.handle("api_update", new String[]{args[0]});
        }catch (NumberFormatException e){
            throw new IOException("Argument not a string");
        }
    }

    @Override
    public String getHelp() {
        return "Updates the element by id";
    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public LocalizedHelp getHelpPack() {
        return super.getHelpPack()
                .addHelp(new Locale("ru"), "Обновляет элемент")
                .addHelp(new Locale("no"), "oppdater element")
                .addHelp(new Locale("hu"), "elem frissítése");
    }

}
