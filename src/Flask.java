public class Flask extends Bottle {

    int keepWarmHours;
    public Flask(String brand, int volumeML, Material material, int keepWarmHours) {
        super(brand, volumeML, material);
        this.keepWarmHours = keepWarmHours;
    }

    public int getKeepWarmHours() {
        return keepWarmHours;
    }

    @Override
    public String toString() {
        String warmTime = "Warm Time: " + keepWarmHours + " hours";
        return String.format("%s %s", super.toString(), warmTime);

    }
}
