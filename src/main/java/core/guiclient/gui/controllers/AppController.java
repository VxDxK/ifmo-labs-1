package core.guiclient.gui.controllers;

import core.guiclient.GuiCommandManager;
import core.guiclient.gui.AppWindow;
import core.guiclient.gui.CoordinatePlane;
import core.guiclient.gui.locales.loc;
import core.packet.InfoPack;
import core.packet.LoginPack;
import core.packet.UpdatePack;
import core.pojos.Color;
import core.pojos.Ticket;
import core.pojos.TicketWrap;
import core.server.database.EnumAdapter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class AppController implements Controller{
    private ResourceBundle res = ResourceBundle.getBundle(loc.class.getName(), Locale.getDefault());
    private final AppWindow appWindow;
    private final GuiCommandManager commandManager;
    public AppController(AppWindow appWindow, GuiCommandManager commandManager) {
        this.appWindow = appWindow;
        this.commandManager = commandManager;
    }


    @Override
    public void initView() {
        appWindow.getMainView().showAppWindow();
        commandManager.getServerCommands()
                .entrySet().stream()
                .filter((x) -> x.getValue().neededInGui)
                .forEach(x -> appWindow.getCommandsComboBox().addItem(x.getKey()));
        initController();
        updateLoginStat();
    }

    @Override
    public void initController() {
        appWindow.getDefaultTableModel().setRowCount(0);
        appWindow.getExec().addActionListener(e -> {runCommand();});
        appWindow.getCommandsComboBox().addActionListener(e -> {updateHelp();});
    }

    public void runCommand(){
        try {
            InfoPack str = commandManager.handle((String) appWindow.getCommandsComboBox().getSelectedItem(), appWindow.getTextField().getText().trim().split(" "));
            appWindow.getTextField().setText("");
            if(!str.getString().equals("")){
                if(str.getString().length() <= 100){
                    JOptionPane.showMessageDialog(null, str.getString());
                }else{
                    JFrame fr = new JFrame(){
                        {
                            add(new JLabel("<html><p style=\"width:480px\">"+str.getString()+"</p></html>"));
                        }
                    };
                    fr.setMinimumSize(new Dimension(800, 500));
                    fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    fr.setTitle("Message");
                    fr.setVisible(true);
                }

            }
            if(str instanceof UpdatePack){
                updateTable();
            }
            if(str instanceof LoginPack){
                updateLoginStat();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), res.getString("err"), JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateTable(){
        appWindow.getDefaultTableModel().setRowCount(0);
        for (TicketWrap i: commandManager.getTicketWraps()) {
            Ticket ticketNow = i.getTicket();
            EnumAdapter<Color> enumAdapter = new EnumAdapter<>(Color.class);

            String[] row = new String[14];
            row[0] = String.valueOf(ticketNow.getId());
            row[1] = ticketNow.getName();
            row[2] = ticketNow.getCoordinates().getX().toString();
            row[3] = ticketNow.getCoordinates().getY().toString();
            row[4] = ticketNow.getCreationDate().atZone(ZoneId.systemDefault()).toString();
            row[5] = String.valueOf(ticketNow.getPrice());
            row[6] = String.valueOf(ticketNow.getDiscount());
            row[7] = ticketNow.getComment();
            row[8] = ticketNow.getType().toString();
            row[9] = String.valueOf(ticketNow.getPerson().getWeight());
            row[10] = enumAdapter.transForm(ticketNow.getPerson().getEyeColor());
            row[11] = enumAdapter.transForm(ticketNow.getPerson().getHairColor());
            row[12] = ticketNow.getPerson().getNationality().toString();
            row[13] = i.getUser().getLogin();

            appWindow.getDefaultTableModel().insertRow(appWindow.getDefaultTableModel().getRowCount(), row);
        }

    }

    public AppWindow getAppWindow() {
        return appWindow;
    }

    public GuiCommandManager getCommandManager() {
        return commandManager;
    }

    public void updateCollection(List<TicketWrap> ticketWrapList){
        commandManager.newList(ticketWrapList);
        updateTable();
        updatePlane();
    }

    public void updatePlane(){
        appWindow.getCoordinatePlane().removeAll();
        appWindow.revalidate();
        appWindow.repaint();
        CoordinatePlane coordinatePlane = new CoordinatePlane(commandManager.getTicketWraps(), commandManager);
        coordinatePlane.setVisible(true);
        appWindow.getCoordinatePlane().add(coordinatePlane);
    }

    public void updateLoginStat(){
        res = ResourceBundle.getBundle(loc.class.getName(), appWindow.getMainView().getLoc());
        if(commandManager.getClient() == null){
            appWindow.getUserLabel().setForeground(java.awt.Color.BLACK);
            appWindow.getUserLabel().setText(res.getString("not_log"));
        }else{
            appWindow.getUserLabel().setForeground(new java.awt.Color(commandManager.getClient().hashCode()));
            appWindow.getUserLabel().setText(res.getString("logged_as") + commandManager.getClient().getLogin());
        }
    }

    private void updateHelp(){
        appWindow.getHelpLabel().setText(commandManager.getHelpMap().get((String) appWindow.getCommandsComboBox().getSelectedItem()).getHelp(appWindow.getMainView().getLoc()));
    }

}
