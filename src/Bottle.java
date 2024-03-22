import java.io.Serializable;

public class Bottle implements Serializable, Comparable<Bottle> {
    public static final int brandLengthLimit = 20;

    private final String brand;
    private final int volumeInML;
    private final Material material;

    private String contents = "EMPTY";

    public Bottle(String brand, int volumeInML, Material material) {
        this.brand = brand;
        this.volumeInML = volumeInML;
        this.material = material;
    }

    public Bottle(String brand, int volumeInML, Material material, String contents) {
        this.brand = brand;
        this.volumeInML = volumeInML;
        this.material = material;
        setContents(contents);
    }

    public void setContents(String contents) {
        if (contents.isBlank()) {
            this.contents = "EMPTY";
        } else {
            this.contents = contents;
        }
    }

    public String getBrand() {
        return brand;
    }

    public Material getMaterial() {
        return material;
    }

    public int getVolumeInML() {
        return volumeInML;
    }

    public String getContents() {
        return contents;
    }

    public String toString() {
        String type = this.getClass().getName();
        //Padding strings to align columns:
        // %n  means strings smaller than n character will be padded to that length (right aligned)
        // %-n  does the same thing but the text is left aligned
        type = String.format("%-7s", type);
        String brand = String.format("%-" + brandLengthLimit + "s", this.brand);
        String volume = String.format("%-8s", volumeInML + "ml");
        String materialName = String.format("%-8s", material.displayName);
        String contents = this.contents;
        if (contents.length() > 17) {
            contents = contents.substring(0, 17) + "...";
        }
        return String.format(" %s | %s | %s | %s | %-20s | ", type, brand, volume, materialName, contents);
    }

    public int compareTo(Bottle o) {
        return volumeInML - o.volumeInML;
    }
}
