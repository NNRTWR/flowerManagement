package ku.cs.flowerManagement.service;

import ku.cs.flowerManagement.common.OrderStatus;
import ku.cs.flowerManagement.entity.OrderItem;
import ku.cs.flowerManagement.model.OrderItemRequest;
import ku.cs.flowerManagement.repository.FlowerRepository;
import ku.cs.flowerManagement.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    // Get Orders
//    public List<OrderFlowerRequest> getOrders() {
//        List<OrderItem> orders = orderRepository.findAll();
//        List<OrderFlowerRequest> orderFlowerRequests = new ArrayList<>();
//        for (OrderItem ord:orders) {
//            OrderFlowerRequest orderFlowerRequest = modelMapper.map(ord, OrderFlowerRequest.class);
//            orderFlowerRequest.setFName(ord.getFlower().getFName());
//            orderFlowerRequest.setFID(ord.getFlower().getFID());
//            orderFlowerRequests.add(orderFlowerRequest);
//        }
//        return orderFlowerRequests;
//    }
//
//    // Get order By Id
//    public OrderFlowerRequest getOrderById(int id) {
//        OrderItem orderFlower = orderRepository.findById(id).orElse(null);
//        if (orderFlower == null) {
//            throw new EntityNotFoundException();
//        }
//        OrderFlowerRequest orderFlowerRequest = modelMapper.map(orderFlower, OrderFlowerRequest.class);
//        orderFlowerRequest.setFName(orderFlower.getFlower().getFName());
//        orderFlowerRequest.setFID(orderFlower.getFlower().getFID());
//        return orderFlowerRequest;
//    }
//
//    // Create Order
//    public void createOrder(OrderFlowerRequest orderFlowerRequest) {
//        OrderItem orderFlower = modelMapper.map(orderFlowerRequest, OrderItem.class);
//        Flower flower = flowerRepository.findById(orderFlowerRequest.getFID()).orElse(null);
//        if(flower == null) return;
//        orderFlower.setFlower(flower);
//        orderFlower.setPrice(orderFlowerRequest.getFlowerPrice()*orderFlowerRequest.getOrderQuantity());
//        orderFlower.setStatus(OrderStatus.PENDING);
//        orderFlower.setPlant_status(FlowerStatus.SEED);
//        orderFlower.setOrder_method(orderFlowerRequest.getOrder_method());
//        orderRepository.save(orderFlower);
//    }

    // Complete order
    public void completeOrderById(int id) {
        OrderItem orderFlower = orderRepository.findById(id).orElse(null);
        if (orderFlower == null) {
            System.out.println("Order not found.");
            return;
        }
        orderFlower.setStatus(OrderStatus.COMPLETED);
        orderRepository.save(orderFlower);
    }

    // Cancel Order
    public void cancelOrderById(int id) {
        OrderItem orderFlower = orderRepository.findById(id).orElse(null);
        if (orderFlower == null) {
            System.out.println("Order not found.");
            return;
        }
        orderFlower.setStatus(OrderStatus.CANCELED);
        orderRepository.save(orderFlower);
    }

    public void addOrder(OrderItemRequest orderItem){
        OrderItem record = modelMapper.map(orderItem, OrderItem.class);
        record.setFlower(flowerRepository.findById(orderItem.getFlowerID()).get());
        record.setStatus(OrderStatus.PENDING);
        orderRepository.save(record);
    }

    public List<OrderItem> getAllOrderStatus(Comparator comparator){ //เอา order ที่ต้องปลูกทั้งหมดออกมา
        List<OrderItem> orders = orderRepository.findByStatus(OrderStatus.PENDING);
        Collections.sort(orders, comparator );
        return orders;
    }

    public OrderItem getOldestOrderStatus(Comparator comparator){
        return getAllOrderStatus(comparator).get(0);
    }

    public void seIn_ProcessOrder(Comparator comparator){ // set status ของ order เป็น confirm
        System.out.println("ก่อน getOldestOrderStatus ที่ setStatusOrder");
        OrderItem orderItem = getOldestOrderStatus(comparator);
        orderItem.setStatus(OrderStatus.IN_PROCESS);
        System.out.println(orderItem.getStatus());
        orderRepository.save(orderItem);
        System.out.println("หลัง getOldestOrderStatus ที่ setStatusOrder");
    }
}
