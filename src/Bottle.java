import java.io.Serializable;

public class Bottle implements Serializable {


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
        String materialName = material.displayName;
        String contains = "Contains: " + contents;
        return String.format("%s, %dml, %s, %s", brand, volumeML, materialName, contains);
    }
}
