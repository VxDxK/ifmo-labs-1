package apps;

import com.sun.java.swing.plaf.gtk.GTKLookAndFeel;
import com.sun.java.swing.plaf.motif.MotifLookAndFeel;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import core.guiclient.gui.MainView;
import core.guiclient.gui.controllers.StartupController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;

/**
 * GUI client app for 8th lab
 * var 3156
 */
public class GUI implements Runnable{
    @Override
    public void run() {
        MainView mainWindow = new MainView();
        mainWindow.setVisible(true);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        mainWindow.setMinimumSize(new Dimension(500, 350));
        mainWindow.setLocation(screenSize.width/2 - mainWindow.getMinimumSize().width/2, screenSize.height/2 - mainWindow.getMinimumSize().height/2);
        mainWindow.setTitle("VxDxK(aka Vadim Ponomarev) lab8");
        mainWindow.setIconImage(new ImageIcon("src/main/resources/lab8.png").getImage());


        StartupController startupController = new StartupController(mainWindow.getStartupPanel());
        startupController.initView();
    }
}
