package org.vdk;

import org.junit.jupiter.api.Test;
import org.vdk.task3.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class Task3Tests {
    @Test
    void testScene() {
        Scene scene = new Scene();
        assertEquals(scene.events, List.of(Noise.CALM));
        Action action = new NoiseEvent(scene);
        action.play();
        assertEquals(scene.events, List.of(Noise.CALM, Noise.NOISE, Noise.SCREAMS));
    }

    @Test
    void roomEnter() {
        Room room = new Room("Main room");
        var persons = List.of(new Person("Biba", List.of()));
        Action action = new RoomEnterAction(room, persons);
        assertEquals(room.persons, List.of());
        action.play();
        assertEquals(room.persons, persons);
    }

    @Test
    void push() {
        var persons = List.of(new Person("Biba"), new Person("Boba"));
        Room room = new Room("Main room", persons);
        Action action = new PushAction(room, persons);
        assertEquals(room.persons, persons);
        action.play();
        assertEquals(room.persons, List.of());
    }

    @Test
    void wearHero1() {
        Person hero1 = new Hero1();
        assertEquals(hero1.wears.size(), 2);
        assertTrue(hero1.wears.contains(new Wear(Color.BLUE, "Balahon")));
        assertTrue(hero1.wears.contains(new Wear(Color.BLACK, "Jeans")));
    }


    @Test
    void wearHero2() {
        Person hero2 = new Hero2();
        assertEquals(hero2.wears.size(), 2);
        assertTrue(hero2.wears.contains(new Wear(Color.RED, "Jacket")));
        assertTrue(hero2.wears.contains(new Wear(Color.BLUE, "Pants")));
    }
}
