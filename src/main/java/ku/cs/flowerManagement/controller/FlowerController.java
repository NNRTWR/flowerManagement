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

    @GetMapping //("/flower")
    // public String showFlowerPage(Model model) {
    //     model.addAttribute("flower", new FlowerRequest());
    //     model.addAttribute("flowers", flowerService.getFlowers());
    //     // ใช้ FlowerService getAllFlowers
    //     int totalFlowers = flowerService.getTotalFlowerCount();
    //     model.addAttribute("totalFlowers", totalFlowers);
    //     return "flower";
    // }

    public String showFlowerPage(@PathVariable("role") String role, Model model, @RequestParam(defaultValue = "0") int page) {
        int pageSize = 4;
        Page<Flower> flowerPage = flowerService.getFlowers(page, pageSize);   //โชว์แถบเลื่อนได้ น้ำตาจะไหลทำได้ซักที

        int totalFlowers = flowerService.getTotalFlowerCount();
        model.addAttribute("flower", new FlowerRequest());
        model.addAttribute("flowers", flowerPage.getContent());
        model.addAttribute("currentPage", flowerPage.getNumber());
        model.addAttribute("totalPages", flowerPage.getTotalPages());
        model.addAttribute("totalFlowers", totalFlowers);
        model.addAttribute("role","owner");

        return "flower";
    }



    @GetMapping("/{id}")
    public String showFlowerDetailPage(@PathVariable("role") String role,Model model, @PathVariable("id") Integer fid, @RequestParam(name = "edit",defaultValue = "0") int edit) {
//        model.addAttribute("flower", flowerService.getFlowerById(id));
        model.addAttribute("flower",flowerService.getOneFlower(fid));
        model.addAttribute("method", "PUT");
        model.addAttribute("edit",edit);
        return "flower-detail";
    }

//     @GetMapping("/flower/create")
//     public String showFlowerDetailPageCreate(Model model) {
//         model.addAttribute("flowers",flowerService.getAllFlower());
// //        model.addAttribute("flower", new FlowerRequest());
// //        model.addAttribute("method", "POST");
//         return "flower-detail";
//     }

//    @PostMapping("/flower")
//    public String createFlower(@ModelAttribute FlowerRequest flower) {
//        flowerService.addFlower(flower);
//        return "redirect:/flower";
//    }

    @PostMapping
    public String createFlower(@PathVariable("role") String role,@ModelAttribute FlowerRequest flower ) {
        flowerService.addFlower(flower);
        return "redirect:/{role}/flower";
    }

    @PutMapping("/{id}")
    public String updateFlower(@PathVariable("role") String role,@ModelAttribute FlowerRequest flower,@PathVariable("id") int id) {
        flower.setFID(id);
        flowerService.updateFlower(flower);
        return "redirect:/{role}/flower";
    }
}
