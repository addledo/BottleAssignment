import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Collections;

class BottleTest {

    ArrayList<Bottle> testBottles = new ArrayList<>();

    @BeforeEach
    void setUp() {
        testBottles.add(new Bottle("Stanley", 500, Material.METAL, ""));
        testBottles.add(new Bottle("Small", 1, Material.PLASTIC, "Something"));
        testBottles.add(new Bottle("Large", 10000, Material.GLASS, "1234"));
        testBottles.add(new Bottle("Empty", 0, Material.GLASS));
    }

    @AfterEach
    void tearDown() {
        testBottles = new ArrayList<>();
    }

    @Test
    void getBrand() {
        assertEquals("Stanley", testBottles.get(0).getBrand());
        assertEquals("Small", testBottles.get(1).getBrand());
        assertEquals("Large", testBottles.get(2).getBrand());
        assertEquals("Empty", testBottles.get(3).getBrand());

    }

    @Test
    void getMaterial() {
        assertEquals(Material.METAL, testBottles.get(0).getMaterial());
        assertEquals(Material.PLASTIC, testBottles.get(1).getMaterial());
        assertEquals(Material.GLASS, testBottles.get(2).getMaterial());
    }

    @Test
    void getVolumeML() {
        assertEquals(500, testBottles.get(0).getVolumeML());
        assertEquals(1, testBottles.get(1).getVolumeML());
        assertEquals(10000, testBottles.get(2).getVolumeML());
        assertEquals(0, testBottles.get(3).getVolumeML());
    }

    @Test
    void getContents() {
        assertEquals("EMPTY", testBottles.get(0).getContents());
        assertEquals("Something", testBottles.get(1).getContents());
        assertEquals("1234", testBottles.get(2).getContents());
        assertEquals("EMPTY", testBottles.get(3).getContents());
    }

    @Test
    void setContents() {
        Assertions.assertNotEquals("Setter test", testBottles.getFirst().getContents());
        testBottles.getFirst().setContents("Setter test");
        Assertions.assertEquals("Setter test", testBottles.getFirst().getContents());

    }

    @Test
    void compareTo() {
        Collections.sort(testBottles);
        assertEquals(0, testBottles.get(0).getVolumeML());
        assertEquals(1, testBottles.get(1).getVolumeML());
        assertEquals(500, testBottles.get(2).getVolumeML());
        assertEquals(10000, testBottles.get(3).getVolumeML());
    }
}