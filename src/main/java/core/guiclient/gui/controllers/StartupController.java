package core.guiclient.gui.controllers;

import core.guiclient.GuiCommandManager;
import core.guiclient.UpdateListener;
import core.guiclient.commands.ChangeLocCommand;
import core.guiclient.gui.AppWindow;
import core.guiclient.gui.StartupPanel;
import core.guiclient.commands.ExecScriptCommand;
import core.guiclient.commands.LogCommand;
import core.guiclient.commands.UpdateCommand;
import core.guiclient.gui.locales.loc;
import core.packet.InfoPack;
import util.AddressValidator;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.Locale;
import java.util.ResourceBundle;

public class StartupController implements Controller {
    private final ResourceBundle res = ResourceBundle.getBundle(loc.class.getName(), Locale.getDefault());
    private final StartupPanel startupPanel;

    public StartupController(StartupPanel startupPanel) {
        this.startupPanel = startupPanel;
    }

    @Override
    public void initView(){
        startupPanel.getMainWindow().showStartupPanel();
        initController();
    }

    @Override
    public void initController(){
        startupPanel.getButton().addActionListener(e -> tryLogin());
    }

    public void tryLogin(){
        try {
            SocketAddress socketAddress = AddressValidator.getAddress("127.0.0.1:8080");
            if(!startupPanel.getAddrTextField().getText().equals("")){
                socketAddress = AddressValidator.getAddress(startupPanel.getAddrTextField().getText());
            }


            GuiCommandManager commandManager = new GuiCommandManager(socketAddress, startupPanel.getMainWindow());
//            startupPanel.getMainWindow().getAppWindow().getCommandsComboBox().addActionListener(e -> {
//                AppWindow appWindow = startupPanel.getMainWindow().getAppWindow();
//                appWindow.getHelpLabel().setText(commandManager.getHelpMap().get((String) appWindow.getCommandsComboBox().getSelectedItem()).getHelp(appWindow.getMainView().getLoc()));
//            });

            commandManager
                    .addCommand(LogCommand::new)
                    .addCommand(ExecScriptCommand::new)
                    .addCommand(UpdateCommand::new)
                    .addCommand(ChangeLocCommand::new);

            InfoPack pack = commandManager.handle("get_unique_commands", new String[]{});
            JOptionPane.showMessageDialog(null, String.format(res.getString("loaded"), commandManager.getServerCommands().size(), socketAddress.toString()));

            AppController controller = new AppController(startupPanel.getMainWindow().getAppWindow(), commandManager);


            DatagramChannel datagramChannel = DatagramChannel.open();
            datagramChannel.configureBlocking(false);
            UpdateListener updateListener = new UpdateListener(controller, datagramChannel, socketAddress);
            updateListener.start();
            Runtime.getRuntime().addShutdownHook(new Thread(updateListener::sendOutMessage));
            startupPanel.getMainWindow().addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {

                }

                @Override
                public void windowClosing(WindowEvent e) {
                    updateListener.sendOutMessage();
                }

                @Override
                public void windowClosed(WindowEvent e) {
                    updateListener.sendOutMessage();
                }

                @Override
                public void windowIconified(WindowEvent e) {

                }

                @Override
                public void windowDeiconified(WindowEvent e) {

                }

                @Override
                public void windowActivated(WindowEvent e) {

                }

                @Override
                public void windowDeactivated(WindowEvent e) {

                }
            });
            controller.initView();
        }catch (IOException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }catch (Exception e){
            System.out.println("Internal exception: " + e.getMessage());
        }
    }

}
