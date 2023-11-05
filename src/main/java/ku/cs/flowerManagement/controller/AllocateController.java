package ku.cs.flowerManagement.controller;

import ku.cs.flowerManagement.entity.Allocate;
import ku.cs.flowerManagement.model.AllocateModel;
import ku.cs.flowerManagement.model.AllocateRequest;
import ku.cs.flowerManagement.service.AllocateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
@RequestMapping("/{role}/allocate")
public class AllocateController {

    @Autowired
    private AllocateService allocateService;

    public AllocateController(AllocateService allocateService) {
        this.allocateService = allocateService;
    }
    @GetMapping
    public String showAllocateData(@PathVariable("role") String role,Model model,@RequestParam(defaultValue = "0") int page) {
        
        int total = 0;
        int size = 4;
        Page<Allocate> allocatePage = allocateService.getAllAllocate(page, size);

        AllocateRequest allocateRequest = allocateService.findAllAllocate();
        
        // model.addAttribute("allocateRequest", allocatePage.getContent()); //แบบโชว์หน้า
        model.addAttribute("currentPage", allocatePage.getNumber());
        model.addAttribute("totalPages", allocatePage.getTotalPages());
        model.addAttribute("allocateRequest", allocateRequest); 
        if(Objects.nonNull(allocateRequest)){
            for(AllocateModel allocateModel : allocateRequest.getAllocateModels()){
                total += allocateModel.getTotal();
            }
        }
        model.addAttribute("total",total);
        return "allocate-list"; 
    }
}
