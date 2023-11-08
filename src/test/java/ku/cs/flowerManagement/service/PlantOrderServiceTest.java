package ku.cs.flowerManagement.service;

import ku.cs.flowerManagement.common.FlowerStatus;
import ku.cs.flowerManagement.common.OrderStatus;
import ku.cs.flowerManagement.entity.Flower;
import ku.cs.flowerManagement.entity.GardenerOrder;
import ku.cs.flowerManagement.entity.PlantOrder;
import ku.cs.flowerManagement.entity.Stock;
import ku.cs.flowerManagement.model.PlantOrderRequest;
import ku.cs.flowerManagement.repository.PlantOrderRepository;
import ku.cs.flowerManagement.repository.StockRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class PlantOrderServiceTest {
    @InjectMocks
    private PlantOrderService plantOrderService;

    @Mock
    private PlantOrderRepository plantOrderRepository;

    @Mock
    private StockRepository stockRepository;



    @Test //ทดสอบการเอาแปลงเลขที่ PID ที่ยังไม่เก็บเกี่ยวออกมา >>> กรณี ไม่มีแปลงไหนที่กำลังปลูก
    public void testGetPlantOrderButNoHarvestedByPID(){
        int testPID = 2;
        List<PlantOrder> mockPlantOrders = new ArrayList<>();

        when(plantOrderRepository.findAllByPID(testPID)).thenReturn(mockPlantOrders);

        PlantOrder result = plantOrderService.getPlantOrderButNoHarvestedByPID(testPID);

        assertNull(result); //แปลงที่2ยังไม่ได้ปลูกอะไร
    }

    @Test  //ติดที่ ChronoUnit รันไม่ได้
    public void testGetAllPlantOrder() {
        // สร้างรายการ PlantOrder จำลองที่คาดหวัง
        List<PlantOrder> mockPlantOrderList = createSamplePlantOrders();

        // เมื่อ plantOrderRepository.findAll() ถูกเรียก
        when(plantOrderRepository.findAll()).thenReturn(mockPlantOrderList);

        List<PlantOrder> result = plantOrderService.getAllPlantOrder();
        // ตรวจสอบผลลัพธ์ที่คาดหวัง
        assertEquals(mockPlantOrderList, result);
    }


    @Test //ทดสอบการเอาแปลงเลขที่ PID ที่เก็บเกี่ยวออกมา >>> กรณีมีแปลงที่ปลูกแล้ว และยังไม่ได้เก็บเกี่ยว
    public void testGetPlantOrderButNoHarvestedByPIDForHaveNoHarvested(){
        int testPID = 2;

        PlantOrder plantOrder = new PlantOrder();
        plantOrder.setPID(2);
        plantOrder.setFlowerStatus(FlowerStatus.HARVEST);
        plantOrderRepository.save(plantOrder);


        List<PlantOrder> mockPlantOrders = new ArrayList<>();
        mockPlantOrders.add(plantOrder);
        when(plantOrderRepository.findAllByPID(testPID)).thenReturn(mockPlantOrders);

        PlantOrder result = plantOrderService.getPlantOrderButNoHarvestedByPID(testPID);

        assertEquals(plantOrder,result); //แปลงที่2ปลูกแล้ว
    }


    @Test //ทดสอบการเอาแปลงเลขที่ PID ที่เก็บเกี่ยวออกมา >>> กรณีมีแปลงที่ปลูกแล้ว และยังไม่ได้เก็บเกี่ยว
    public void testGetPlantOrderButNoHarvestedByPIDForHaveHarvested(){
        int testPID = 2;

        PlantOrder plantOrder = new PlantOrder();
        plantOrder.setPID(2);
        plantOrder.setFlowerStatus(FlowerStatus.HARVESTED);
        plantOrderRepository.save(plantOrder);


        List<PlantOrder> mockPlantOrders = new ArrayList<>();
        mockPlantOrders.add(plantOrder);
        when(plantOrderRepository.findAllByPID(testPID)).thenReturn(mockPlantOrders);

        PlantOrder result = plantOrderService.getPlantOrderButNoHarvestedByPID(testPID);

        assertNull(result); //แปลงที่2ปลูกแล้ว และเก็บเกี่ยวแล้ว
    }


    @Test //ทดสอบการหาแปลงเี่ยังไม่เก็บเกี่ยวออกมา >>> กรณีมีทั้งแปลงที่เก็บเกี่ยวแล้ว และยังไม่ได้เก็บเกี่ยว
    public void testFindPlantNoHarvestedForHaveNoHarvested() {
        List<PlantOrder> plantOrders = new ArrayList<>();

        //แปลงที่เก็บเกี่ยวแล้ว
        PlantOrder harvestedPlantOrder = mock(PlantOrder.class);
        when(harvestedPlantOrder.getFlowerStatus()).thenReturn(FlowerStatus.HARVESTED);

        // แปลงที่ยังไม่เก็บเกี่ยว
        PlantOrder nonHarvestedPlantOrder = mock(PlantOrder.class);
        when(nonHarvestedPlantOrder.getFlowerStatus()).thenReturn(FlowerStatus.SEED);


        plantOrders.add(harvestedPlantOrder);
        plantOrders.add(nonHarvestedPlantOrder);


        PlantOrder result = plantOrderService.findPlantNoHarvested(plantOrders);

        assertEquals(nonHarvestedPlantOrder, result); // result คือ nonHarvestedPlantOrderer
    }


    @Test //ทดสอบการหาแปลงเี่ยังไม่เก็บเกี่ยวออกมา  >>> กรณีมีแค่แปลงที่เก็บเกี่ยวแล้ว
    public void testFindPlantNoHarvestedForHaveHarvested() {
        List<PlantOrder> plantOrders = new ArrayList<>();

        //แปลงที่เก็บเกี่ยวแล้ว
        PlantOrder harvestedPlantOrder = mock(PlantOrder.class);
        when(harvestedPlantOrder.getFlowerStatus()).thenReturn(FlowerStatus.HARVESTED);
        PlantOrder nonHarvestedPlantOrder = mock(PlantOrder.class);
        when(nonHarvestedPlantOrder.getFlowerStatus()).thenReturn(FlowerStatus.HARVESTED);


        plantOrders.add(harvestedPlantOrder);
        plantOrders.add(nonHarvestedPlantOrder);


        PlantOrder result = plantOrderService.findPlantNoHarvested(plantOrders);

        assertNull(result);
    }


    @Test //ทดสอบการหาแปลงทั้งหมดที่ยังไม่ได้เก็บเกี่ยวออกมา
    public void testFindAllPlantNoHarvested() {
        PlantOrder mockPlantOrder1 = mock(PlantOrder.class);
        PlantOrder mockPlantOrder2 = mock(PlantOrder.class);

        //แปลงที่เก็บเกี่ยวแล้ว
        when(mockPlantOrder1.getFlowerStatus()).thenReturn(FlowerStatus.HARVESTED);
        when(mockPlantOrder1.getTotal()).thenReturn(100);

        //แปลงที่ยังไม่ได้เก็บเกี่ยว
        when(mockPlantOrder2.getFlowerStatus()).thenReturn(FlowerStatus.SEED);
        when(mockPlantOrder2.getTotal()).thenReturn(50);

        List<PlantOrder> plantOrders = new ArrayList<>();
        plantOrders.add(mockPlantOrder1);
        plantOrders.add(mockPlantOrder2);


        List<PlantOrder> result = plantOrderService.findAllPlantNoHarvested(plantOrders);

        assertEquals(1, result.size());

        verify(mockPlantOrder2).getFlowerStatus();
        verify(mockPlantOrder2).getTotal();
    }

    @Test  //ติดที่ ChronoUnit รันไม่ได้
    public void testGetAllPlantOrderButNoHarvested() {

        PlantOrderRepository mockPlantOrderRepository = mock(PlantOrderRepository.class);
        when(mockPlantOrderRepository.findAll()).thenReturn(createSamplePlantOrders());


        PlantOrderService plantOrderService = new PlantOrderService();
        plantOrderService.setPlantOrderRepository(mockPlantOrderRepository);

        List<PlantOrder> result = plantOrderService.getAllPlantOrderButNoHarvested();


        assertEquals(2, result.size());

        verify(mockPlantOrderRepository).findAll();
    }

    @Test  //ติดที่ ChronoUnit รันไม่ได้
    public void testGetAllPIDWithPlantOrder() {
        PlantOrderRepository mockPlantOrderRepository = mock(PlantOrderRepository.class);
        when(mockPlantOrderRepository.findAll()).thenReturn(createSamplePlantOrders());

        plantOrderService.setPlantOrderRepository(mockPlantOrderRepository);

        Map<Integer, PlantOrder> result = plantOrderService.getAllPIDWithPlantOrder();

        assertEquals(15, result.size());

        verify(mockPlantOrderRepository).findAll();
    }

    @Test
    public void testHarvest() {
        int PID = 12;
        int harvestable = 1; // จำนวนการเก็บเกี่ยวที่เหลือ

        PlantOrder mockPlantOrder = createMockPlantOrder(PID, harvestable);

        when(plantOrderRepository.findByPID(PID)).thenReturn(mockPlantOrder);
        when(stockRepository.save(any(Stock.class))).thenReturn(new Stock());

        PlantOrderRequest plantOrderRequest = createPlantOrderRequest(PID);
        plantOrderService.harvest(plantOrderRequest);

        assertEquals(FlowerStatus.HARVESTED, mockPlantOrder.getFlowerStatus());
        verify(stockRepository, times(1)).save(any(Stock.class));
    }

    @Test
    public void testMultipleHarvest() {
        int PID = 12;
        int harvestable = 2; // จำนวนการเก็บเกี่ยวที่เหลือ

        PlantOrder mockPlantOrder = createMockPlantOrder(PID, harvestable);

        when(plantOrderRepository.findByPID(PID)).thenReturn(mockPlantOrder);
        when(stockRepository.save(any(Stock.class))).thenReturn(new Stock());

        PlantOrderRequest plantOrderRequest = createPlantOrderRequest(PID);
        plantOrderService.harvest(plantOrderRequest);

        assertEquals(FlowerStatus.FULLY_GROWN, mockPlantOrder.getFlowerStatus());
        verify(stockRepository, times(1)).save(any(Stock.class));
    }

    @Test
    public void testResetPlant() {
        int PID = 3;

        PlantOrder mockPlantOrder = new PlantOrder();

        when(plantOrderRepository.findByPID(PID)).thenReturn(mockPlantOrder);

        PlantOrderRequest request = new PlantOrderRequest();
        request.setPID(PID);

        PlantOrderRequest plantOrderRequest = createPlantOrderRequest(PID);
        plantOrderService.resetPlant(plantOrderRequest);

        // ตรวจสอบว่า total ถูกตั้งค่าเป็น -1 มั้ย และ plantOrderRepository.save() ถูกเรียกรึเปล่า
        assertEquals(-1, mockPlantOrder.getTotal());
        verify(plantOrderRepository, times(1)).save(mockPlantOrder);
    }

    @Test
    public void testPlantWasDiedAllFlower(){
        int PID = 5;
        PlantOrderRequest mockPlantOrderRequest = new PlantOrderRequest();
        mockPlantOrderRequest.setPID(PID);
        mockPlantOrderRequest.setDeadPlant(20);

        GardenerOrder gardenerOrder = new GardenerOrder();
        gardenerOrder.setStatus(OrderStatus.IN_PROCESS);

        PlantOrder mockPlantOrder = new PlantOrder();
        mockPlantOrder.setPID(PID);
        mockPlantOrder.setTotal(20);
        mockPlantOrder.setFlowerStatus(FlowerStatus.SPROUT);
        mockPlantOrder.setGardener_order(gardenerOrder);

        when(plantOrderRepository.findAllByPID(PID)).thenReturn(Collections.singletonList(mockPlantOrder));

        plantOrderService.plantWasDied(mockPlantOrderRequest);

        verify(plantOrderRepository).save(mockPlantOrder);
        assertEquals(0, mockPlantOrder.getTotal());
        assertEquals(FlowerStatus.DEAD, mockPlantOrder.getFlowerStatus());
        assertEquals(OrderStatus.FAIL, mockPlantOrder.getGardener_order().getStatus());

    }

    @Test
    public void testPlantWasDiedSomeFlower(){
        int PID = 5;
        PlantOrderRequest mockPlantOrderRequest = new PlantOrderRequest();
        mockPlantOrderRequest.setPID(PID);
        mockPlantOrderRequest.setDeadPlant(10);

        GardenerOrder gardenerOrder = new GardenerOrder();
        gardenerOrder.setStatus(OrderStatus.IN_PROCESS);

        PlantOrder mockPlantOrder = new PlantOrder();
        mockPlantOrder.setPID(PID);
        mockPlantOrder.setTotal(20);
        mockPlantOrder.setFlowerStatus(FlowerStatus.SPROUT);
        mockPlantOrder.setGardener_order(gardenerOrder);

        when(plantOrderRepository.findAllByPID(PID)).thenReturn(Collections.singletonList(mockPlantOrder));

        plantOrderService.plantWasDied(mockPlantOrderRequest);

        verify(plantOrderRepository).save(mockPlantOrder);
        assertEquals(10, mockPlantOrder.getTotal());
        assertEquals(FlowerStatus.SPROUT, mockPlantOrder.getFlowerStatus());
        assertEquals(OrderStatus.IN_PROCESS, mockPlantOrder.getGardener_order().getStatus());
    }


    private List<PlantOrder> createSamplePlantOrders() {
        PlantOrder plantOrder1 = new PlantOrder();
        plantOrder1.setFlowerStatus(FlowerStatus.HARVESTED);

        PlantOrder plantOrder2 = new PlantOrder();
        plantOrder2.setFlowerStatus(FlowerStatus.SEED);

        PlantOrder plantOrder3 = new PlantOrder();
        plantOrder3.setFlowerStatus(FlowerStatus.FULLY_GROWN);

        List<PlantOrder> plantOrders = new ArrayList<>();
        plantOrders.add(plantOrder1);
        plantOrders.add(plantOrder2);
        plantOrders.add(plantOrder3);

        return plantOrders;
    }

    private PlantOrder createMockPlantOrder(int PID, int harvestable) {
        Flower flower = new Flower();
        flower.setQuantity(15);

        GardenerOrder gardenerOrder = new GardenerOrder();

        PlantOrder plantOrder = new PlantOrder();
        plantOrder.setPID(PID);
        plantOrder.setFlower(flower);
        plantOrder.setQuantity(40);
        plantOrder.setFlowerStatus(FlowerStatus.HARVEST);
        plantOrder.setHarvestable(harvestable);
        plantOrder.setGardener_order(gardenerOrder);

        return plantOrder;
    }

    private PlantOrderRequest createPlantOrderRequest(int PID) {
        PlantOrderRequest request = new PlantOrderRequest();
        request.setPID(PID);
        return request;
    }

}
