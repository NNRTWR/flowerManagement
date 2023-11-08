package ku.cs.flowerManagement.service;

import ku.cs.flowerManagement.entity.Allocate;
import ku.cs.flowerManagement.entity.Flower;
import ku.cs.flowerManagement.entity.OrderItem;
import ku.cs.flowerManagement.entity.Stock;
import ku.cs.flowerManagement.model.AllocateModel;
import ku.cs.flowerManagement.model.AllocateRequest;
import ku.cs.flowerManagement.repository.AllocateRepository;
import ku.cs.flowerManagement.repository.OrderRepository;
import ku.cs.flowerManagement.repository.StockRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AllocateServiceTest {
    @InjectMocks
    private AllocateService allocateService;

    @Mock
    private AllocateRepository allocateRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private StockRepository stockRepository;

    @Test
    public void testCreateAllocate() {
        int OID = 1;
        int SID = 2;
        double amount = 10.0;
        int stockChanged = 5;

        OrderItem mockOrderItem = new OrderItem();
        Stock mockStock = new Stock();
        when(orderRepository.findById(OID)).thenReturn(Optional.of(mockOrderItem));
        when(stockRepository.findById(SID)).thenReturn(Optional.of(mockStock));

        allocateService.createAllocate(OID, SID, amount, stockChanged);


        Allocate expectedAllocate = new Allocate();
        expectedAllocate.setOID(mockOrderItem);
        expectedAllocate.setSID(mockStock);
        expectedAllocate.setAmount(amount);
        expectedAllocate.setStockChanged(stockChanged);

        verify(allocateRepository).save(expectedAllocate);
    }


    @Test
    public void testFindAllAllocate() {
        Flower flower1 = new Flower();
        flower1.setFName("rose");
        Flower flower2 = new Flower();
        flower2.setFName("lily");

        Allocate allocate1 = new Allocate();
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setFlower(flower1);
        allocate1.setOID(orderItem1);
        allocate1.setAmount(9.0);
        allocate1.setStockChanged(2);

        Allocate allocate2 = new Allocate();
        OrderItem orderItem2 = new OrderItem();
        orderItem2.setFlower(flower2);
        allocate2.setOID(orderItem2);
        allocate2.setAmount(17.0);
        allocate2.setStockChanged(4);

        List<Allocate> allocateList = new ArrayList<>();
        allocateList.add(allocate1);
        allocateList.add(allocate2);

        when(allocateRepository.findAll()).thenReturn(allocateList);

        AllocateRequest allocateRequest = allocateService.findAllAllocate();

        List<AllocateModel> allocateModels = allocateRequest.getAllocateModels();

        assertEquals(2, allocateModels.size());

        assertEquals("lily", allocateModels.get(0).getFName());
        assertEquals(17.0, allocateModels.get(0).getTotal(), 0.001);
        assertEquals(4, allocateModels.get(0).getStockFlower());

        assertEquals("rose", allocateModels.get(1).getFName());
        assertEquals(9.0, allocateModels.get(1).getTotal(), 0.001);
        assertEquals(2, allocateModels.get(1).getStockFlower());

    }

    @Test
    public void testGetAllAllocate() {
        List<Allocate> allocateList = Arrays.asList(new Allocate(), new Allocate());

        PageRequest pageRequest = PageRequest.of(0, 15);
        Page<Allocate> page = new PageImpl<>(allocateList, pageRequest, allocateList.size());

        when(allocateRepository.findAll(pageRequest)).thenReturn(page);

        Page<Allocate> result = allocateService.getAllAllocate(0, 15);

        assertEquals(allocateList.size(), result.getTotalElements());
    }


}
