import com.vdk.lab2demo.Monadka;
import com.vdk.lab2demo.bean.Shot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MonadkaTest {

    @Test
    void of() {
    }

    @Test
    void check() {
        Monadka<Double> mon = Monadka.of(5d);
        mon.check(x -> x > 3, "X should be greater 5");
        assertDoesNotThrow(() -> {
            mon.get();
        });

        mon.check(x -> x > 10, "X should be greater 10").check(x -> x > 11, "X should be greater 11");
        assertThrows(RuntimeException.class, mon::get);
        try {
            mon.get();
        }catch (RuntimeException e){
            assertEquals(e.getSuppressed().length, 2);
        }
    }

    @Test
    void checkFinal() {
        Monadka<Double> mon = Monadka.of(5d);
        mon.checkFinal(x -> x > 3, "X should be greater 5");
        assertDoesNotThrow(() -> {
            mon.get();
        });

        mon.checkFinal(x -> x > 10, "X should be greater 10").checkFinal(x -> x > 11, "X should be greater 11");
        assertThrows(RuntimeException.class, mon::get);
        try {
            mon.get();
        }catch (RuntimeException e){
            assertEquals(e.getSuppressed().length, 1);
        }
    }

    @Test
    void map() {
        Monadka<Double> monadka = Monadka.of("5.034").map(Double::parseDouble).check(x -> x > 0, "");

        assertDoesNotThrow(() -> {
            monadka.get();
        });
        assertEquals(5.034, monadka.get());

        Monadka<Double> mon = Monadka.of("5.0ad34").map(Double::parseDouble).check(x -> x > 0, "");
        assertThrows(RuntimeException.class, mon::get);
    }

    @Test
    void shotTest(){
        Monadka<Shot> monadka = Monadka.of(new Shot(9, 6, 3));
    }

    @Test
    void onFailure(){

    }

    @Test
    void get() {
    }
}