package org.vdk.task3;

import java.util.List;

public class NoiseEvent implements Action{
    public final Scene scene;

    public NoiseEvent(Scene scene) {
        this.scene = scene;
    }

    @Override
    public void play() {
        scene.events.addAll(List.of(Noise.NOISE, Noise.SCREAMS));
    }
}
