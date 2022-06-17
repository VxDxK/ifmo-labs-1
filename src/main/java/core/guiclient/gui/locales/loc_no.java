package core.guiclient.gui.locales;

import java.util.ListResourceBundle;

public class loc_no extends ListResourceBundle {

    private final String[][] translates = {
            {"conn", "Koble"},
            {"esa", "Skriv inn serveradresse"},
            {"inp", "Inngangsbillett"},
            {"fine_inp", "Billetten er gyldig"},
            {"loaded", "Lastet: % d kommandoer, fra: %s"},
            {"err", "Feil"},
            {"exec", "Utføre"},
            {"mode_exec", "Kommando utførelse"},
            {"mode_table", "Billett tabell"},
            {"mode_plane", "Koordinatplanet"},
            {"logged_as", "Du er logget som:"},
            {"fine_startup", "Fin oppstart"},
            {"fl", "Fin logg inn"},
            {"serv_addr_down", "Server på: %s er utilgjengelig"},
            {"server_down", "Serveren er nede nå"},
            {"loq_req", "Innlogging kreves"},
            {"no_element", "Ingen element ble spesifisert"},
            {"remove", "Fjerne"},
            {"upd", "Oppdater Billett"},
            {"in", "Pålogging"},
            {"up", "Logg inn"},
            {"out", "Logge"},
            {"not_log", "Du er ikke logget inn"},
            {"lr", "Innlogging kreves"}
    };


    @Override
    protected Object[][] getContents() {
        return translates;
    }
}
