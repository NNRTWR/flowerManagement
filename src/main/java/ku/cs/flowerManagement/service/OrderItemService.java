package ku.cs.flowerManagement.service;

import jakarta.persistence.EntityNotFoundException;
import ku.cs.flowerManagement.common.FlowerStatus;
import ku.cs.flowerManagement.common.OrderStatus;
import ku.cs.flowerManagement.entity.Flower;
import ku.cs.flowerManagement.entity.OrderItem;
import ku.cs.flowerManagement.model.OrderItemRequest;
import ku.cs.flowerManagement.repository.FlowerRepository;
import ku.cs.flowerManagement.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class OrderItemService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private FlowerRepository flowerRepository;

    @Autowired
    private ModelMapper modelMapper;

    private int numOrder = 0;

    //Get Orders
    public List<OrderItemRequest> getOrders() {
        List<OrderItem> orders = orderRepository.findAll();
        List<OrderItemRequest> orderFlowerRequests = new ArrayList<>();
//        for (OrderItem ord:orders) {
//            OrderItemRequest orderFlowerRequest = modelMapper.map(ord, OrderItemRequest.class);
//            orderFlowerRequest.setFName(ord.getFlower().getFName());
//            orderFlowerRequest.setFID(ord.getFlower().getFID());
//            orderFlowerRequests.add(orderFlowerRequest);
//        }
        return orderFlowerRequests;
    }

    //ลองปรับ
    public List<OrderItem> getAllOrders() {
        List<OrderItem> orders = orderRepository.findAll();
        return orders;
    }


    // Get order By Id
    public OrderItemRequest getOrderById(int id) {
        OrderItem orderFlower = orderRepository.findByOID(id);
//        OrderItem orderFlower = orderRepository.findById(id).orElse(null); //ติดแดงที่ .orElse(null)
        if (orderFlower == null) {
            throw new EntityNotFoundException();
        }
        OrderItemRequest orderFlowerRequest = modelMapper.map(orderFlower, OrderItemRequest.class);
        orderFlowerRequest.setFName(orderFlower.getFlower().getFName());
        orderFlowerRequest.setFID(orderFlower.getFlower().getFID());
        return orderFlowerRequest;
    }


    // Create Order
    public void createOrder(OrderItemRequest orderFlowerRequest) {
        OrderItem orderFlower = modelMapper.map(orderFlowerRequest, OrderItem.class);
        Flower flower = flowerRepository.findByFID(orderFlowerRequest.getFID());
//        Flower flower = flowerRepository.findById(orderFlowerRequest.getFID()).orElse(null); //ติดแดงที่ .orElse(null)
        if(flower == null) return;
        orderFlower.setFlower(flower);
        orderFlower.setPrice(orderFlowerRequest.getFlowerPrice()*orderFlowerRequest.getOrderQuantity());
        orderFlower.setStatus(OrderStatus.PENDING);
//        orderFlower.setPlant_status(FlowerStatus.SEED);
        orderFlower.setOrder_method(orderFlowerRequest.getOrder_method());
        numOrder++ ; //ปลูกสำเร็จก็จะมาเพิ่มจำนวนดอกไม้ในระบบ
        orderFlower.setOID(numOrder);
        orderRepository.save(orderFlower);
    }

    // Complete order
    public void completeOrderById(int id) {
        OrderItem orderFlower = orderRepository.findByOID(id);
//        OrderItem orderFlower = orderRepository.findById(id).orElse(null); //ติดแดงที่ .orElse(null)
        if (orderFlower == null) {
            System.out.println("Order not found.");
            return;
        }
        orderFlower.setStatus(OrderStatus.COMPLETED);
        orderRepository.save(orderFlower);
    }

    // Cancel Order
    public void cancelOrderById(int id) {
        OrderItem orderFlower = orderRepository.findByOID(id);
//        OrderItem orderFlower = orderRepository.findById(id).orElse(null);
        if (orderFlower == null) {
            System.out.println("Order not found.");
            return;
        }
        orderFlower.setStatus(OrderStatus.CANCELED);
        orderRepository.save(orderFlower);
    }

}
