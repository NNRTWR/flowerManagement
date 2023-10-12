package ku.cs.flowerManagement.service;

import ku.cs.flowerManagement.adapter.DateTimeComparator;
import ku.cs.flowerManagement.common.FlowerStatus;
import ku.cs.flowerManagement.entity.Flower;
import ku.cs.flowerManagement.entity.OrderItem;
import ku.cs.flowerManagement.entity.PlantOrder;
import ku.cs.flowerManagement.model.PlantOrderRequest;
import ku.cs.flowerManagement.repository.FlowerRepository;
import ku.cs.flowerManagement.repository.PlantOrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Service
public class PlantOrderService {
    @Autowired
    private PlantOrderRepository plantOrderRepository;

    @Autowired
    private FlowerRepository flowerRepository;

    @Autowired
    private GardenerOrderService gardenerOrderService;

    @Autowired
    private ModelMapper modelMapper;

    public int currentPID;




    public PlantOrder getOnePlantOrder(int PID){ //เอาแค่คำสั่งปลูกที่เลือกไปใช้
        System.out.println(PID); // เลือกแปลงไหนนะ
        currentPID = PID;
        PlantOrder plantOrder = plantOrderRepository.findByPID(PID); //หาว่าเลขที่่แปลงนี้ปลูกดอกไม้ยัง
        if (plantOrder == null)
            return null;
        else {
            setFlowerOrderStatus(plantOrder); //ไปเรียก set สถานะของดอกไม้ในแปลงก่อน
            return plantOrder;
        }
    }

    public List<PlantOrder> getAllPlantOrder(){ //เอาแค่คำสั่งปลูกทั้งหมด
        System.out.println("ก่อน List<PlantOrder> listPlantOrder = plantOrderRepository.findAll(); ที่ getAllPlantOrder ");
        List<PlantOrder> listPlantOrder = plantOrderRepository.findAll();
        System.out.println("หลัง List<PlantOrder> listPlantOrder = plantOrderRepository.findAll(); ที่ getAllPlantOrder ");
        if (listPlantOrder.isEmpty()) {
            return null;
        }
        else {
//            System.out.println("ก่อน  for (PlantOrder plantOrder : listPlantOrder) {");
            for (PlantOrder plantOrder : listPlantOrder) {
//                //ไปเรียก set สถานะของดอกไม้ในแปลงก่อน
//                System.out.println(plantOrder); //ลองดูว่ามี plantOrder อะไรบ้าง
                setFlowerOrderStatus(plantOrder);
            }
        }
        return listPlantOrder;
    }

    public void setFlowerOrderStatus(PlantOrder plantOrder){ //set สถานะของดอกไม้ในแปลง
        plantOrder.setFlowerStatus(getFlowerStatus(plantOrder));
        plantOrderRepository.save(plantOrder);
    }


    //ต้องแก้เพราะ เก็บได้หลายครั้งก็มี
    public FlowerStatus getFlowerStatus(PlantOrder plantOrder){ //หา status ของดอกไม้ในแปลงนั้น
        long period = ChronoUnit.DAYS.between(plantOrder.getTimePlant(), LocalDateTime.now()); //ระยะเวลาตั้งแต่ปลูกจนวันที่ปัจจุบัน = ปลูกมาได้กี่วันแล้ว

        System.out.println("period คือ "+period);
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
    }

    public void createPlantOrder(PlantOrderRequest plantOrder, DateTimeComparator dateTimeComparator){ //ปลูกตาม order ที่ได้รับจากฝ่ายอื่น
        PlantOrder record = modelMapper.map(plantOrder, PlantOrder.class); //map จาก PlantOrderRequest เป็น PlantOrder
        Flower flower = flowerRepository.findById(plantOrder.getFlowerID()).get(); //หาดอกไม้ที่ปลูก
        record.setFlower(flower); //แปลงนี้ปลูกดอกนี้นะ
        OrderItem orderItem = gardenerOrderService.getOldestOrderStatus(dateTimeComparator);
        record.setOrder(orderItem); //ปลูกตาม order เก่าสุด
        record.setPID(currentPID); //ปลูกที่แปลงไหน
        record.setTimePlant(LocalDateTime.now()); //วันเวลาที่ปลูก
        System.out.println("ก่อน plantOrderRepository.save(record) ที่ createPlantOrder");
        plantOrderRepository.save(record);
        System.out.println("หลัง plantOrderRepository.save(record) ที่ createPlantOrder");
        gardenerOrderService.setIn_ProcessOrder(dateTimeComparator);
    }
}
