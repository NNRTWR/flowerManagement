package ku.cs.flowerManagement.service;

import com.google.gson.Gson;
import jakarta.persistence.EntityNotFoundException;
import ku.cs.flowerManagement.common.OrderStatus;

import ku.cs.flowerManagement.entity.Flower;
import ku.cs.flowerManagement.entity.Invoice;
import ku.cs.flowerManagement.entity.OrderItem;

import ku.cs.flowerManagement.model.OrderItemRequest;
import ku.cs.flowerManagement.repository.FlowerRepository;
import ku.cs.flowerManagement.repository.OrderRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private FlowerRepository flowerRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Get Orders
    public List<OrderItemRequest> getOrders() {
        List<OrderItem> orders = orderRepository.findAll();

        List<OrderItemRequest> orderFlowerRequests = new ArrayList<>();

        for (OrderItem ord:orders) {
            OrderItemRequest orderFlowerRequest = modelMapper.map(ord, OrderItemRequest.class);
            orderFlowerRequest.setFName(ord.getFlower().getFName());
            orderFlowerRequest.setFID(ord.getFlower().getFID());

            orderFlowerRequest.setFlowerPrice(ord.getPrice());

            orderFlowerRequests.add(orderFlowerRequest);
        }


        return orderFlowerRequests;
    }
    public Page<OrderItem> getOrderPage(int page, int size) {   //สำหรับ pagination
        Pageable pageable = PageRequest.of(page, size);
        return orderRepository.findAll(pageable);
    }

    public int getTotalOrderCount() {
        List<OrderItem> orders = orderRepository.findAll();
        return orders.size();
    }


    // Get order By Id
    public OrderItemRequest getOrderById(int id) {
        OrderItem orderFlower = orderRepository.findById(id).orElse(null);
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
        Flower flower = flowerRepository.findById(orderFlowerRequest.getFID()).orElse(null);
        if(flower == null) return;
        System.out.println("Price///////////////////// : "+flower.getPrice() );
        System.out.println("Quantity///////////////////: " + orderFlowerRequest.getOrderQuantity());
        orderFlower.setFlower(flower);
        orderFlower.setPrice(flower.getPrice() * orderFlowerRequest.getOrderQuantity());
        orderFlower.setStatus(OrderStatus.PENDING);
        orderFlower.setQuantity(orderFlowerRequest.getOrderQuantity());
//        orderFlower.setPlant_status(PlantStatus.SEEDING);
        orderFlower.setOrder_method(orderFlowerRequest.getOrder_method());
        orderRepository.save(orderFlower);
    }

    public List<OrderItemRequest> getPendingOrders() {
        List<OrderItem> orders = orderRepository.findByStatus(OrderStatus.PENDING);
        List<OrderItemRequest> orderFlowerRequests = new ArrayList<>();
        for (OrderItem ord : orders) {
            OrderItemRequest orderFlowerRequest = modelMapper.map(ord, OrderItemRequest.class);
            orderFlowerRequest.setFName(ord.getFlower().getFName());
            orderFlowerRequest.setFID(ord.getFlower().getFID());
            orderFlowerRequest.setFlowerPrice(ord.getPrice());
            orderFlowerRequests.add(orderFlowerRequest);
        }
        return orderFlowerRequests;
    }
    public List<OrderItemRequest> getCancelOrders() {
        List<OrderItem> orders = orderRepository.findByStatus(OrderStatus.CANCELED);
        List<OrderItemRequest> orderFlowerRequests = new ArrayList<>();
        for (OrderItem ord : orders) {
            OrderItemRequest orderFlowerRequest = modelMapper.map(ord, OrderItemRequest.class);
            orderFlowerRequest.setFName(ord.getFlower().getFName());
            orderFlowerRequest.setFID(ord.getFlower().getFID());
            orderFlowerRequest.setFlowerPrice(ord.getPrice());
            orderFlowerRequests.add(orderFlowerRequest);
        }
        return orderFlowerRequests;
    }
    public List<OrderItemRequest> getCompleteOrders() {
        List<OrderItem> orders = orderRepository.findByStatus(OrderStatus.CANCELED);
        List<OrderItemRequest> orderFlowerRequests = new ArrayList<>();
        for (OrderItem ord : orders) {
            OrderItemRequest orderFlowerRequest = modelMapper.map(ord, OrderItemRequest.class);
            orderFlowerRequest.setFName(ord.getFlower().getFName());
            orderFlowerRequest.setFID(ord.getFlower().getFID());
            orderFlowerRequest.setFlowerPrice(ord.getPrice());
            orderFlowerRequests.add(orderFlowerRequest);
        }
        return orderFlowerRequests;
    }

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

    public void confirmOrderById(int id) {
        OrderItem orderFlower = orderRepository.findById(id).orElse(null);
        if (orderFlower == null) {
            System.out.println("Order not found.");
            return;
        }
        orderFlower.setStatus(OrderStatus.COMPLETED);
        orderRepository.save(orderFlower);
    }
}
