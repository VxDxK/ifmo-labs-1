package core.guiclient.commands;

import core.LocalizedHelp;
import core.packet.InfoPack;

import java.io.IOException;
import java.util.Locale;

public interface GuiCommand {
    InfoPack handle(String[] args) throws IOException;
    String getHelp();
    String getName();
    default LocalizedHelp getHelpPack(){
        LocalizedHelp localizedHelp = new LocalizedHelp();
        localizedHelp.addHelp(Locale.ENGLISH, getHelp());
        return localizedHelp;
    }
}
