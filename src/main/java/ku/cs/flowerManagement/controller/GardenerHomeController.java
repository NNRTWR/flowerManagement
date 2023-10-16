package ku.cs.flowerManagement.controller;

import ku.cs.flowerManagement.entity.Flower;
import ku.cs.flowerManagement.entity.Garden;
import ku.cs.flowerManagement.model.GardenRequest;
import ku.cs.flowerManagement.service.FlowerService;
import ku.cs.flowerManagement.service.GardenService;
import ku.cs.flowerManagement.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/gardener-home")
public class GardenerHomeController {
    @Autowired
    private GardenService gardenService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private FlowerService flowerService;
    @GetMapping
    public String getAllGarden(Model model){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        model.addAttribute("gardens",gardenService.getAllGarden());
        model.addAttribute("orders",orderItemService.getAllOrders());
        model.addAttribute("time",now);
        model.addAttribute("Statistics",gardenService.getAllGardenWithFlower());
        return "gardener-home";
    }
    @GetMapping("/add")
    public String getAllFlower(Model model){
        model.addAttribute("flowers",flowerService.getAllFlower());
        return "garden-add";
    }
    @PostMapping("/add")
    public String addGarden(@ModelAttribute GardenRequest gardenRequest, Model model){
        gardenService.addGarden(gardenRequest);
        return "redirect:/gardener-home";
    }

}
