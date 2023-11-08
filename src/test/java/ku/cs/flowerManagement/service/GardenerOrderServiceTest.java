package ku.cs.flowerManagement.service;

import ku.cs.flowerManagement.common.OrderStatus;
import ku.cs.flowerManagement.entity.Flower;
import ku.cs.flowerManagement.entity.GardenerOrder;
import ku.cs.flowerManagement.entity.PlantOrder;
import ku.cs.flowerManagement.model.GardenerOrderRequest;
import ku.cs.flowerManagement.repository.FlowerRepository;
import ku.cs.flowerManagement.repository.GardenerOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GardenerOrderServiceTest {
    @InjectMocks
    private GardenerOrderService gardenerOrderService;

    @Mock
    private GardenerOrderRepository gardenerOrderRepository;

    @Mock
    private FlowerRepository flowerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllGardenerOrder() {
        List<GardenerOrder> mockOrders = new ArrayList<>();

        when(gardenerOrderRepository.findAll()).thenReturn(mockOrders);

        List<GardenerOrder> result = gardenerOrderService.getAllGardenerOrder(null);

        verify(gardenerOrderRepository).findAll();
        assertEquals(mockOrders, result);
    }

    @Test
    void testAddOrder() {
        GardenerOrderRequest gardenerOrderRequest = new GardenerOrderRequest();
        gardenerOrderRequest.setFlowerID(1);
        gardenerOrderRequest.setQuantity(5);

        Flower mockFlower = new Flower();
        when(flowerRepository.findByFID(gardenerOrderRequest.getFlowerID())).thenReturn(mockFlower);

        gardenerOrderService.addOrder(gardenerOrderRequest);

        GardenerOrder expectedOrder = new GardenerOrder();
        expectedOrder.setFlower(mockFlower);
        expectedOrder.setQuantity(gardenerOrderRequest.getQuantity());
        expectedOrder.setStatus(OrderStatus.PENDING);

        verify(gardenerOrderRepository).save(expectedOrder);
    }

    @Test
    void testSetInProcessOrder() {
        GardenerOrder mockGardenerOrder = new GardenerOrder();
        PlantOrder mockPlantOrder = new PlantOrder();

        when(gardenerOrderRepository.save(mockGardenerOrder)).thenReturn(mockGardenerOrder);

        gardenerOrderService.setIn_ProcessOrder(mockGardenerOrder, mockPlantOrder);

        verify(gardenerOrderRepository).save(mockGardenerOrder);

        assertEquals(OrderStatus.IN_PROCESS, mockGardenerOrder.getStatus());
    }
}
