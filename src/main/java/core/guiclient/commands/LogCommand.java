package core.guiclient.commands;

import core.LocalizedHelp;
import core.guiclient.GuiCommandManager;
import core.guiclient.commands.GuiCommandImpl;
import core.guiclient.gui.LogDialog;
import core.packet.InfoPack;
import core.packet.LoginPack;
import core.pojos.UserClient;
import util.Pair;

import java.io.IOException;
import java.util.Locale;

public class LogCommand extends GuiCommandImpl {
    public LogCommand(GuiCommandManager commandManager) {
        super(commandManager);
    }

    @Override
    public InfoPack handle(String[] args) throws IOException {
        LogDialog logDialog = new LogDialog(commandManager.getFrame());
        logDialog.setVisible(true);
        Pair<String, UserClient> userClientPair = logDialog.getValue();
        if(!userClientPair.first.equals("out") && !userClientPair.second.getLogin().equals("")){
            return commandManager.handle("sign", new String[]{userClientPair.first, userClientPair.second.getLogin(), userClientPair.second.getPassword()});
        }else{
            commandManager.unLogin();
            return new LoginPack();
        }
    }

    @Override
    public String getHelp() {
        return "Helps us log in, up, out etc";
    }

    @Override
    public String getName() {
        return "log";
    }

    @Override
    public LocalizedHelp getHelpPack() {
        return super.getHelpPack()
                .addHelp(new Locale("ru"), "Команда для логина, выхода и прочего")
                .addHelp(new Locale("no"), "Kommandoen for innlogging, utlogging og andre ting")
                .addHelp(new Locale("hu"), "A Bejelentkezés, Kijelentkezés és egyéb dolgok parancsa");
    }

}
