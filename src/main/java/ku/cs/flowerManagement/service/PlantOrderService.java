package ku.cs.flowerManagement.service;

import ku.cs.flowerManagement.adapter.DateTimeComparator;
import ku.cs.flowerManagement.common.FlowerStatus;
import ku.cs.flowerManagement.entity.Flower;
import ku.cs.flowerManagement.entity.GardenerOrder;
import ku.cs.flowerManagement.entity.PlantOrder;

import ku.cs.flowerManagement.entity.*;

import ku.cs.flowerManagement.model.PlantOrderRequest;
import ku.cs.flowerManagement.repository.FlowerRepository;
import ku.cs.flowerManagement.repository.GardenerOrderRepository;
import ku.cs.flowerManagement.repository.PlantOrderRepository;
import ku.cs.flowerManagement.repository.StockRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class
PlantOrderService {
    @Autowired
    private PlantOrderRepository plantOrderRepository;

    @Autowired
    private FlowerRepository flowerRepository;

    @Autowired
    private GardenerOrderService gardenerOrderService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private GardenerOrderRepository gardenerOrderRepository;
    @Autowired
    private StockRepository stockRepository;

    public int currentPID;


    //เอาข้อมูลการปลูกทั้งหมดในแปลงที่เลือก
    public List<PlantOrder> getAllPlantOrderByPID(int PID){
        System.out.println("แปลงที่ " + PID); // เลือกแปลงไหนนะ
        currentPID = PID;
        List<PlantOrder> listPlantOrder = plantOrderRepository.findByPID(PID); //หาว่าเลขที่่แปลงนี้มีปลูกดอกไม้ยัง
        if (listPlantOrder.isEmpty())
            return null;
        else {
            setFlowerOrderStatus(listPlantOrder); //ไปเรียก set สถานะของดอกไม้ในแปลงก่อน
            return listPlantOrder;
        }
    }


    //เอาข้อมูลการปลูกทั้งหมดที่ยังไม่เก็บเกี่ยวในแปลงที่เลือก
    public List<PlantOrder> getAllPlantOrderButNoStockByPID(int PID){
        currentPID = PID;
        System.out.println("แปลงที่ " + PID); // เลือกแปลงไหนนะ
        List<PlantOrder> listPlantOrder = plantOrderRepository.findByPID(PID); //หาว่าเลขที่่แปลงนี้ปลูกดอกไม้ยัง
        System.out.println("ก่อน System.out.println(listPlantOrder)");
        for (PlantOrder o:listPlantOrder) {
            System.out.println(o.getId());
        }

        //แปลงนี้เคยมีการปลูกมาก่อนรึเปล่า
        if(listPlantOrder == null){ //ถ้าแปลงนี้ไม่เคยมีการปลูกมาก่อน
            System.out.println("ถ้าแปลงนี้ไม่เคยมีการปลูกมาก่อน");
        }
        else {
            listPlantOrder = findPlantNoStock(listPlantOrder); //หาว่ามีรอบการปลูกที่ยังไม่เก็บเกี่ยวมั้ย
            System.out.println("ถ้าแปลงนี้เคยมีการปลูกมาก่อน");
        }

        //มี stock รึเปล่า ถ้าไม่มี = แปลงว่าง
        if (listPlantOrder.isEmpty()){ //ไม่เคยมีการปลูกที่แปลงนีมาก่อน หรือ รอบการปลูกพวกนั้นเก็บเกี่ยวหมดแล้ว = แปลงนี้ปลูกได้
            System.out.println("รอบการปลูกพวกนั้นเก็บเกี่ยวหมดแล้ว");
            return null;
        }
        else {
            System.out.println("รอบการปลูกพวกนั้นยังไม่ได้เก็บเกี่ยว");
            setFlowerOrderStatus(listPlantOrder); //ไปเรียก set สถานะของดอกไม้ในแปลงก่อน
            return listPlantOrder;
        }
    }


    //get plantOrder ที่ยังไม่เก็บเกี่ยว
    public List<PlantOrder> findPlantNoStock(List<PlantOrder> plantOrder){
        List<PlantOrder> listPlantOrder = new ArrayList<>();
        for (PlantOrder order : plantOrder){
            if(order.getStock() == null){ //รอบการปลูกนี้ยังไม่เก็บเกี่ยว
                System.out.println("แปลงที่: " + order.getPID() + "รอบการปลูก: " + order.getTimePlant() + " ยังไม่เก็บเกี่ยว");
                listPlantOrder.add(order);
            }
        }
        return listPlantOrder;
    }


    public List<PlantOrder> getAllPlantOrder(){ //เอาแค่คำสั่งปลูกทั้งหมด
        System.out.println("ก่อน List<PlantOrder> listPlantOrder = plantOrderRepository.findAll(); ที่ getAllPlantOrder ");
        List<PlantOrder> listPlantOrder = plantOrderRepository.findAll();
        System.out.println("หลัง List<PlantOrder> listPlantOrder = plantOrderRepository.findAll(); ที่ getAllPlantOrder ");
        if (listPlantOrder.isEmpty()) {
            return null;
        }
        else {
            setFlowerOrderStatus(listPlantOrder);
        }
        return listPlantOrder;
    }


    //เอาคำสั่งปลูกทั้งหมดทุกแปลงที่ยังไม่เก็บเกี่ยว
    public List<PlantOrder> getAllPlantOrderButNoStock(){
        List<PlantOrder> listPlantOrder = findPlantNoStock(plantOrderRepository.findAll());
        if (listPlantOrder == null) {
            return null;
        }
        else {
            setFlowerOrderStatus(listPlantOrder);
        }
        return listPlantOrder;
    }


    //set สถานะของดอกไม้ในแปลง (update สถานะแหละ)
    //พยายามให้มันยืดหยุ่นเผื่อว่าแปลงนึงปลูกได้หลายครั้ง
    public void setFlowerOrderStatus(List<PlantOrder> plantOrder){
        for (PlantOrder order: plantOrder) {
            order.setFlowerStatus(getFlowerStatus(order));
            plantOrderRepository.save(order);
        }
    }


    //ต้องแก้เพราะ เก็บได้หลายครั้งก็มี
    public FlowerStatus getFlowerStatus(PlantOrder plantOrder){ //หา status ของดอกไม้ในแปลงนั้น
        long period = ChronoUnit.DAYS.between(plantOrder.getTimePlant(), LocalDateTime.now()); //ระยะเวลาตั้งแต่ปลูกจนวันที่ปัจจุบัน = ปลูกมาได้กี่วันแล้ว

        System.out.println("period คือ "+ period);
        //อาจจะแยกไปเป็นอีก method ได้
        Flower flower = plantOrder.getFlower();
        long seed = flower.getSeedPeriod();
        long sprout = seed + flower.getSproutPeriod();
        long growing = sprout + flower.getGrowingPeriod();
        long fullyGrown = growing + flower.getFullyGrownPeriod();
        long harvest = fullyGrown + flower.getFullyGrownPeriod();

        if (seed > period)
            return FlowerStatus.SEED;
        else if (sprout > period)
            return FlowerStatus.SPROUT;
        else if (growing > period)
            return FlowerStatus.GROWING;
        else if (fullyGrown > period)
            return FlowerStatus.FULLY_GROWN;
        else if (harvest > period)
            return FlowerStatus.HARVEST;
        else
            return FlowerStatus.DEAD;

        //เก็บเกี่ยวแล้วเป็น ฟูลลี่
        //death
    }



    //ไม่ได้ใช้ เป็น ปลูกของเก่าที่เอา Request มาทั้งก้อนแล้วค่อยมาแตกใน method นี้
//    public boolean createPlantOrder(PlantOrderRequest plantOrder, DateTimeComparator dateTimeComparator){ //ปลูกตาม order ที่ได้รับจากฝ่ายอื่น
////        PlantOrder record = modelMapper.map(plantOrder, PlantOrder.class); //map จาก PlantOrderRequest เป็น PlantOrder
////        GardenerOrder order = modelMapper.map(gardenerOrder,GardenerOrder.class);
//
//        PlantOrder record = new PlantOrder();
//        GardenerOrder order = gardenerOrderRepository.findById(plantOrder.getPlant_order_ID()).get();
//        Flower flower = flowerRepository.findById(plantOrder.getFlowerID()).get(); //หาดอกไม้ที่ปลูก
//
//        if(!checkPlantOrder(order,flower)){ //check แล้วพบว่าดอกไม่ที่ปลูกกับ order ไม่ตรงกัน
//            return false;
//        }
//
//        record.setGardener_order(order); // รอบการปลูกนี้มาจาก plantOrder อันนี้
//        record.setQuantity(order.getQuantity()); //ตอนนี้ปลูกดอกไม้ตาม order แบบเป๊ะๆอยู๋
//
//        record.setFlower(flower); //แปลงนี้ปลูกดอกนี้นะ
//        record.setPID(currentPID); //ปลูกที่แปลงไหน
//        record.setTimePlant(LocalDateTime.now()); //วันเวลาที่ปลูก
////        System.out.println("ก่อน plantOrderRepository.save(record) ที่ createPlantOrder");
//        plantOrderRepository.save(record);
////        System.out.println("หลัง plantOrderRepository.save(record) ที่ createPlantOrder");
//        gardenerOrderService.setIn_ProcessOrder(order);
//        return true;
//    }



    public boolean createPlantOrder(UUID gardenerId, UUID flowerId, DateTimeComparator dateTimeComparator){ //ปลูกตาม order ที่ได้รับจากฝ่ายอื่น

        System.out.println(gardenerId);
        System.out.println(flowerId);
        PlantOrder record = new PlantOrder();
        GardenerOrder order = gardenerOrderRepository.findById(gardenerId).get();
        Flower flower = flowerRepository.findById(flowerId).get(); //หาดอกไม้ที่ปลูก

        if(!checkPlantOrder(order,flower)){ //check แล้วพบว่าดอกไม่ที่ปลูกกับ order ไม่ตรงกัน
            return false;
        }

        record.setGardener_order(order); // รอบการปลูกนี้มาจาก plantOrder อันนี้
        record.setQuantity(order.getQuantity()); //ตอนนี้ปลูกดอกไม้ตาม order แบบเป๊ะๆอยู๋
        record.setFlower(flower); //แปลงนี้ปลูกดอกนี้นะ
        record.setHarvestable(flower.getHow_to_harvest());

        record.setPID(currentPID); //ปลูกที่แปลงไหน
        record.setTimePlant(LocalDateTime.now()); //วันเวลาที่ปลูก
//        System.out.println("ก่อน plantOrderRepository.save(record) ที่ createPlantOrder");
        plantOrderRepository.save(record);
//        System.out.println("หลัง plantOrderRepository.save(record) ที่ createPlantOrder");
        gardenerOrderService.setIn_ProcessOrder(order);
        return true;
    }


    // check ก่อนว่า ดอกไม้ที่กดปลูก กับดอกไม้ใน order ตรงกันมั้ย
    public boolean checkPlantOrder(GardenerOrder gardenerOrder, Flower flower){
        if(gardenerOrder.getFlower() == flower){
            System.out.println(flower.getFName());
            return true;
        }
        else
            return false;
    }

    //Donut
    public List<PlantOrder> getAllPLantOrder() {
        return plantOrderRepository.findAll();
    }

    public List<Statistic> getAllGardenWithFlower(){ //เหมือนมันน่าจะผิดนะ
        List<Statistic> statistics = new ArrayList<>();
        List<Flower> flowers = flowerRepository.findAll();
        List<PlantOrder> plantOrders = plantOrderRepository.findAll();

        for (Flower flower : flowers) {
            Statistic statistic = new Statistic();
            statistic.setFlower(flower);
            statistics.add(statistic);
        }

        for (PlantOrder plantOrder : plantOrders) {
            Flower gardenFlower = plantOrder.getFlower();
            for (Statistic statistic : statistics) {
                if (statistic.getFlower() == gardenFlower) {
                    statistic.setPlantOrder(new ArrayList<>());
                    statistic.getPlantOrder().add(plantOrder);
                    break;
                }
            }
        }
        statistics.removeIf(statistic -> statistic.getPlantOrder() == null || statistic.getPlantOrder().isEmpty());
        return statistics;
    }
    //เก็บเกี่ยวกับจัดการตาย
    //dead plant management
    public void plantWasDied(PlantOrderRequest plantOrderRequest){
        PlantOrder record = modelMapper.map(plantOrderRequest, PlantOrder.class);
        PlantOrder plantOrder = plantOrderRepository.findById(plantOrderRequest.getFlowerID()).get();
        //alive-dead
        plantOrder.setTotal(plantOrder.getTotal()-plantOrderRequest.getDeadPlant());
        record.setTotal(plantOrder.getTotal());
        plantOrderRepository.save(record);
    }
        //harvest
    public void harvest(PlantOrderRequest plantOrderRequest){
        PlantOrder record = modelMapper.map(plantOrderRequest,PlantOrder.class);
        //managing stock
        Stock stock = record.getStock();
        stock.setQuantity(record.getQuantity());
        stock.setTotal(record.getTotal());
        //still harvestable
        if(record.getHarvestable()>1){
            record.setFlowerStatus(FlowerStatus.FULLY_GROWN);
        }
        else {//died set stock to zero show as empty field
        record.setFlowerStatus(FlowerStatus.DEAD);
        record.setStock(null);
        }

        plantOrderRepository.save(record);
        stockRepository.save(stock);
    }


}
