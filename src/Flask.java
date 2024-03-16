public class Flask extends Bottle {

    private final int keepWarmHours;

    public Flask(String brand, int volumeInML, Material material, int keepWarmHours) {
        super(brand, volumeInML, material);
        this.keepWarmHours = keepWarmHours;
    }

    public int getKeepWarmHours() {
        return keepWarmHours;
    }

    @Override
    public String toString() {
        String warmTime = "Warm Time: " + keepWarmHours + " hours";
        return super.toString() + warmTime;
    }
}
