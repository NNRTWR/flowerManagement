package ku.cs.flowerManagement.controller;

import ku.cs.flowerManagement.model.GardenRequest;
import ku.cs.flowerManagement.service.GardenService;
import ku.cs.flowerManagement.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gardener-home")
public class GardenerHomeController {
    @Autowired
    private GardenService gardenService;
    @Autowired
    private OrderItemService orderItemService;
    @GetMapping
    public String getAllGarden(Model model){
        model.addAttribute("gardens",gardenService.getAllGarden());
        model.addAttribute("orders",orderItemService.getAllOrders());
        return "gardener-home";
    }
    @GetMapping("/add")
    public String addGarden(@ModelAttribute GardenRequest gardenRequest, Model model){
        return "redirect:/gardener-add";
    }

}
