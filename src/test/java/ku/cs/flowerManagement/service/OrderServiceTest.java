package ku.cs.flowerManagement.service;

import ku.cs.flowerManagement.common.OrderStatus;
import ku.cs.flowerManagement.entity.Flower;
import ku.cs.flowerManagement.entity.OrderItem;
import ku.cs.flowerManagement.model.OrderItemRequest;
import ku.cs.flowerManagement.repository.FlowerRepository;
import ku.cs.flowerManagement.repository.OrderRepository;
import ku.cs.flowerManagement.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private FlowerRepository flowerRepository;

    @Spy
    ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCompleteOrderById() {
        int orderId = 1;
        OrderItem order = new OrderItem();
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        orderService.completeOrderById(orderId);

        assertEquals(OrderStatus.COMPLETED, order.getStatus());
        verify(orderRepository).save(order);
    }

    @Test
    public void testCancelOrderById() {
        int orderId = 1;
        OrderItem order = new OrderItem();
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        orderService.cancelOrderById(orderId);

        assertEquals(OrderStatus.CANCELED, order.getStatus());
        verify(orderRepository).save(order);
    }

    @Test
    public void testConfirmOrderById() {
        int orderId = 1;
        OrderItem order = new OrderItem();
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        orderService.confirmOrderById(orderId);

        assertEquals(OrderStatus.COMPLETED, order.getStatus());
        verify(orderRepository).save(order);
    }

}
