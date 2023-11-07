package ku.cs.flowerManagement;

import ku.cs.flowerManagement.common.OrderStatus;
import ku.cs.flowerManagement.entity.Flower;
import ku.cs.flowerManagement.model.OwnerViewSalesResponse;
import ku.cs.flowerManagement.repository.FlowerRepository;
import ku.cs.flowerManagement.repository.OrderRepository;
import ku.cs.flowerManagement.repository.StockRepository;
import ku.cs.flowerManagement.service.OwnerViewSalesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class OwnerViewSalesServiceTest {

    @InjectMocks
    private OwnerViewSalesService ownerViewSalesService;

    @Mock
    private StockRepository stockRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private FlowerRepository flowerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testGetAllSalesWithNoFlowers() {
        when(flowerRepository.findAll()).thenReturn(new ArrayList<>());

        List<OwnerViewSalesResponse> result = ownerViewSalesService.getAllSales();

        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetAllSalesWithNoStockAndOrder() {
        Flower flower1 = new Flower();
        flower1.setFID(1);
        flower1.setFName("Rose");
        when(flowerRepository.findAll()).thenReturn(List.of(flower1));
        when(stockRepository.sumQuantityFromStockByFlower(1)).thenReturn(0);
        when(orderRepository.sumFlowerStatusFromOrder(1, OrderStatus.FAIL)).thenReturn(0);
        when(orderRepository.sumFlowerStatusFromOrder(1, OrderStatus.COMPLETED)).thenReturn(0);

        List<OwnerViewSalesResponse> result = ownerViewSalesService.getAllSales();

        assertEquals(1, result.size());
        OwnerViewSalesResponse response = result.get(0);
        assertEquals("Rose", response.getFlowerName());
        assertEquals(0, response.getHarvestAmount());
        assertEquals(0, response.getDeadAmount());
        assertEquals(0, response.getSalesAmount());
        assertEquals(0, response.getBalanceAmount());
    }

    @Test
    public void testGetAllSalesWithNoCompletedOrder() {
        Flower flower1 = new Flower();
        flower1.setFID(1);
        flower1.setFName("Rose");
        when(flowerRepository.findAll()).thenReturn(List.of(flower1));
        when(stockRepository.sumQuantityFromStockByFlower(1)).thenReturn(100);
        when(orderRepository.sumFlowerStatusFromOrder(1, OrderStatus.FAIL)).thenReturn(10);
        when(orderRepository.sumFlowerStatusFromOrder(1, OrderStatus.COMPLETED)).thenReturn(0);

        List<OwnerViewSalesResponse> result = ownerViewSalesService.getAllSales();

        assertEquals(1, result.size());
        OwnerViewSalesResponse response = result.get(0);
        assertEquals("Rose", response.getFlowerName());
        assertEquals(100, response.getHarvestAmount());
        assertEquals(10, response.getDeadAmount());
        assertEquals(0, response.getSalesAmount());
        assertEquals(0, response.getBalanceAmount());
    }
}


