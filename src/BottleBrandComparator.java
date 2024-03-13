import java.util.Comparator;

public class BottleBrandComparator implements Comparator<Bottle> {
    public int compare(Bottle o1, Bottle o2) {
        return o1.getBrand().compareTo(o2.getBrand());
    }
}
