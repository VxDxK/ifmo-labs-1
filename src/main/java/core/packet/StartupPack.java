package core.packet;

import util.CommandExternalInfo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class StartupPack extends InfoPack implements Serializable {
    protected final Map<String, CommandExternalInfo> tre = new HashMap<>();

    public Map<String, CommandExternalInfo> getMap() {
        return tre;
    }


    public StartupPack() {

    }

    @Override
    public String toString() {
        return "StartupPack{" +
                "tre=" + tre +
                ", string='" + string + '\'' +
                '}';
    }
}
