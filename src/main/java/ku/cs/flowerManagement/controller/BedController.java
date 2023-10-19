package ku.cs.flowerManagement.controller;

import ku.cs.flowerManagement.adapter.DateTimeComparator;
import ku.cs.flowerManagement.entity.PlantOrder;
import ku.cs.flowerManagement.model.PlantOrderRequest;
import ku.cs.flowerManagement.model.fRequest;
import ku.cs.flowerManagement.model.gRequest;
import ku.cs.flowerManagement.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
//<<<<<<< HEAD
import java.util.List;
import java.util.UUID;
//=======
//import java.time.format.DateTimeFormatter;
//>>>>>>> main

@Controller
@RequestMapping("/beds")
public class BedController { //ปลูกดอกไม้แต่ละแปลง
    @Autowired
    private FlowerService flowerService;

    @Autowired
    private PlantOrderService plantOrderService;

    @Autowired
    private GardenerOrderService gardenerOrderService;

    @Autowired
    private DateTimeComparator dateTimeComparator;

    @Autowired
    private OrderItemService orderItemService;

//<<<<<<< HEAD
    @Autowired
    private CommonService commonService;

    public UUID currentG;


    //model.addAttribute USE IN ***********************GETMAPPING******************* not in postMapping
    //หน้ารวมแปลง  แต่ละแปลงจะมีเลขที่แปลงของตัวเอง
    @GetMapping
    private String getAllBed(Model model){
        LocalDateTime now = commonService.getCurrentTime();

        model.addAttribute("plantOrders", plantOrderService.getAllPlantOrderButNoStock()); //ส่งข้อมูลแปลงที่กำลังปลูกออกไป
        model.addAttribute("orders",gardenerOrderService.getAllPendingGardenerOrder(dateTimeComparator)); //ส่ง order ทั้งหมดไปให้ (= ORDER) ,bottom table(order table)
        model.addAttribute("time",now);//show time
        model.addAttribute("Statistics",plantOrderService.getAllGardenWithFlower());//overall table
        return "bed";
    }


    @GetMapping("/{PID}")
    public String detailOfPlantOrder(@PathVariable int id,Model model){
        PlantOrder plantOrder = plantOrderService.findByPID(id);
        model.addAttribute("plantOrder", plantOrder);
        return "bed-plant"; //ไปปลูก
    }

    @PostMapping("/{PID}")
    public String editedPlantOrder(@ModelAttribute PlantOrderRequest plantOrderRequest,Model model){
        plantOrderService.harvest(plantOrderRequest);
        plantOrderService.plantWasDied(plantOrderRequest);
        return "bed-view";
    }

    @GetMapping("/{PID}/plant")
    public String planting(@PathVariable int PID,Model model){
        model.addAttribute("flowers", flowerService.getAllFlower()); //ส่งข้อมูลดอกไม้ทั้งหมดไปให้
        model.addAttribute("orders",gardenerOrderService.getAllPendingGardenerOrder(dateTimeComparator)); //ส่ง order ทั้งหมดไปให้ (= ORDER)
        model.addAttribute("PID",PID);
        return "bed-plant";
    }
    @PostMapping("/{PID}/plant")
    public String planted(@ModelAttribute gRequest gRequest,Model model){
        model.addAttribute("flowers", flowerService.getAllFlower()); //ส่งข้อมูลดอกไม้ทั้งหมดไปให้
        currentG = gRequest.getGardener_order_ID();
        return "bed-plant2";
    }

    //รับเลขที่แปลงเข้ามา แล้วเช็คว่าจะไปหน้าปลูก หรือ หน้าดูข้อมูลการปลูกในแปลงนั้น
    @PostMapping("/{PID}")
    public String sendNumber(@PathVariable int PID, Model model) {

        System.out.println("เลือกแปลงที่ " + PID);
        List<PlantOrder> plantOrder = plantOrderService.getAllPlantOrderButNoStockByPID(PID); //หารอบการปลูกที่ยังไม่เก็บเกี่ยวที่แปลงนี้
        if (plantOrder == null) { //แปลงว่างปลูกได้ //ใช้ plantOrder.isEmpty() แตก
            model.addAttribute("flowers", flowerService.getAllFlower()); //ส่งข้อมูลดอกไม้ทั้งหมดไปให้
            model.addAttribute("orders",gardenerOrderService.getAllPendingGardenerOrder(dateTimeComparator)); //ส่ง order ทั้งหมดไปให้ (= ORDER)
            model.addAttribute("PID",PID);
            return "bed-plant"; //ไปปลูก
        }
        else{ //แปลงไม่ว่าง ห้ามปลูก
            model.addAttribute("plantOrder", plantOrder); //ข้อมูลการปลูกของแปลงนี้ ส่งไปเป็น List<PlantOrder>
            return "bed-view"; //ไปดูข้อมูลแปลงนั้น
        }
    }


    //ยังไม่ได้คิด >>> ถ้า 1 แปลงปลูกได้หลายครั้ง
    @GetMapping("/planted")
    private String getPlatedBed(Model model){ //เรียกให้แสดงแปลงที่ปลูกแล้ว
        List<PlantOrder> plantOrder = plantOrderService.getAllPlantOrderButNoStockByPID(plantOrderService.currentPID);
        model.addAttribute("plantOrder", plantOrder); //ข้อมูลการปลูกของแปลงนั้น ส่งไปเป็น List<PlantOrder>
        return "bed-view"; //ไปดูข้อมูลแปลงนั้น
    }



    ////////////////////โปรดอ่าน////////////////////

    // plantFlower เนื่องจากกว่าหาวิธีที่กดเลือก 2 รอบแล้วค่อยกด submit เพื่อส่งไม่ได้ ก็เลยลองทำแบบแยกหน้าเลือกทีละอัน เหมือน plantFlower
    // ต่างกันที่ plantFlower 1-2 >>> plantFlower1 จะให้กดเลือก getGardener_order_ID ส่วน plantFlower2 จะให้กดเลือก FlowerID
    // ตอนนี้ใช้ plantFlower 1-2 อยู่

    //ฝ่ายปลูกจะกดว่า ปลูกดอกอะไร และปลูกตาม order ไหน
    @PostMapping("/plant/{PID}")
    private String plantFlower(@ModelAttribute PlantOrderRequest plantOrder, Model model){
        if(plantOrderService.createPlantOrder(plantOrder.getGardener_order_ID(), plantOrder.getFlowerID(), dateTimeComparator)){ //สร้างคำสั่งปลูกได้ = ปลูกละ
            return "redirect:/beds/planted";
        }
        else{//ดอกไม้ที่กดปลูก กับดอกไม้ใน order ไม่ตรงกัน = ปลูกไม่ได้ให้กลับไปที่หน้ารวม
            LocalDateTime now = commonService.getCurrentTime();

            model.addAttribute("plantOrders", plantOrderService.getAllPlantOrderButNoStock()); //ส่งข้อมูลแปลงที่กำลังปลูกออกไป
            model.addAttribute("orders",orderItemService.getAllOrders());
            model.addAttribute("time",now);
            return "redirect:/beds" ;
        }
    }



    @PostMapping("/plant/1")
    private String plantFlower1(@ModelAttribute gRequest gRequest, Model model){
        model.addAttribute("flowers", flowerService.getAllFlower()); //ส่งข้อมูลดอกไม้ทั้งหมดไปให้
        currentG = gRequest.getGardener_order_ID();
        return "bed-plant2";
    }

    @PostMapping("/plant/2")
    private String plantFlower2(@ModelAttribute fRequest fRequest, Model model){

        if(plantOrderService.createPlantOrder(currentG, fRequest.getFlowerID(), dateTimeComparator)){ //สร้างคำสั่งปลูกได้ = ปลูกละ
            return "redirect:/beds/planted";
        }

        else{//ดอกไม้ที่กดปลูก กับดอกไม้ใน order ไม่ตรงกัน = ปลูกไม่ได้ให้กลับไปที่หน้ารวม
            LocalDateTime now = commonService.getCurrentTime();

            model.addAttribute("plantOrders", plantOrderService.getAllPlantOrderButNoStock()); //ส่งข้อมูลแปลงที่กำลังปลูกออกไป
            model.addAttribute("orders",orderItemService.getAllOrders());
            model.addAttribute("time",now);
            return "redirect:/beds" ;
        }

    }



}
