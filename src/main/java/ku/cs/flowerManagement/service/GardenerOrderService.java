package ku.cs.flowerManagement.service;

import ku.cs.flowerManagement.common.OrderStatus;
import ku.cs.flowerManagement.entity.Flower;
import ku.cs.flowerManagement.entity.GardenerOrder;
import ku.cs.flowerManagement.entity.OrderItem;
import ku.cs.flowerManagement.entity.PlantOrder;
import ku.cs.flowerManagement.model.GardenerOrderRequest;
import ku.cs.flowerManagement.model.OrderItemRequest;
import ku.cs.flowerManagement.repository.FlowerRepository;
import ku.cs.flowerManagement.repository.GardenerOrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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



    public List<GardenerOrder> getAllGardenerOrder(Comparator comparator){ //เอา order ที่ทั้งหมดออกมา
        List<GardenerOrder> orders = gardenerOrderRepository.findAll();
        return orders;
    }
    public Page<GardenerOrder> getAllGardenerOrderPage(int page, int size) {   //สำหรับ pagination
        Pageable pageable = PageRequest.of(page, size);
        return gardenerOrderRepository.findAll(pageable);
    }

    public Page<GardenerOrder> getAllGardenerOrderPendingPage(Pageable pageable) {   //สำหรับ pagination
        return gardenerOrderRepository.findAllByStatus(OrderStatus.PENDING, pageable);
    }


    public List<GardenerOrder> getAllPendingGardenerOrderForSort(){ //เอา order ที่ต้องปลูกทั้งหมดออกมา
        List<GardenerOrder> orders = gardenerOrderRepository.findAllByStatus(OrderStatus.PENDING);
    
        return orders;
    }
    public List<GardenerOrder> getAllPendingGardenerOrder(Comparator comparator){ 
        List<GardenerOrder> orders = gardenerOrderRepository.findAllByStatus(OrderStatus.PENDING);
        orders.sort(comparator);
        return orders;
    }
    public List<GardenerOrder> getAllInprocessGardenerOrder(){ 
        List<GardenerOrder> orders = gardenerOrderRepository.findAllByStatus(OrderStatus.IN_PROCESS);
        return orders;
    }

     public List<GardenerOrder> getAllFailGardenerOrder(){ 
        List<GardenerOrder> orders = gardenerOrderRepository.findAllByStatus(OrderStatus.FAIL);
       
        return orders;
    }


    public void addOrder(GardenerOrderRequest gardenerOrder){ //สร้าง order ส่งไปให้ฝ่ายปลูก
        GardenerOrder record = new GardenerOrder();
        record.setFlower(flowerRepository.findByFID(gardenerOrder.getFlowerID()));
        record.setQuantity(gardenerOrder.getQuantity());
        record.setStatus(OrderStatus.PENDING);
        gardenerOrderRepository.save(record);
    }
    
    public void setIn_ProcessOrder(GardenerOrder gardenerOrder, PlantOrder plantOrder){ // set status ของ order เป็น in_process = order นี้ปลูกแล้วนะ
        gardenerOrder.setStatus(OrderStatus.IN_PROCESS);
        gardenerOrderRepository.save(gardenerOrder);
    }


}
