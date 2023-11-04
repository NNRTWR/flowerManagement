package ku.cs.flowerManagement.controller;

import ku.cs.flowerManagement.model.AllocateModel;
import ku.cs.flowerManagement.model.AllocateRequest;
import ku.cs.flowerManagement.service.AllocateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Objects;

@Controller
public class AllocateController {

    @Autowired
    private AllocateService allocateService;

    public AllocateController(AllocateService allocateService) {
        this.allocateService = allocateService;
    }

    @GetMapping("/allocate")
    public String showAllocateData(Model model) {
        // Call the service to get the AllocateRequest
        int total = 0;
        AllocateRequest allocateRequest = allocateService.findAllAllocate();

        // Add the AllocateRequest to the model for the Thymeleaf template
        model.addAttribute("allocateRequest", allocateRequest);
        if(Objects.nonNull(allocateRequest)){
            for(AllocateModel allocateModel : allocateRequest.getAllocateModels()){
                total += allocateModel.getTotal();
            }
        }
        model.addAttribute("total",total);
        return "allocate-list"; // Return the name of your Thymeleaf template (e.g., "allocate.html")
    }
}
