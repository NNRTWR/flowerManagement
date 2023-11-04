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

@RequestMapping("/owner/flower")
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

    public String showFlowerPage(Model model, @RequestParam(defaultValue = "0") int page) {
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
    public String showFlowerDetailPage(Model model, @PathVariable("id") Integer fid, @RequestParam(name = "edit",defaultValue = "0") int edit) {
//        model.addAttribute("flower", flowerService.getFlowerById(id));
        String role = "owner";
        model.addAttribute("flower",flowerService.getOneFlower(fid));
        model.addAttribute("method", "PUT");
        model.addAttribute("url","/"+role+"/flower");
        model.addAttribute("current_url","/"+role+"/flower/"+fid);
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

    @PostMapping ()//("/flower/create")
    public String createFlower(@ModelAttribute FlowerRequest flower ) {
        flowerService.addFlower(flower);
        String url = "/"+"owner"+"/flower";
        return "redirect:"+url;
    }

    @PutMapping("/{id}")
    public String updateFlower(@ModelAttribute FlowerRequest flower,@PathVariable("id") int id) {
        flower.setFID(id);
        flowerService.updateFlower(flower);
        String url = "/"+"owner"+"/flower";
        return "redirect:"+url;
    }
}
