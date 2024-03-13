import java.io.Serializable;

public class Bottle implements Serializable, Comparable<Bottle> {


    private final String brand;
    //Should I use a final uninitialised string or an initialised variable??
    /////--------------------------------------------------------
    private final int volumeML;
    private final Material material;

    private String contents = "";

    public Bottle(String brand, int volumeML, Material material) {
        this.brand = brand;
        this.volumeML = volumeML;
        this.material = material;
        this.contents = "EMPTY";
    }

    public Bottle(String brand, int volumeML, Material material, String contents) {
        this.brand = brand;
        this.volumeML = volumeML;
        this.material = material;
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

    public int getVolumeML() {
        return volumeML;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        if (contents.isBlank()) {
            contents = "EMPTY";
        } else {
            this.contents = contents;
        }
    }

    public String toString() {
        String type = "BOTTLE";
        String brand = String.format("%-20s", this.brand);
        String volume = String.format("%-9s", volumeML+"ml");
        String materialName = String.format("%-10s", material.displayName);
//        String contains = "Contains: " + contents;
        return String.format("%s --- %s | %s | %s | %s", type, brand, volume, materialName, contents);
    }

    public int compareTo(Bottle o) {
        return volumeML - o.volumeML;
    }
}
