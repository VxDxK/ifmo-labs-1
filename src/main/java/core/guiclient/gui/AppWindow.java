package core.guiclient.gui;

import core.guiclient.gui.locales.loc;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.*;

public class AppWindow extends JPanel implements ItemListener {
    private ResourceBundle res = ResourceBundle.getBundle(loc.class.getName(), Locale.getDefault());
    private final JLabel userLabel = new JLabel();
    private final JComboBox<String> commandsComboBox = new JComboBox<>();
    private final JLabel helpLabel = new JLabel();
    private final JButton exec = new JButton(res.getString("exec"));

    private final JTextField textField = new JTextField(40);

    private final DefaultTableModel defaultTableModel = new DefaultTableModel();
    private final JTable table = new JTable(defaultTableModel);

    private final JPanel cards;

    Map<String, String> cardNames = new HashMap<>();

    private final MainView mainView;

    private final JPanel coordinatePlane = new JPanel();
    JComboBox<String> combobox = new JComboBox<>(
            new String[] {res.getString("mode_exec"), res.getString("mode_table"), res.getString("mode_plane")});

    public AppWindow(MainView mainView) {
        this.mainView = mainView;


        combobox.addItemListener(this);

        cardNames.put(res.getString("mode_exec"), "mode_exec");
        cardNames.put(res.getString("mode_table"), "mode_table");
        cardNames.put(res.getString("mode_plane"), "mode_plane");


        defaultTableModel.addColumn("id");
        defaultTableModel.addColumn("name");
        defaultTableModel.addColumn("x");
        defaultTableModel.addColumn("y");
        defaultTableModel.addColumn("creationDate");
        defaultTableModel.addColumn("price");
        defaultTableModel.addColumn("discount");
        defaultTableModel.addColumn("comment");
        defaultTableModel.addColumn("type");
        defaultTableModel.addColumn("weight");
        defaultTableModel.addColumn("eyecolor");
        defaultTableModel.addColumn("haircolor");
        defaultTableModel.addColumn("nationality");
        defaultTableModel.addColumn("owner");


        TableRowSorter<TableModel> sorter = new TableRowSorter<>(defaultTableModel);
        table.setRowSorter(sorter);
        sorter.setComparator(0, Comparator.comparingInt(x -> Integer.parseInt((String) x)));
        sorter.setComparator(2, Comparator.comparingDouble(x -> Float.parseFloat((String) x)));
        sorter.setComparator(3, Comparator.comparingDouble(x -> Double.parseDouble((String) x)));
        sorter.setComparator(5, Comparator.comparingDouble(x -> Integer.parseInt((String) x)));
        sorter.setComparator(6, Comparator.comparingDouble(x -> Integer.parseInt((String) x)));
        sorter.setComparator(9, Comparator.comparingDouble(x -> Float.parseFloat((String) x)));

        JPanel card1 = new JPanel();
        commandsComboBox.setMaximumSize(new Dimension(250, 25));
        textField.setMaximumSize(new Dimension(textField.getPreferredSize().width, textField.getPreferredSize().height + 6));
        userLabel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        commandsComboBox.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        exec.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        textField.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        helpLabel.setAlignmentX(JPanel.CENTER_ALIGNMENT);


        card1.setLayout(new BoxLayout(card1, BoxLayout.Y_AXIS));
        card1.add(userLabel);
        card1.add(commandsComboBox);
        card1.add(exec);
        card1.add(textField);
        card1.add(helpLabel);


        JPanel card2 = new JPanel();
        card2.setLayout(new BorderLayout(10, 10));
        card2.add(new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS));

        coordinatePlane.setLayout(new BorderLayout());

        cards = new JPanel(new CardLayout());
        cards.add(card1, "mode_exec");
        cards.add(card2, "mode_table");
        cards.add(coordinatePlane, "mode_plane");

        setLayout(new BorderLayout());

        add(combobox, BorderLayout.NORTH);
        add(cards);

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        CardLayout layout = (CardLayout) (cards.getLayout());
        layout.show(cards, cardNames.get((String) e.getItem()));
    }

    public JComboBox<String> getCommandsComboBox() {
        return commandsComboBox;
    }

    public JButton getExec() {
        return exec;
    }

    public DefaultTableModel getDefaultTableModel() {
        return defaultTableModel;
    }

    public JTable getTable() {
        return table;
    }

    public JPanel getCards() {
        return cards;
    }

    public MainView getMainView() {
        return mainView;
    }

    public JTextField getTextField() {
        return textField;
    }

    public JLabel getUserLabel() {
        return userLabel;
    }

    public JLabel getHelpLabel() {
        return helpLabel;
    }

    public JPanel getCoordinatePlane() {
        return coordinatePlane;
    }

    public void changeLocale(Locale locale){
        if(locale.equals("en")){
            res = ResourceBundle.getBundle(loc.class.getName());
        }else{
            res = ResourceBundle.getBundle(loc.class.getName(), locale);
        }
        setNames();
    }



    public void setNames(){
        exec.setText(res.getString("exec"));

        combobox.removeAllItems();
        combobox.addItem(res.getString("mode_exec"));
        combobox.addItem(res.getString("mode_table"));
        combobox.addItem(res.getString("mode_plane"));
        cardNames.put(res.getString("mode_exec"), "mode_exec");
        cardNames.put(res.getString("mode_table"), "mode_table");
        cardNames.put(res.getString("mode_plane"), "mode_plane");

        commandsComboBox.setSelectedItem("log");

    }

}
