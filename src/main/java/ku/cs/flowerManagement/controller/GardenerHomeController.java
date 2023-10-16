package ku.cs.flowerManagement.controller;

import ku.cs.flowerManagement.entity.Flower;
import ku.cs.flowerManagement.model.GardenRequest;
import ku.cs.flowerManagement.service.FlowerService;
import ku.cs.flowerManagement.service.GardenService;
import ku.cs.flowerManagement.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        model.addAttribute("gardens",gardenService.getAllGarden());
        model.addAttribute("orders",orderItemService.getAllOrders());
        return "gardener-home";
    }
    @GetMapping("/add")
    public String getAllFlower(Model model){
        for (Flower flower: flowerService.getAllFlower()) {
            System.out.println(flower.getFName());
        }
        model.addAttribute("flowers",flowerService.getAllFlower());
        return "garden-add";
    }
    @PostMapping("/add")
    public String addGarden(@ModelAttribute GardenRequest gardenRequest, Model model){
        gardenService.addGarden(gardenRequest);
        return "redirect:/gardener-home";
    }

}
