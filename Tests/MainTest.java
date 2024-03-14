import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
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
    void calculateTotalVolume() {
        assertEquals(10501, Main.calculateTotalVolume(testBottles));
    }

    @Test
    void getBottlesMatchingBrand() {
        Bottle stanleyBottle = testBottles.getFirst();
        ArrayList<Bottle> matchingBottles = Main.getBottlesMatchingBrand(testBottles, "Stanley");
        assertEquals(1, matchingBottles.size());
        assertEquals(stanleyBottle, matchingBottles.getFirst());
    }
}