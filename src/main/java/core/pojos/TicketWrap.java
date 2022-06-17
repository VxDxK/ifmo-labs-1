package core.pojos;

import java.io.Serializable;

public final class TicketWrap implements Serializable {
    private Ticket ticket;
    private UserClient.UserServer user;
    public TicketWrap() {
    }

    public TicketWrap(Ticket ticket, UserClient.UserServer user) {
        this.ticket = ticket;
        this.user = user;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public UserClient.UserServer getUser() {
        return user;
    }

    public void setUser(UserClient.UserServer user) {
        this.user = user;
    }

    public int getId() {
        if(ticket == null){
            return -1;
        }
        return ticket.getId();
    }

    @Override
    public String toString() {
        return "TicketWrap{" +
                "ticket=" + ticket +
                ", user=" + user.getLogin() +
                '}';
    }
}
