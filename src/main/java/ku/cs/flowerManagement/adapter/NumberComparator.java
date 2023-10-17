package ku.cs.flowerManagement.adapter;

import ku.cs.flowerManagement.entity.PlantOrder;

import java.util.Comparator;

public class NumberComparator implements Comparator<PlantOrder> {
    @Override
    public int compare(PlantOrder o1, PlantOrder o2) {
        return Integer.compare(o1.getPID(), o2.getPID()); //int ไม่มี method compareTo
    }
}
