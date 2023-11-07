package ku.cs.flowerManagement.controller;

import ku.cs.flowerManagement.entity.Flower;
import ku.cs.flowerManagement.model.FlowerRequest;
import ku.cs.flowerManagement.service.FlowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

@RequestMapping("/{role}/flower")
@Controller
public class FlowerController {

    @Autowired
    private FlowerService flowerService;

    @GetMapping 

    public String showFlowerPage(@PathVariable("role") String role, Model model, @RequestParam(defaultValue = "0") int page) {
        int pageSize = 4;
        Page<Flower> flowerPage = flowerService.getFlowers(page, pageSize);   //โชว์แถบเลื่อนได้ น้ำตาจะไหลทำได้ซักที

        int totalFlowers = flowerService.getTotalFlowerCount();
        model.addAttribute("flower", new FlowerRequest());
        model.addAttribute("flowers", flowerPage.getContent());
        model.addAttribute("currentPage", flowerPage.getNumber());
        model.addAttribute("totalPages", flowerPage.getTotalPages());
        model.addAttribute("totalFlowers", totalFlowers);
        

        return "flower";
    }



    @GetMapping("/{id}")
    public String showFlowerDetailPage(@PathVariable("role") String role,Model model, @PathVariable("id") Integer fid, @RequestParam(name = "edit",defaultValue = "0") int edit) {
//        model.addAttribute("flower", flowerService.getFlowerById(id));
        model.addAttribute("flower",flowerService.getOneFlower(fid));
        model.addAttribute("method", "PUT");
        model.addAttribute("edit",edit);
        model.addAttribute("current_url","/"+role+"/flower/"+fid);
        return "flower-detail";
    }

    @PostMapping
    public String createFlower(@PathVariable("role") String role,@ModelAttribute FlowerRequest flower, Model model ) {
        try {
            flowerService.addFlower(flower);
        } catch (RuntimeException e) {
            model.addAttribute("error", "ห้ามชื่อดอกไม้ซ้ำ"); 
            
        }
        return "redirect:/{role}/flower";
    }

    @PutMapping("/{id}")
    public String updateFlower(@PathVariable("role") String role,@ModelAttribute FlowerRequest flower,@PathVariable("id") int id) {
        flower.setFID(id);
        flowerService.updateFlower(flower);
        return "redirect:/{role}/flower";
    }
}
