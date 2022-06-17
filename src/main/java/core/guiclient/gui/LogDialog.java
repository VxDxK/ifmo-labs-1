package core.guiclient.gui;

import core.guiclient.gui.locales.loc;
import core.pojos.UserClient;
import util.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class LogDialog extends JDialog {
    private final ResourceBundle res;
    private final JComboBox<String> modeComboBox = new JComboBox<>();
    private final JTextField loginField = new JTextField(40);
    private final JPasswordField passwordField = new JPasswordField(40);
    private final JButton button = new JButton("Ok");
    private final Map<String, String> logModes = new HashMap<>();
    MainView mainView;
    public LogDialog(MainView view) {
        super(view, "Login", true);
        res = ResourceBundle.getBundle(loc.class.getName(), view.getLoc());
        mainView = view;

        logModes.put(res.getString("in"), "in");
        logModes.put(res.getString("up"), "up");
        logModes.put(res.getString("out"), "out");


        modeComboBox.addItem(res.getString("in"));
        modeComboBox.addItem(res.getString("up"));
        modeComboBox.addItem(res.getString("out"));

        button.addActionListener(x -> buttonPressed());

        JPanel jPanel = new JPanel();
        GridLayout gridLayout = new GridLayout(3, 1, 5, 10);

        jPanel.setLayout(gridLayout);

        modeComboBox.setAlignmentY(CENTER_ALIGNMENT);
        loginField.setAlignmentY(CENTER_ALIGNMENT);
        passwordField.setAlignmentY(CENTER_ALIGNMENT);
        button.setAlignmentY(JPanel.CENTER_ALIGNMENT);

        modeComboBox.setMaximumSize(modeComboBox.getPreferredSize());
        passwordField.setMaximumSize(passwordField.getPreferredSize());
        loginField.setMaximumSize(loginField.getPreferredSize());
        button.setMaximumSize(button.getPreferredSize());

        jPanel.add(modeComboBox);
        jPanel.add(loginField);
        jPanel.add(passwordField);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(jPanel);
        getContentPane().add(button, BorderLayout.SOUTH);
        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width/2, screenSize.height/2);
    }

    private void buttonPressed(){
        dispose();
    }

    public Pair<String, UserClient> getValue(){
        return new Pair<>(logModes.get((String)modeComboBox.getSelectedItem()), new UserClient(loginField.getText(), passwordField.getText()));
    }

}
