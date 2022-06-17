package core.guiclient.gui.locales;

import java.util.ListResourceBundle;

public class loc extends ListResourceBundle {

    private final String[][] translates = {
            {"conn", "Connect"},
            {"esa", "Enter server address"},
            {"inp", "Input Ticket"},
            {"fine_inp", "Ticket is valid"},
            {"loaded", "Loaded: %d commands, from: %s"},
            {"err", "Error"},
            {"exec", "Execute"},
            {"mode_exec", "Command execution"},
            {"mode_table", "Ticket table"},
            {"mode_plane", "Coordinate plane"},
            {"logged_as", "You`re logged as: "},
            {"fine_startup", "Fine startup"},
            {"fl", "Fine login"},
            {"serv_addr_down", "Server on: %s is unavailable"},
            {"server_down", "Server is down now"},
            {"loq_req", "Login required"},
            {"no_element", "No element was specified"},
            {"remove", "Remove"},
            {"upd", "Update Ticket"},
            {"in", "Log in"},
            {"up", "Log up"},
            {"out", "Log out"},
            {"not_log", "You are not logged in"},
            {"lr", "Login required"}
    };


    @Override
    protected Object[][] getContents() {
        return translates;
    }
}
