package core.packet;

import java.io.Serializable;

public class InfoPack implements Serializable {
    private String string;

    public InfoPack(String string) {
        this.string = string;
    }

    public InfoPack() {
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return "InfoPack{" +
                "string='" + string + '\'' +
                '}';
    }
}
