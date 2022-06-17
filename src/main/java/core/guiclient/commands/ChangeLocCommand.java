package core.guiclient.commands;

import core.LocalizedHelp;
import core.guiclient.GuiCommandManager;
import core.packet.InfoPack;
import core.packet.LoginPack;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Locale;

public class ChangeLocCommand extends GuiCommandImpl{
    public ChangeLocCommand(GuiCommandManager commandManager) {
        super(commandManager);
    }

    @Override
    public InfoPack handle(String[] args) throws IOException {
        String[] locales = {"en", "ru", "no", "hu"};
        String s = (String) JOptionPane.showInputDialog(null, "", "", JOptionPane.QUESTION_MESSAGE, null, locales, "en");
        if(s == null){
            return new InfoPack("");
        }
        commandManager.getFrame().changeLocale(new Locale(s));
        return new LoginPack();
    }

    @Override
    public String getHelp() {
        return "Changing app locale";
    }

    @Override
    public String getName() {
        return "Change locale";
    }

    @Override
    public LocalizedHelp getHelpPack() {
        return super.getHelpPack().addHelp(new Locale("ru"), "Меняет язык приложения")
                .addHelp(new Locale("no"), "Endrer applikasjonsspråket")
                .addHelp(new Locale("hu"), "Módosítja az alkalmazás nyelvét");
    }
}
