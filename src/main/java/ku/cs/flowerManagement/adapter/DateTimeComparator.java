package ku.cs.flowerManagement.adapter;

import ku.cs.flowerManagement.entity.OrderItem;
import ku.cs.flowerManagement.entity.PlantOrder;

import java.util.Comparator;

public class DateTimeComparator implements Comparator<PlantOrder> {

    @Override
    public int compare(PlantOrder o1, PlantOrder o2) {
        return o1.getTimePlant().compareTo(o2.getTimePlant());
    }
}
