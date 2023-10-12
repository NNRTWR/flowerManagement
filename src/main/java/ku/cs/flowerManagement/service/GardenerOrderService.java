package ku.cs.flowerManagement.service;

import ku.cs.flowerManagement.common.OrderStatus;
import ku.cs.flowerManagement.entity.OrderItem;
import ku.cs.flowerManagement.model.OrderItemRequest;
import ku.cs.flowerManagement.repository.FlowerRepository;
import ku.cs.flowerManagement.repository.GardenerOrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class GardenerOrderService { // order ของฝ่ายปลูก
    @Autowired
    private GardenerOrderRepository gardenerOrderRepository;

    @Autowired
    private FlowerRepository flowerRepository;

    @Autowired
    private ModelMapper modelMapper;



    public List<OrderItem> getAllOrderItem(Comparator comparator){ //เอา order ที่ทั้งหมดออกมา
        List<OrderItem> orders = gardenerOrderRepository.findAll();
        Collections.sort(orders, comparator );
        return orders;
    }


    //ตอนนี้ยังใช้ order เดียวกับตอนบันทึกอยู่
    public List<OrderItem> getAllOrderStatus(Comparator comparator){ //เอา order ที่ต้องปลูกทั้งหมดออกมา
        List<OrderItem> orders = gardenerOrderRepository.findByStatus(OrderStatus.PENDING);
        Collections.sort(orders, comparator );
        return orders;
    }

    public OrderItem getOldestOrderStatus(Comparator comparator){ //เอา order แรกที่ยังไม่ได้ปลูกออกมา
        return getAllOrderStatus(comparator).get(0);
    }

    public void addOrder(OrderItemRequest orderItem){ //สร้าง order ส่งไปให้ฝ่ายปลูก
        OrderItem record = modelMapper.map(orderItem, OrderItem.class);
        record.setFlower(flowerRepository.findById(orderItem.getFlowerID()).get());
        record.setStatus(OrderStatus.PENDING);
        gardenerOrderRepository.save(record);
    }

    public void setIn_ProcessOrder(Comparator comparator){ // set status ของ order เป็น in_process = order นี้ปลูกแล้วนะ
//        System.out.println("ก่อน getOldestOrderStatus ที่ setStatusOrder");
        OrderItem orderItem = getOldestOrderStatus(comparator);
        orderItem.setStatus(OrderStatus.IN_PROCESS);
//        System.out.println(orderItem.getStatus());
        gardenerOrderRepository.save(orderItem);
//        System.out.println("หลัง getOldestOrderStatus ที่ setStatusOrder");
    }


}
