package ku.cs.flowerManagement.service;

import ku.cs.flowerManagement.common.OrderStatus;
import ku.cs.flowerManagement.entity.GardenerOrder;
import ku.cs.flowerManagement.entity.OrderItem;
import ku.cs.flowerManagement.model.GardenerOrderRequest;
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



    public List<GardenerOrder> getAllGardenerOrder(Comparator comparator){ //เอา order ที่ทั้งหมดออกมา
        List<GardenerOrder> orders = gardenerOrderRepository.findAll();
//        Collections.sort(orders, comparator ); แก้ Comparator
        return orders;
    }


    //ตอนนี้ยังใช้ order เดียวกับตอนบันทึกอยู่ //แก้อยู่
    public List<GardenerOrder> getAllPendingGardenerOrder(Comparator comparator){ //เอา order ที่ต้องปลูกทั้งหมดออกมา
        List<GardenerOrder> orders = gardenerOrderRepository.findByStatus(OrderStatus.PENDING);
//        Collections.sort(orders, comparator );
        return orders;
    }

    //น่าจะไม่ได้ใช้แล้ว
//    public OrderItem getOldestOrderStatus(Comparator comparator){ //เอา order แรกที่ยังไม่ได้ปลูกออกมา
//        return getAllOrderStatus(comparator).get(0);
//    }

    public void addOrder(GardenerOrderRequest gardenerOrder){ //สร้าง order ส่งไปให้ฝ่ายปลูก
//        GardenerOrder record = modelMapper.map(gardenerOrder, GardenerOrder.class);
        GardenerOrder record = new GardenerOrder();
        record.setFlower(flowerRepository.findById(gardenerOrder.getFlowerID()).get());
        record.setQuantity(gardenerOrder.getQuantity());
        record.setStatus(OrderStatus.PENDING);
        gardenerOrderRepository.save(record);
    }

    public void setIn_ProcessOrder(GardenerOrder gardenerOrder){ // set status ของ order เป็น in_process = order นี้ปลูกแล้วนะ
//        System.out.println("ก่อน getOldestOrderStatus ที่ setStatusOrder");
//        OrderItem orderItem = getOldestOrderStatus(comparator);
        gardenerOrder.setStatus(OrderStatus.IN_PROCESS);
//        System.out.println(orderItem.getStatus());
        gardenerOrderRepository.save(gardenerOrder);
//        System.out.println("หลัง getOldestOrderStatus ที่ setStatusOrder");
    }


}
