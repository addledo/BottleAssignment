import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BottleBrandComparatorTest {
    ArrayList<Bottle> testBottles = new ArrayList<>();

    @BeforeEach
    void setUp() {
        testBottles.add(new Bottle("D", 500, Material.METAL, ""));
        testBottles.add(new Bottle("C", 1, Material.PLASTIC, "Something"));
        testBottles.add(new Bottle("A", 10000, Material.GLASS, "1234"));
        testBottles.add(new Bottle("B", 0, Material.GLASS));
    }

    @AfterEach
    void tearDown() {
        testBottles = new ArrayList<>();
    }

    @Test
    void compare() {
        BottleBrandComparator brandComparator = new BottleBrandComparator();
        testBottles.sort(brandComparator);
        assertEquals("A", testBottles.get(0).getBrand());
        assertEquals("B", testBottles.get(1).getBrand());
        assertEquals("C", testBottles.get(2).getBrand());
        assertEquals("D", testBottles.get(3).getBrand());
    }
}