package core.guiclient.gui;

import core.guiclient.GuiCommandManager;
import core.guiclient.gui.locales.loc;
import core.pojos.Coordinates;
import core.pojos.TicketWrap;
import core.pojos.UserClient;
import util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.List;


public class CoordinatePlane extends JComponent {
    private final List<TicketWrap> ticketWrapList;
    private final List<Pair<Rectangle2D, TicketWrap>> rectangles = new ArrayList<>();
    private final GuiCommandManager commandManager;
    private float minX = Float.MAX_VALUE;
    private float maxX = Float.MIN_VALUE;
    private double minY = Double.MAX_VALUE;
    private double maxY = Double.MIN_VALUE;
    public CoordinatePlane(List<TicketWrap> ticketWrapList, GuiCommandManager commandManager) {
        this.ticketWrapList = ticketWrapList;
        this.commandManager = commandManager;

        for(TicketWrap wrap : ticketWrapList){
            Coordinates coordinates = wrap.getTicket().getCoordinates();
            minX = Math.min(coordinates.getX(), minX);
            maxX = Math.max(coordinates.getX(), maxX);

            minY =  Math.min(coordinates.getY(), minY);
            maxY =  Math.max(coordinates.getY(), maxY);
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton() == 1){
                    Optional<Pair<Rectangle2D, TicketWrap>> rectangle = rectangles.stream().filter(x -> x.first.contains(e.getX(), e.getY())).findFirst();
                    rectangle.ifPresent(x -> {
                        UpdateDialog updateDialog = new UpdateDialog(commandManager.getFrame(), commandManager, x.second);
                        updateDialog.setVisible(true);
                    });
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;

        for(TicketWrap wrap : ticketWrapList){
            float x = wrap.getTicket().getCoordinates().getX();
            double y = wrap.getTicket().getCoordinates().getY();

            double realX = map(x, minX, maxX, 0, getSize().getWidth());
            double realY = map(y, minY, maxY, 0, getSize().getHeight());
            Rectangle2D rectangle2D = new Rectangle2D.Double(realX, realY, 50, 50);
            rectangles.add(new Pair<>(rectangle2D, wrap));

            graphics2D.setColor(new Color(wrap.getUser().hashCode()));
            graphics2D.fill(rectangle2D);
            graphics2D.setColor(Color.BLACK);
            graphics2D.draw(rectangle2D);
        }
    }


    private double map(double x, double in_min, double in_max, double out_min, double out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }


}
