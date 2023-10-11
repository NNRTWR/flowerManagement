package ku.cs.flowerManagement.adapter;

import ku.cs.flowerManagement.entity.OrderItem;

import java.util.Comparator;

public class DateTimeComparator implements Comparator<OrderItem> {
    @Override
    public int compare(OrderItem o1, OrderItem o2) {
        return o1.getDate().compareTo(o2.getDate());
    }
}
