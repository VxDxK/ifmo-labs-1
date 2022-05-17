package core.pojos;

public class IntegerWrapper {
    private Ticket ticket;
    private int ownerID;

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public IntegerWrapper(Ticket ticket, int ownerID) {
        this.ticket = ticket;
        this.ownerID = ownerID;
    }
}
