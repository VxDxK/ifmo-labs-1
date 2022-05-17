package core.server;

import core.pojos.Ticket;
import core.pojos.TicketWrap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.time.LocalDateTime;
import java.util.*;


public final class EncapsulatedCollection extends AbstractCollection<TicketWrap> {
    private final Deque<TicketWrap> tickets = new ArrayDeque<>();
    private final LocalDateTime initTime = LocalDateTime.now();

    @Override
    public Iterator<TicketWrap> iterator() {
        return tickets.iterator();
    }

    @Override
    public int size() {
        return tickets.size();
    }

    public String getType(){
        return tickets.getClass().getSimpleName();
    }

    public LocalDateTime getInitTime() {
        return initTime;
    }

    @Override
    public synchronized boolean add(TicketWrap ticket) {
        return tickets.add(ticket);
    }

    public synchronized boolean removeByID(int id){
        for(TicketWrap t : tickets){
            if(t.getTicket().getId() == id){
                return tickets.remove(t);
            }
        }
        return false;
    }

    public synchronized boolean remove(TicketWrap o) {
        return removeByID(o.getTicket().getId());
    }

    public synchronized TicketWrap getById(int id){
        if(id < 0){
            return null;
        }
        for (TicketWrap ticket: tickets) {
            if(ticket.getTicket().getId() == id){
                return ticket;
            }
        }
        return null;
    }

    @Override
    public synchronized boolean addAll(Collection<? extends TicketWrap> c) {
        return super.addAll(c);
    }

    public Deque<TicketWrap> getSourceCollection(){
        return tickets;
    }

}
