package core.server.database;

import core.pojos.TicketWrap;

import java.util.List;

public interface TicketDAO extends DAO<TicketWrap> {
    List<TicketWrap> all();

}
