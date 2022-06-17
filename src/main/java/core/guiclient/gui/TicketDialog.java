package core.guiclient.gui;

import core.guiclient.gui.locales.loc;
import core.pojos.*;
import core.pojos.Color;
import core.server.ValidationException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class TicketDialog extends JDialog {
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

    JButton button = new JButton("OK");

    private final Ticket.TicketBuilder builder = new Ticket.TicketBuilder();
    private boolean ready = false;
    private boolean validated = false;

    public TicketDialog(MainView frame) {
        super(frame, "Input Ticket", true);
        res = ResourceBundle.getBundle(loc.class.getName(), frame.getLoc());
        setTitle(res.getString("inp"));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        button.addActionListener(e -> buttonPressed());

        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                ready = true;
            }

            @Override
            public void windowClosed(WindowEvent e) {

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
        getContentPane().add(button, BorderLayout.SOUTH);

        pack();
    }

    private void buttonPressed(){

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
            Integer i = Integer.parseInt(discountField.getText());
            builder.validateDiscount(i);
            builder.setDiscount(i);
        }catch (NumberFormatException | ValidationException e){
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
            JOptionPane.showMessageDialog(null, res.getString("fine_inp"));
            ready = true;
            validated = true;
            dispose();
        }else {
            JOptionPane.showMessageDialog(null, errorCount +"\n" + finalErrors);
        }
    }

    public Optional<Ticket.TicketBuilder> getValue(){
        if(validated){
            return Optional.of(builder);
        }else{
            return Optional.empty();
        }
    }

}
