package ku.cs.flowerManagement.adapter;

import ku.cs.flowerManagement.entity.GardenerOrder;
import ku.cs.flowerManagement.entity.PlantOrder;

import java.util.Comparator;

public class GardenOrderTimeComparator implements Comparator<GardenerOrder> {
    @Override
    public int compare(GardenerOrder o1, GardenerOrder o2) {
        return o1.getDate().compareTo(o2.getDate());
    }
}
