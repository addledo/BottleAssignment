import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FlaskTest {

    ArrayList<Flask> testFlasks = new ArrayList<>();

    @BeforeEach
    void setUp() {
        testFlasks.add(new Flask("Empty", 0, Material.GLASS, -5));
        testFlasks.add(new Flask("Empty", 0, Material.GLASS, 0));
        testFlasks.add(new Flask("Empty", 0, Material.GLASS, 4));
        testFlasks.add(new Flask("Empty", 0, Material.GLASS, 500));
    }

    @AfterEach
    void tearDown() {
        testFlasks = new ArrayList<>();
    }

    @Test
    void getKeepWarmHours() {
        assertEquals(-5, testFlasks.get(0).getKeepWarmHours());
        assertEquals(0, testFlasks.get(1).getKeepWarmHours());
        assertEquals(4, testFlasks.get(2).getKeepWarmHours());
        assertEquals(500, testFlasks.get(3).getKeepWarmHours());
    }

}