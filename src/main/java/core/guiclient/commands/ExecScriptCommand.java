package core.guiclient.commands;

import core.LocalizedHelp;
import core.guiclient.GuiCommandManager;
import core.packet.InfoPack;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Locale;

public class ExecScriptCommand extends GuiCommandImpl {
    public ExecScriptCommand(GuiCommandManager commandManager) {
        super(commandManager);
    }

    @Override
    public InfoPack handle(String[] args) throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        int ret = fileChooser.showDialog(null, "Choose file");

        if(ret == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
            Path path = file.toPath();

        }else{
            return new InfoPack("");
        }

        return new InfoPack("Exec script");
    }

    @Override
    public String getHelp() {
        return "Execute script";
    }

    @Override
    public String getName() {
        return "execute script";
    }

    @Override
    public LocalizedHelp getHelpPack() {
        return super.getHelpPack()
                .addHelp(new Locale("ru"), "Выполняет скрипт")
                .addHelp(new Locale("no"), "Utfører skriptet")
                .addHelp(new Locale("hu"), "A szkript végrehajtása");
    }
}
