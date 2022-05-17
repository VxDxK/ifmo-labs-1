package util;

import java.io.Serializable;

public class CommandExternalInfo implements Serializable {
    public boolean elementRequire;
    public boolean needAuth;

    public CommandExternalInfo(boolean elementRequire, boolean needAuth) {
        this.elementRequire = elementRequire;
        this.needAuth = needAuth;
    }
}
