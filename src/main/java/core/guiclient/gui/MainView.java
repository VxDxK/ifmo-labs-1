package core.guiclient.gui;

import javax.swing.*;
import java.util.Locale;

public class MainView extends JFrame {
    private final StartupPanel startupPanel = new StartupPanel(this);
    private final AppWindow appWindow = new AppWindow(this);
    private Locale locale = Locale.getDefault();
    public MainView(){

    }

    public StartupPanel getStartupPanel() {
        return startupPanel;
    }

    public void showStartupPanel(){
        hideAppWindow();

        this.add(startupPanel);
        this.validate();
    }

    public void hideStartupPanel(){
        this.remove(startupPanel);
        this.repaint();
    }

    public void showAppWindow(){
        hideStartupPanel();

        this.add(appWindow);
        this.validate();
    }

    public void hideAppWindow(){
        this.remove(appWindow);
        this.repaint();
    }

    public AppWindow getAppWindow() {
        return appWindow;
    }

    public void changeLocale(Locale loc){
        if(loc == null)
            return;
        appWindow.changeLocale(loc);
        locale = loc;
    }
    public Locale getLoc() {
        return locale;
    }
}