package org.vdk.task3;

import java.util.Objects;

public class Wear {
    public Color color;
    public String type;

    public Wear(Color color, String type) {
        this.color = color;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wear wear = (Wear) o;
        return color == wear.color && Objects.equals(type, wear.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, type);
    }
}
