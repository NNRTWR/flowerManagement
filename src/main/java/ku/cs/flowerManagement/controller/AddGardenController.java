package ku.cs.flowerManagement.controller;

import ku.cs.flowerManagement.model.GardenRequest;
import ku.cs.flowerManagement.service.GardenService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/garden-add")
public class AddGardenController {
    private GardenService gardenService;
    @PostMapping("/add")
    public String addGarden(@ModelAttribute GardenRequest gardenRequest, Model model) {
        gardenService.addGarden(gardenRequest);
        return "redirect:/gardener-home";
    }
}
