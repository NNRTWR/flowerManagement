package ku.cs.flowerManagement.controller;

import ku.cs.flowerManagement.adapter.DateTimeComparator;
import ku.cs.flowerManagement.adapter.GardenOrderTimeComparator;
import ku.cs.flowerManagement.entity.GardenerOrder;
import ku.cs.flowerManagement.entity.Flower;
import ku.cs.flowerManagement.entity.PlantOrder;
import ku.cs.flowerManagement.model.PlantOrderRequest;
import ku.cs.flowerManagement.model.gRequest;
import ku.cs.flowerManagement.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/gardener/beds")
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
    private OrderService orderItemService;
    @Autowired
    private CommonService commonService = new CommonService();

    public UUID currentG;


    @GetMapping
    private String getAllBed(Model model,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "8") int size){
        LocalDateTime now = commonService.getCurrentTime();
        model.addAttribute("orders",gardenerOrderService.getAllGardenerOrder(dateTimeComparator));
        model.addAttribute("plantOrders", plantOrderService.getAllPlantOrder()); //ส่งข้อมูลแปลงทุกรอบการปลูกไปให้

        Map<Integer, PlantOrder> listPID = plantOrderService.getAllPIDWithPlantOrder();
        model.addAttribute("listPID", listPID);  //ใช้อันนี้แสดงแปลง

        model.addAttribute("time",now);//show time
        model.addAttribute("Statistics",plantOrderService.getAllGardenWithFlower());//overall table

        List<PlantOrder> plantOrdersPage = plantOrderService.getAllPlantOrderPage(page, size);
        model.addAttribute("plantOrdersPage", plantOrdersPage);
        return "bed";
    }

    //dead-harvest-detail
    @GetMapping("/{PID}")
    public String detailOfPlantOrder(@PathVariable int PID,Model model){
        model.addAttribute("commonService",commonService);
        PlantOrder plantOrder = plantOrderService.getPlantOrderButNoHarvestedByPID(PID);
        System.out.println("แปลงที่ " + plantOrder.getPID() + " สถานะ " + plantOrder.getFlowerStatus() + "  " + plantOrder.getTotal());
        model.addAttribute("plantOrder", plantOrder);
        model.addAttribute("total", plantOrder.getTotal());
        return "bed-view"; //ไปดูข้อมูลรึเปล่า
    }

    @PostMapping("/{PID}")
    public String editedPlantOrder(@ModelAttribute PlantOrderRequest plantOrderRequest, @RequestParam(name = "deadButton", required = false) String deadButton, @RequestParam(name = "harvestButton", required = false) String harvestButton,Model model, @RequestParam(name = "resetButton",required = false) String resetButton){
        if (deadButton != null) {
            // The "แจ้งดอกไม้ตาย" button was clicked
            plantOrderService.plantWasDied(plantOrderRequest);
        } else if (harvestButton != null) {
            // The "เก็บเกี่ยว" button was clicked
            System.out.println("clicked harvest");
            plantOrderService.harvest(plantOrderRequest);
        }

        if ("true".equals(resetButton)) {
            System.out.println("อยู่ที่ editedPlantOrder");
            plantOrderService.resetPlant(plantOrderRequest);
            return "redirect:/gardener/beds/order/{PID}"; //reset แล้ว ให้ไปปลูกใหม่ได้
        }

        //return bed-view ต่อจะแตกเพราะ PID ใน URL มันจะมี "?" อยู่ท้ายแล้วก็แก้ไม่เป็นด้วย
        return "redirect:/gardener/beds";
    }

    //planting zone
    // @GetMapping("/order/{PID}")
    // public String showOrder(@PathVariable int PID,Model model){
    //     model.addAttribute("orders",gardenerOrderService.getAllPendingGardenerOrder(dateTimeComparator)); //ส่ง order ทั้งหมดไปให้ (= ORDER)
    //     model.addAttribute("PID",PID);
    //     plantOrderService.currentPID = PID;
    //     return "bed-plant";
    // }
    // อันนี้แบบpagination
    @GetMapping("/order/{PID}")
    public String showOrder(@PathVariable int PID, @RequestParam(defaultValue = "0") int page, Model model) {
        int pageSize = 3; // Number of items per page
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<GardenerOrder> gardenerOrdersPage = gardenerOrderService.getAllGardenerOrderPendingPage(pageable);
        GardenOrderTimeComparator gardenOrderTimeComparator = new GardenOrderTimeComparator();
        List<GardenerOrder> gardenerOrders = gardenerOrderService.getAllPendingGardenerOrder(gardenOrderTimeComparator);
        model.addAttribute("orders", gardenerOrdersPage.getContent());
        model.addAttribute("PID", PID);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", gardenerOrdersPage.getTotalPages());
        model.addAttribute("commonService", commonService);
        plantOrderService.currentPID = PID;
        return "bed-plant";
    }
    //chose order
    @PostMapping("/order/{PID}")
    public String choseOrder(@ModelAttribute gRequest plantOrder,Model model){
        plantOrderService.createPlantOrder(plantOrder, dateTimeComparator);
        return "redirect:/gardener/beds";
    }
}
