package ku.cs.flowerManagement.controller;

import ku.cs.flowerManagement.adapter.DateTimeComparator;
import ku.cs.flowerManagement.entity.Flower;
import ku.cs.flowerManagement.entity.OrderItem;
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
import java.util.List;
import java.util.UUID;

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
    @Autowired
    private CommonService commonService;

    public UUID currentG;


    @GetMapping
    private String getAllBed(Model model){
        LocalDateTime now = commonService.getCurrentTime();
        model.addAttribute("orders",gardenerOrderService.getAllGardenerOrder(dateTimeComparator));
        model.addAttribute("plantOrders", plantOrderService.getAllPlantOrder()); //ส่งข้อมูลแปลงทุกรอบการปลูกไปให้
        // for (PlantOrder plantOrder:plantOrderService.getAllPlantOrder()) {
        //     System.out.println(plantOrder.getId()+"  " +plantOrder.getTimePlant() + "  " + plantOrder.getFlowerStatus());
        // }
//        model.addAttribute("orders",gardenerOrderService.getAllPendingGardenerOrder(dateTimeComparator)); //ส่ง order ทั้งหมดไปให้ (= ORDER) ,bottom table(order table)
        model.addAttribute("time",now);//show time
        model.addAttribute("Statistics",plantOrderService.getAllGardenWithFlower());//overall table
        return "bed";
    }

    //dead-harvest-detail
    @GetMapping("/{PID}")
    public String detailOfPlantOrder(@PathVariable int PID,Model model){
        System.out.println("detailOfPlantOrder แปลงที่: "+ PID);
        List<PlantOrder> plantOrders = plantOrderService.getAllPlantOrderButNoStockByPID(PID);
        model.addAttribute("plantOrders", plantOrders);
        //        PlantOrder plantOrder = plantOrderService.findByPID(PID);
//        model.addAttribute("plantOrder", plantOrder);
        return "bed-view"; //ไปปลูก //ไปดูข้อมูลรึเปล่า
    }

    @PostMapping("/{PID}")
    public String editedPlantOrder(@ModelAttribute PlantOrderRequest plantOrderRequest,Model model){
        //plantOrderService.harvest(plantOrderRequest);
        //plantOrderService.plantWasDied(plantOrderRequest);
        return "bed-view";
    }
    //planting zone
    @GetMapping("/order/{PID}")
    public String showOrder(@PathVariable int PID,Model model){
        model.addAttribute("orders",gardenerOrderService.getAllPendingGardenerOrder(dateTimeComparator)); //ส่ง order ทั้งหมดไปให้ (= ORDER)
        model.addAttribute("PID",PID);
        return "bed-plant";
    }
    //chose order
    @PostMapping("/order/{PID}")
    public String choseOrder(@ModelAttribute gRequest plantOrder,Model model){
        plantOrderService.createPlantOrder(plantOrder, dateTimeComparator);
        return "redirect:/beds/{PID}";
    }


    /*  --------------------in case U wanna plant flower without any order-------------------------
    @GetMapping("{PID}/create")
    public String showNonOrderPlant(Model model){
        model.addAttribute("flowers",flowerService.getAllFlower());
        return "bed-plant2";
    }
    @PostMapping("{PID}/create")
    public String choseNonOrderPlant(@ModelAttribute PlantOrderRequest plantOrder,Model model){
        plantOrderService.createPlantOrder(plantOrder.getGardener_order_ID(), plantOrder.getFlowerID(), dateTimeComparator);
        return "redirect:/beds/{PID}";
    }
     */
}
