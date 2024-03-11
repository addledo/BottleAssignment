import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    ArrayList<Bottle> testBottles = new ArrayList<>();
    String testSaveLocation = "test_save_file.dat";

    @BeforeEach
    void setUp() {
        testBottles.add(new Bottle("Stanley", 500, Material.METAL, ""));
        testBottles.add(new Bottle("Small", 1, Material.PLASTIC));
        testBottles.add(new Bottle("Large", 1000000, Material.GLASS));
        testBottles.add(new Bottle("Empty", 0, Material.GLASS));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void bottlesSave() {
        assertDoesNotThrow(
                () -> {
                    Main.saveBottles(testBottles, "src/fake_directory/bottles.dat");
                });

    }

    @Test
    void bottlesLoadThenSaveSuccessfully() {
        Main.saveBottles(null, testSaveLocation);
        assertNull(Main.loadBottles(testSaveLocation));
//        Main.saveBottles(testBottles, testSaveLocation);
//        assertEquals(testBottles, Main.loadBottles(testSaveLocation));


        try {
            Files.delete(Path.of(testSaveLocation));
        } catch (IOException e) {
            System.out.println("Test file may remain: \"" +testSaveLocation + "\"");
            System.out.println(e.getMessage());
        }
    }

}