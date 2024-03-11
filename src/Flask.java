public class Flask extends Bottle {

    int keepWarmHours;
    public Flask(String brand, int volumeML, Material material, int keepWarmHours) {
        super(brand, volumeML, material);
        this.keepWarmHours = keepWarmHours;
    }

    public int getKeepWarmHours() {
        return keepWarmHours;
    }

    public void setKeepWarmHours(int keepWarmHours) {
        this.keepWarmHours = keepWarmHours;
    }
}
