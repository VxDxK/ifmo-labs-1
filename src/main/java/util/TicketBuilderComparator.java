package util;

import core.pojos.Ticket;

import java.util.Comparator;

/**
 * Comparator to compare ticket builders
 */
public class TicketBuilderComparator implements Comparator<Ticket.TicketBuilder> {
    @Override
    public int compare(Ticket.TicketBuilder o1, Ticket.TicketBuilder o2) {
        return (o1.getPrice() * (1 -(o1.getDiscount()/100))) - (o2.getPrice() * (1 - (o2.getDiscount()/100)));
    }
}
