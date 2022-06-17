package core.guiclient.gui.locales;

import java.util.ListResourceBundle;

public class loc_hu extends ListResourceBundle {

    private final String[][] translates = {
            {"conn", "Csatlakozás"},
            {"esa", "Adja meg a szerver címét"},
            {"inp", "Beviteli Jegy"},
            {"fine_inp", "A jegy érvényes"},
            {"loaded", "Betöltve: %d parancsok, tól től: %s"},
            {"err", "Hiba"},
            {"exec", "Végrehajtás"},
            {"mode_exec", "Parancs végrehajtása"},
            {"mode_table", "Jegy asztal"},
            {"mode_plane", "Koordináta sík"},
            {"logged_as", "Be van jelentkezve, mint: "},
            {"fine_startup", "Finom indítás"},
            {"fl", "Finom bejelentkezés"},
            {"serv_addr_down", "Szerver bekapcsolva: %s nem érhető el"},
            {"server_down", "A szerver most nem működik"},
            {"loq_req", "Bejelentkezés szükséges"},
            {"no_element", "Nincs megadva elem"},
            {"remove", "Eltávolítás"},
            {"upd", "Jegy Frissítése"},
            {"in", "Bejelentkezés"},
            {"up", "Bejelentkezés"},
            {"out", "Kijelentkezés"},
            {"not_log", "Nincs bejelentkezve"},
            {"lr", "Bejelentkezés szükséges"}
    };


    @Override
    protected Object[][] getContents() {
        return translates;
    }
}
