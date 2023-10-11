package ku.cs.flowerManagement.controller;

import ku.cs.flowerManagement.adapter.DateTimeComparator;
import ku.cs.flowerManagement.entity.PlantOrder;
import ku.cs.flowerManagement.model.PlantOrderRequest;
import ku.cs.flowerManagement.service.FlowerService;
import ku.cs.flowerManagement.service.OrderItemService;
import ku.cs.flowerManagement.service.PlantOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/beds")
public class BedController { //ปลูกดอกไม้แต่ละแปลง
    @Autowired
    private FlowerService flowerService;

    @Autowired
    private PlantOrderService plantOrderService;

    @Autowired
    private OrderItemService orderService;

    @Autowired
    private DateTimeComparator dateTimeComparator;

    @GetMapping
    private String getAllBed(Model model){ //หน้ารวมแปลง  แต่ละแปลงจะมีเลขที่แปลงของตัวเอง
        model.addAttribute("plantOrders", plantOrderService.getAllPlantOrder()); //ส่งข้อมูลแปลงที่ปลูกแล้วออกไป
        return "bed";
    }


    @PostMapping("/{PID}")
    public String sendNumber(@PathVariable int PID, Model model) { //รับเลขที่แปลงเข้ามา

        PlantOrder plantOrder = plantOrderService.getOnePlantOrder(PID); //ใช้ได้
        if (plantOrder == null) {
            model.addAttribute("flowers", flowerService.getAllFlower()); //ส่งข้อมูลดอกไม้ทั้งหมดไปให้
            model.addAttribute("orderItems",orderService.getAllOrderStatus(dateTimeComparator)); //ส่ง order ที่ต้องปลูกทั้งหมดไปให้ (= ORDER)
            model.addAttribute("PID",PID);
            return "bed-plant";
        }
        else{
            PlantOrder plantOrder1 = plantOrderService.getOnePlantOrder(PID);
            model.addAttribute("plantOrder", plantOrder1); //ข้อมูลการปลูกของแปลงนี้
            return "bed-view";
        }
    }


//    @GetMapping("/empty") //จากหน้า all ถ้า user กดที่แปลงเปล่า    !!!!!!!!เลขที่แปลง
//    private String getEmptyBed(Model model){ //เรียกให้แสดงแปลงเปล่า
//        model.addAttribute("flowers", flowerService.getAllFlower()); //ส่งข้อมูลดอกไม้ทั้งหมดไปให้
//        model.addAttribute("orderItems",orderService.getAllOrderItem()); //ส่ง order ที่ต้องปลูกทั้งหมดไปให้
//        return "bed-plant";
//    }
//

    @GetMapping("/planted")
    private String getPlatedBed(Model model){ //เรียกให้แสดงแปลงที่ปลูกแล้ว
        PlantOrder plantOrder = plantOrderService.getOnePlantOrder(plantOrderService.currentPID);
        model.addAttribute("plantOrder", plantOrder); //ข้อมูลการปลูกของแปลงนั้น
        return "bed-view";
    }

    @PostMapping("/plant/{PID}")
    private String plantFlower(@ModelAttribute PlantOrderRequest plantOrder, Model model){ //ปลูกตาม order แรกใน list (order จะเรียงตามก่อนหลัง หลังปลูกจะไป set ค่าเพื่อให้การปลูกในแปลงถัดไปเป็นการปลูกตาม order ถัดไป (order แรกใน list เป็น order ถัดไป))
//        System.out.println(orderService.getOldestOrderStatus(dateTimeComparator));
        plantOrderService.createPlantOrder(plantOrder, dateTimeComparator); //สร้างคำสั่งปลูก = ปลูกละ
//        System.out.println("ก่อน return ที่ @PostMapping(\"/plant/{PID}\")");
        return "redirect:/beds";
    }

}
