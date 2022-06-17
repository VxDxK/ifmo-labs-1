package core.guiclient.gui;

import core.guiclient.gui.locales.loc;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class StartupPanel extends JPanel {

    private final ResourceBundle res = ResourceBundle.getBundle(loc.class.getName(), Locale.getDefault());
    private final MainView mainWindow;
    private final JLabel label = new JLabel(res.getString("esa"));
    private final JTextField addrTextField = new JTextField("127.0.0.1:8080", 40);
    private final JButton button = new JButton(res.getString("conn"));

    private String inputted = "";


    public StartupPanel(MainView mainWindow) {
        this.mainWindow = mainWindow;

        JPanel box = new JPanel();
        box.setSize(500, 300);


        label.setAlignmentX(CENTER_ALIGNMENT);

        addrTextField.setAlignmentX(CENTER_ALIGNMENT);

        button.setAlignmentX(CENTER_ALIGNMENT);

        box.add(label);
        box.add(addrTextField);
        box.add(button);

        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        add(box);
    }


    public JTextField getAddrTextField() {
        return addrTextField;
    }

    public JButton getButton() {
        return button;
    }

    public MainView getMainWindow() {
        return mainWindow;
    }
}
