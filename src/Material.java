import java.util.ArrayList;
import java.util.Arrays;

public enum Material {
    PLASTIC("Plastic"),
    METAL("Metal"),
    GLASS("Glass");

    final String displayName;

    Material(String displayName) {
        this.displayName = displayName;
    }

//    public String getDisplayName() {
//        String name = this.name();
////    ArrayList<String> letters = (ArrayList<String>) Arrays.asList(name.split("_"));
//        String[] letters = name.split()
//        return name;
//    }
}