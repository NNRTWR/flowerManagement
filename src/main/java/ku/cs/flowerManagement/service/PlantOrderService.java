package ku.cs.flowerManagement.service;

import ku.cs.flowerManagement.adapter.DateTimeComparator;
import ku.cs.flowerManagement.adapter.PlantComparator;
import ku.cs.flowerManagement.common.FlowerStatus;
import ku.cs.flowerManagement.common.OrderStatus;
import ku.cs.flowerManagement.entity.*;

import ku.cs.flowerManagement.model.PlantOrderRequest;
import ku.cs.flowerManagement.model.gRequest;
import ku.cs.flowerManagement.repository.FlowerRepository;
import ku.cs.flowerManagement.repository.GardenerOrderRepository;
import ku.cs.flowerManagement.repository.PlantOrderRepository;
import ku.cs.flowerManagement.repository.StockRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.Flow;


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
        List<PlantOrder> listPlantOrder = plantOrderRepository.findAllByPID(PID); //หาว่าเลขที่่แปลงนี้มีปลูกดอกไม้ยัง
        if (listPlantOrder.isEmpty())
            return null;
        else {
            setFlowerOrderStatus(listPlantOrder); //ไปเรียก set สถานะของดอกไม้ในแปลงก่อน
            return listPlantOrder;
        }
    }
    public List<PlantOrder> getAllPlantOrderPage(int pageNumber, int pageSize) {   //ทำ pagination
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<PlantOrder> plantOrderPage = plantOrderRepository.findAll(pageable);
        return plantOrderPage.getContent();
    }

    //เอาข้อมูลการปลูกทั้งหมดที่ยังไม่เก็บเกี่ยวในแปลงที่เลือก
    public PlantOrder getPlantOrderButNoHarvestedByPID(int PID){
        currentPID = PID;
        System.out.println("แปลงที่ " + PID); // เลือกแปลงไหนนะ
        List<PlantOrder> listPlantOrder = plantOrderRepository.findAllByPID(PID); //หาว่าเลขที่่แปลงนี้ปลูกดอกไม้ยัง
        PlantOrder plantOrder;

        //แปลงนี้เคยมีการปลูกมาก่อนรึเปล่า
        if(listPlantOrder == null){ //ถ้าแปลงนี้ไม่เคยมีการปลูกมาก่อน
            System.out.println("แปลงนี้ไม่เคยมีการปลูกมาก่อน");
        }
        else {
            System.out.println("แปลงนี้เคยมีการปลูกมาก่อน");
            plantOrder = findPlantNoHarvested(listPlantOrder); //หาว่ามีรอบการปลูกที่ยังไม่เก็บเกี่ยวมั้ย
            if(plantOrder == null)
                return null;
            else
                return plantOrder;
        }
        return null;
//
//        //มี stock รึเปล่า ถ้าไม่มี = แปลงว่าง
//        if (listPlantOrder.isEmpty()){ //ไม่เคยมีการปลูกที่แปลงนีมาก่อน หรือ รอบการปลูกพวกนั้นเก็บเกี่ยวหมดแล้ว = แปลงนี้ปลูกได้
//            System.out.println("รอบการปลูกพวกนั้นเก็บเกี่ยวหมดแล้ว");
//            return null;
//        }
//        else {
//            System.out.println("รอบการปลูกพวกนั้นยังไม่ได้เก็บเกี่ยว");
//            setFlowerOrderStatus(listPlantOrder); //ไปเรียก set สถานะของดอกไม้ในแปลงก่อน
//            return plantOrder;
//        }
    }



    public List<PlantOrder> getAllPlantOrder(){ //เอาแค่คำสั่งปลูกทั้งหมด
        List<PlantOrder> listPlantOrder = plantOrderRepository.findAll();
        if (listPlantOrder.isEmpty()) {
            return null;
        }
        else {
            listPlantOrder.sort(new PlantComparator());
            setFlowerOrderStatus(listPlantOrder);
        }
        return listPlantOrder;
    }


    public PlantOrder findPlantNoHarvested(List<PlantOrder> plantOrders){
//        List<PlantOrder> listPlantOrder = new ArrayList<>();

        int i=0;
        for (PlantOrder plantOrder : plantOrders){
            if(plantOrder.getFlowerStatus() != FlowerStatus.HARVESTED){ //รอบการปลูกนี้ยังไม่เก็บเกี่ยว
                System.out.println("แปลงที่: " + plantOrder.getPID() + "รอบการปลูก: " + plantOrder.getTimePlant() + " ยังไม่เก็บเกี่ยว");
                return plantOrder;
            }
            i++;
        }
        return null;
    }

    public List<PlantOrder> findAllPlantNoHarvested(List<PlantOrder> plantOrders){
        List<PlantOrder> listPlantOrder = new ArrayList<>();
        int i=0;
        for (PlantOrder plantOrder : plantOrders){
            if(plantOrder.getFlowerStatus() != FlowerStatus.HARVESTED && plantOrder.getTotal() != -1){ //รอบการปลูกนี้ยังไม่เก็บเกี่ยว
                System.out.println("แปลงที่: " + plantOrder.getPID() + "รอบการปลูก: " + plantOrder.getTimePlant() + " ยังไม่เก็บเกี่ยว");
                listPlantOrder.add(plantOrder);
            }
            i++;
        }
        return listPlantOrder;
    }

    //เอาคำสั่งปลูกทั้งหมดทุกแปลงที่ยังไม่เก็บเกี่ยว
    public List<PlantOrder> getAllPlantOrderButNoHarvested(){

        List<PlantOrder> plantOrders = plantOrderRepository.findAll();
        List<PlantOrder> plantOrder = findAllPlantNoHarvested(plantOrders);


        if (plantOrder == null) {
            return null;
        }
        else {
            setFlowerOrderStatus(plantOrder);
        }
        return plantOrder;
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
        System.out.println("แปลงที่ " + plantOrder.getPID());
        if(plantOrder.getFlowerStatus() != FlowerStatus.DEAD && plantOrder.getFlowerStatus() != FlowerStatus.HARVESTED){
            long period = ChronoUnit.DAYS.between(plantOrder.getTimePlant(), LocalDateTime.now()) + 1; //ระยะเวลาตั้งแต่ปลูกจนวันที่ปัจจุบัน = ปลูกมาได้กี่วันแล้ว นับวันที่ปลูกเป็นวันที่ 1

            System.out.println("period คือ "+ period);

            Flower flower = plantOrder.getFlower();
            int fullyHarvest = flower.getHow_to_harvest();
            int canHarvest = plantOrder.getHarvestable();
            int harvested = fullyHarvest - canHarvest;

            long seed = flower.getSeedPeriod();
            long sprout = seed + flower.getSproutPeriod();
            long growing = sprout + flower.getGrowingPeriod();
            long fullyGrown = growing + flower.getFullyGrownPeriod();
            long harvest = fullyGrown + flower.getHarvestPeriod();


            if (seed >= period)
                return FlowerStatus.SEED;
            else if (sprout >= period)
                return FlowerStatus.SPROUT;
            else if (growing >= period)
                return FlowerStatus.GROWING;
            else if (fullyGrown + ((long) harvested * (flower.getFullyGrownPeriod() + flower.getHarvestPeriod())) >= period && flower.getFullyGrownPeriod() != 0)
                return FlowerStatus.FULLY_GROWN;
            else if (harvest + ( harvested * (flower.getFullyGrownPeriod() + flower.getHarvestPeriod())) >= period)
                return FlowerStatus.HARVEST;
            else
                return FlowerStatus.DEAD;
        }

        else
            return plantOrder.getFlowerStatus();
    }


    public Map<Integer,PlantOrder> getAllPIDWithPlantOrder(){
        Map<Integer, PlantOrder> listPID = new HashMap<>();
        List<PlantOrder> plantOrderList = getAllPlantOrderButNoHarvested();


        for(int i=1; i <= 15; i++){
            boolean find = false;
            for (PlantOrder plantOrder : plantOrderList) {
                if(plantOrder.getPID() == i){
                    listPID.put(i,plantOrder);
                    find = true;
                    break;
                }
                if (!find){
                    listPID.put(i,null);
                }
            }
        }

        return  listPID;
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



    public void createPlantOrder(gRequest request, DateTimeComparator dateTimeComparator){ //ปลูกตาม order ที่ได้รับจากฝ่ายอื่น
        GardenerOrder order = gardenerOrderRepository.findById(request.getGardener_order_ID()).get();
        PlantOrder record;
        if(plantOrderRepository.findByPID(request.getPID())==null){
            record = new PlantOrder();
        } else {
            record = plantOrderRepository.findByPID(request.getPID());
        }
        record.setFlowerStatus(FlowerStatus.SEED);
        record.setGardener_order(order); // รอบการปลูกนี้มาจาก plantOrder อันนี้
        record.setQuantity(order.getQuantity()); //ตอนนี้ปลูกดอกไม้ตาม order แบบเป๊ะๆอยู๋
        record.setFlower(order.getFlower()); //แปลงนี้ปลูกดอกนี้นะ
        record.setHarvestable(order.getFlower().getHow_to_harvest());
        record.setTotal(order.getQuantity()); //เริ่มต้นปลูกมาเท่าไหนก็จะมีเท่านั้น
        record.setPID(currentPID); //ปลูกที่แปลงไหน
        record.setTimePlant(LocalDateTime.now()); //วันเวลาที่ปลูก

        plantOrderRepository.save(record);
        gardenerOrderService.setIn_ProcessOrder(order,record);
    }


    //Donut
    public List<PlantOrder> getAllPLantOrder() {
        return plantOrderRepository.findAll();
    }

    public List<Statistic> getAllGardenWithFlower(){
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
            for (Statistic statistic : statistics) { //ไม่เอา plantOrder ที่ตายหมดทั้งแปลงแล้ว กับ ไม่เอาที่เก็บครบทุกรอบแล้ว


                if (statistic.getFlower() == gardenFlower && plantOrder.getFlowerStatus() != FlowerStatus.DEAD && plantOrder.getFlowerStatus() != FlowerStatus.HARVESTED) {
                    if(statistic.getPlantOrder() == null){
                        statistic.setPlantOrder(new ArrayList<>());
                    }
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
        if(plantOrderRequest.getDeadPlant()>0) {
//            PlantOrder record = plantOrderRepository.findByPID(plantOrderRequest.getPID());
            PlantOrder record = getPlantOrderButNoHarvestedByPID(plantOrderRequest.getPID());
            if(record == null){
                System.out.println("แปลงที่ " + plantOrderRequest.getPID() + " เป็น null");
            }
            else{
                System.out.println("แปลงที่ " + plantOrderRequest.getPID() + record.getFlowerStatus());
            }

            //alive-dead

            ////////ตรงนี้ copy ไปแก้
            int remain = record.getTotal() - plantOrderRequest.getDeadPlant();
            if(remain > 0)
                record.setTotal(record.getTotal() - plantOrderRequest.getDeadPlant());
            else if(remain <= 0) {
                record.setTotal(0); //จะใช้เวลากด reset แปลง จะให้มันเป็น -1 >>> ใช้เป็นเงื่อนไขเวลากดไปที่แปลง ถ้าเป็น -1 จะไปปลูก แต่ถ้าไม่ใช้จะแสดงข้อมูลมันอยู่
                record.setFlowerStatus(FlowerStatus.DEAD);
                record.getGardener_order().setStatus(OrderStatus.FAIL);
            }

            System.out.println("did");
            plantOrderRepository.save(record);
        }
    }

    public void resetPlant(PlantOrderRequest request){
        PlantOrder plantOrder =  plantOrderRepository.findByPID(request.getPID());
        plantOrder.setTotal(-1);
        plantOrderRepository.save(plantOrder);
        System.out.println(plantOrder.getTimePlant() + " " + plantOrder.getTotal());
    }

        //harvest
//    public void harvest(PlantOrderRequest plantOrderRequest){
//        PlantOrder record = modelMapper.map(plantOrderRequest,PlantOrder.class);
//        //managing stock
//        Stock stock;
//        if(record.getStock()==null){
//            stock = new Stock();
//        } else {
//            stock = record.getStock();
//        }
//        LocalDateTime localDateTime = LocalDateTime.now();
//        stock.setTime(localDateTime);
//        stock.setQuantity(record.getQuantity());
//        stock.setTotal(record.getTotal());
//        stock.setPlantOrder(record);
//        record.setStock(stock);
//        //still harvestable
//        if(record.getHarvestable()>1){
//            record.setFlowerStatus(FlowerStatus.FULLY_GROWN);
//
//            record.setHarvestable(record.getHarvestable() - 1);
//        }
//        else {//died set stock to zero show as empty field
//        record.setFlowerStatus(FlowerStatus.HARVESTED); //เปลี่ยนจาก DEAD เป็น HARVESTED
//
//        record.setHarvestable(record.getHarvestable() - 1);
//        record.setStock(null);
//        }
//
//        plantOrderRepository.save(record);
//        stockRepository.save(stock);
//    }

    public void harvest(PlantOrderRequest plantOrderRequest){
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++dadas");
        PlantOrder record = plantOrderRepository.findByPID(plantOrderRequest.getPID());
        if(record.getFlowerStatus() == FlowerStatus.HARVEST) {
            //managing stock
            Stock stock = new Stock();
            LocalDateTime localDateTime = LocalDateTime.now();
            stock.setTime(localDateTime);
            stock.setQuantity(record.getTotal()*record.getFlower().getQuantity());
            stock.setTotal(record.getTotal()*record.getFlower().getQuantity());
            stock.setPlantOrder(record);
            System.out.println("hey");
            stockRepository.save(stock);
            record.setHarvestable(record.getHarvestable() - 1);
            //still harvestable
            if (record.getHarvestable() >= 1) {
                record.setFlowerStatus(FlowerStatus.FULLY_GROWN);
            } else if (record.getHarvestable() == 0) {//died set stock to zero show as empty field (ถ้าเป็นการเก็บเกี่ยวรอบสุดท้าย)
                record.setFlowerStatus(FlowerStatus.HARVESTED);
                record.getGardener_order().setStatus(OrderStatus.COMPLETED);
            }



            if (record.getListStock() == null) { //ยังไม่เคยเก็บเกี่ยวเลย
                record.setListStock(new ArrayList<>());
            } else {
                record.getListStock().add(stock);
            }

            plantOrderRepository.save(record);
        }
    }


}
