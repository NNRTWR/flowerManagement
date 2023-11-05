package ku.cs.flowerManagement.controller;

import ku.cs.flowerManagement.service.OwnerViewSalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class OwnerViewSalesController {

    @Autowired
    private OwnerViewSalesService ownerViewSalesService;

    @GetMapping("/view/owner")
    public String showSalesData(Model model) {

        model.addAttribute("sales", ownerViewSalesService);
        return "view-owner";
    }

}
