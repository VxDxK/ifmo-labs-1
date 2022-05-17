package core.packet;

import core.pojos.TicketWrap;

import java.util.ArrayList;
import java.util.List;

public class UpdatePack extends InfoPack{
    public List<TicketWrap> listOfTickets = new ArrayList<>();

    public List<TicketWrap> getListOfTickets() {
        return listOfTickets;
    }
}
