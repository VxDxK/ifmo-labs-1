package core.packet;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class StartupPack implements Serializable {
    private final Map<String, Boolean> tre = new HashMap<>();
    private String help;

    public Map<String, Boolean> getMap() {
        return tre;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public StartupPack() {

    }

    @Override
    public String toString() {
        return "StartupPack{" +
                "tre=" + tre +
                ", help='" + help + '\'' +
                '}';
    }
}
