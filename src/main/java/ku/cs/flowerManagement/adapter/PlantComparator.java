package ku.cs.flowerManagement.adapter;

import ku.cs.flowerManagement.entity.PlantOrder;

import java.util.Comparator;

public class PlantComparator implements Comparator<PlantOrder> {
    @Override
    public int compare(PlantOrder order1, PlantOrder order2) {
        // Compare PlantOrder objects based on their PID
        return Integer.compare(order1.getPID(), order2.getPID());
    }
}
