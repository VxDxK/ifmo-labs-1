package core;

import core.pojos.Ticket;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.time.LocalDateTime;
import java.util.*;

@XmlRootElement(name = "collection")
@XmlAccessorType(XmlAccessType.FIELD)
public class EncapsulatedCollection extends AbstractCollection<Ticket> {
    private final Deque<Ticket> tickets;
    @XmlTransient
    private final LocalDateTime initTime;
    @XmlTransient
    private final Set<Integer> idSet;
    {
        tickets = new ArrayDeque<>();
        initTime = LocalDateTime.now();
        idSet = new HashSet<>();
//        for(int i = 0; i < Integer.MAX_VALUE; i++){
//            idSet.add(i);
//        }
    }


    @Override
    public Iterator<Ticket> iterator() {
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
    public boolean add(Ticket ticket) {
        idSet.add(ticket.getId());
        return tickets.add(ticket);
    }

    public boolean validate(){
        for(Ticket ticket : tickets){
            Ticket.TicketBuilder ticketBuilder = new Ticket.TicketBuilder(ticket);
            try{
                ticketBuilder.build();
            } catch (ValidationException e) {
                return false;
            }
            if(idSet.contains(ticket.getId())){
                return false;
            }
            idSet.add(ticket.getId());
        }
        return true;
    }


    public boolean add(Ticket.TicketBuilder ticketBuilder) throws ValidationException{
        int id = (int)(Math.random() * Integer.MAX_VALUE);
        while (idSet.contains(id)){
            id = (int)(Math.random() * Integer.MAX_VALUE);
        }
        ticketBuilder.setId(id);
        ticketBuilder.setCreationDate(LocalDateTime.now());
        return add(ticketBuilder.build());
    }

    public boolean remove(Ticket o) {
        if(super.remove(o)){
            idSet.remove(o.getId());
            return true;
        }
        return false;
    }

    public Ticket getById(int id){
        if(id < 0){
            return null;
        }
        for (Ticket ticket: tickets) {
            if(ticket.getId() == id){
                return ticket;
            }
        }
        return null;
    }

    public boolean update(int id, Ticket.TicketBuilder builder){
        try {
            if(getById(id) == null){
                return add(builder);
            }
            builder.setId(id);
            builder.setCreationDate(LocalDateTime.now());
            Ticket ticket = builder.build();
            if(remove(getById(id))){
                return add(builder);
            }else {
                return false;
            }
        }catch (ValidationException e){
            return false;
        }
    }

    public Set<Integer> getIdSet() {
        return idSet;
    }

    public Deque<Ticket> getSourceCollection(){
        return tickets;
    }

    public Ticket getMin(){
        if(size() == 0){
            return null;
        }
        Ticket ticket = tickets.getFirst();
        for (Ticket ticketC : tickets) {
            if(ticket.compareTo(ticketC) > 0){
                ticket = ticketC;
            }
        }
        return ticket;
    }

    public Ticket getMax(){
        if(size() == 0){
            return null;
        }
        Ticket ticket = tickets.getFirst();
        for (Ticket ticketC : tickets) {
            if(ticket.compareTo(ticketC) < 0){
                ticket = ticketC;
            }
        }
        return ticket;
    }

}
