import java.util.Comparator;

public class BottleBrandComparator implements Comparator<Bottle> {
    public int compare(Bottle o1, Bottle o2) {
        String brand1 = o1.getBrand();
        String brand2 = o2.getBrand();
        return brand1.compareTo(brand2);
    }
}
