package ku.cs.flowerManagement.controller;

import ku.cs.flowerManagement.model.AllocateModel;
import ku.cs.flowerManagement.model.AllocateRequest;
import ku.cs.flowerManagement.service.AllocateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String showAllocateData(@PathVariable("role") String role,Model model) {
        int total = 0;
        AllocateRequest allocateRequest = allocateService.findAllAllocate();

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
