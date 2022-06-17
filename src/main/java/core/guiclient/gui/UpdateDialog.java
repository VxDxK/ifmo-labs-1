package core.guiclient.gui;

import core.guiclient.GuiCommandManager;
import core.guiclient.gui.locales.loc;
import core.pojos.*;
import core.pojos.Color;
import core.server.ValidationException;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public class UpdateDialog extends JDialog {
    private final Ticket.TicketBuilder builder;
    private final ResourceBundle res;
    JTextField nameField = new JTextField(20);
    JTextField xField = new JTextField(20);
    JTextField yField = new JTextField(20);
    JTextField priceField = new JTextField(20);
    JTextField discountField = new JTextField(20);
    JTextField commentField = new JTextField(20);
    JComboBox<TicketType> typeJComboBox = new JComboBox<>();
    JTextField weightField = new JTextField(20);
    JComboBox<Color> eyeColorJComboBox = new JComboBox<>();
    JComboBox<Color> hairColorJComboBox = new JComboBox<>();
    JComboBox<Country> countryJComboBox = new JComboBox<>();

    JButton updateButton = new JButton("OK");
    JButton removeButton;


    private final GuiCommandManager commandManager;


    public UpdateDialog(MainView frame, GuiCommandManager commandManager, TicketWrap ticketWrap) {
        super(frame, "Update dialog", true);
        res = ResourceBundle.getBundle(loc.class.getName(), frame.getLoc());
        setTitle(res.getString("upd"));
        removeButton = new JButton(res.getString("remove"));
        this.commandManager = commandManager;
        builder = new Ticket.TicketBuilder(ticketWrap.getTicket());

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width/2, screenSize.height/2);
        JPanel jPanel = new JPanel();
        GridLayout gridLayout = new GridLayout(12, 2, 5, 10);
        jPanel.setLayout(gridLayout);

        jPanel.add(new JLabel("name"));
        jPanel.add(nameField);

        jPanel.add(new JLabel("x"));
        jPanel.add(xField);

        jPanel.add(new JLabel("y"));
        jPanel.add(yField);

        jPanel.add(new JLabel("price"));
        jPanel.add(priceField);

        jPanel.add(new JLabel("discount"));
        jPanel.add(discountField);

        jPanel.add(new JLabel("comment"));
        jPanel.add(commentField);

        jPanel.add(new JLabel("ticket type"));
        jPanel.add(typeJComboBox);
        typeJComboBox.addItem(TicketType.VIP);
        typeJComboBox.addItem(TicketType.USUAL);
        typeJComboBox.addItem(TicketType.BUDGETARY);


        jPanel.add(new JLabel("weight"));
        jPanel.add(weightField);

        jPanel.add(new JLabel("eye color"));
        jPanel.add(eyeColorJComboBox);
        eyeColorJComboBox.addItem(null);
        Arrays.stream(core.pojos.Color.values()).forEach(x -> eyeColorJComboBox.addItem(x));


        jPanel.add(new JLabel("hair color"));
        jPanel.add(hairColorJComboBox);
        hairColorJComboBox.addItem(null);
        Arrays.stream(Color.values()).forEach(x -> hairColorJComboBox.addItem(x));

        jPanel.add(new JLabel("nationality"));
        jPanel.add(countryJComboBox);
        Arrays.stream(Country.values()).forEach(x -> countryJComboBox.addItem(x));

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(jPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        updateButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        removeButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        buttonPanel.add(updateButton);
        buttonPanel.add(removeButton);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        nameField.setText(builder.getName());
        xField.setText(builder.getCoordinates().getX().toString());
        yField.setText(builder.getCoordinates().getY().toString());
        priceField.setText(String.valueOf(builder.getPrice()));
        discountField.setText(String.valueOf(builder.getDiscount()));
        commentField.setText(builder.getComment());


        typeJComboBox.setSelectedItem(builder.getType());

        weightField.setText(String.valueOf(builder.getPerson().getWeight()));


        eyeColorJComboBox.setSelectedItem(builder.getPerson().getEyeColor());
        hairColorJComboBox.setSelectedItem(builder.getPerson().getHairColor());
        countryJComboBox.setSelectedItem(builder.getPerson().getNationality());

        pack();

        removeButton.addActionListener(x -> remove());
        updateButton.addActionListener(x -> update());
    }

    private void update(){
        StringBuilder errors = new StringBuilder();
        int errorCount = 0;

        if(!builder.setName(nameField.getText())){
            try {
                builder.validateName(nameField.getText());
            }catch (ValidationException e){
                errors.append("Name: " + e.getMessage() + '\n');
                errorCount++;
            }
        }

        Coordinates.CoordinatesBuilder coordinatesBuilder = new Coordinates.CoordinatesBuilder();
        try {
            Float f = Float.valueOf(xField.getText());
            coordinatesBuilder.validateX(f);
            coordinatesBuilder.setX(f);
        }catch (NumberFormatException | ValidationException e){
            errors.append("X field: " + e.getMessage() + '\n');
            errorCount++;
        }
        try {
            Double d = Double.valueOf(yField.getText());
            coordinatesBuilder.validateY(d);
            coordinatesBuilder.setY(d);
        }catch (NumberFormatException | ValidationException e){
            errors.append("Y field: " + e.getMessage() + '\n');
            errorCount++;

        }
        try {
            builder.setCoordinates(coordinatesBuilder.build());
        }catch (ValidationException e){
            errors.append("Coordinates: " + e.getMessage() + '\n');
            errorCount++;

        }

        try {
            builder.setPrice(Integer.parseInt(priceField.getText()));
        }catch (NumberFormatException e){
            errors.append("Price: " + e.getMessage() + '\n');
            errorCount++;

        }

        try {
            builder.setDiscount(Integer.parseInt(discountField.getText()));
        }catch (NumberFormatException e){
            errors.append("Discount: " + e.getMessage() + '\n');
            errorCount++;

        }

        if(!builder.setComment(commentField.getText())){
            try {
                builder.validateComment(commentField.getText());
            }catch (ValidationException e){
                errors.append("Comment: " + e.getMessage() + '\n');
                errorCount++;
            }
        }


        builder.setType((TicketType) typeJComboBox.getSelectedItem());

        Person.PersonBuilder personBuilder = new Person.PersonBuilder();
        try {
            Float f = Float.parseFloat(weightField.getText());
            personBuilder.validateWeight(f);
            personBuilder.setWeight(f);
        }catch (NumberFormatException | ValidationException e){
            errors.append("Person weight: " + e.getMessage() + '\n');
            errorCount++;

        }
        personBuilder.setEyeColor((Color) eyeColorJComboBox.getSelectedItem());
        personBuilder.setHairColor((Color) hairColorJComboBox.getSelectedItem());
        personBuilder.setNationality((Country) countryJComboBox.getSelectedItem());
        try {
            builder.setPerson(personBuilder.build());
        }catch (ValidationException e){
            errors.append("Person: " + e.getMessage() + '\n');
            errorCount++;
        }
        String finalErrors = errors.toString();
        if(errorCount == 0){
            try {
                commandManager.handle("api_update", new String[]{String.valueOf(builder.getId())}, builder);
            }catch (IOException e){
                JOptionPane.showMessageDialog(null, e.getMessage(), "", JOptionPane.ERROR_MESSAGE);
            }
            dispose();
        }else {
            JOptionPane.showMessageDialog(null, errorCount +"\n" + finalErrors);
        }
    }


    private void remove() {
        try {
            commandManager.handle("remove_by_id", new String[]{String.valueOf(builder.getId())});
            dispose();
        }catch (IOException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "", JOptionPane.ERROR_MESSAGE);
        }

    }

}
