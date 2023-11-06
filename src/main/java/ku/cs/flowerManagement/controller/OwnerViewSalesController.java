package ku.cs.flowerManagement.controller;

import ku.cs.flowerManagement.model.OwnerViewSalesResponse;
import ku.cs.flowerManagement.service.OwnerViewSalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import java.util.List;

@Controller
public class OwnerViewSalesController {

    @Autowired
    private OwnerViewSalesService ownerViewSalesService;

    @GetMapping("/owner/view")
    public String showSalesData(Model model) {
        List<OwnerViewSalesResponse> sales = ownerViewSalesService.getAllSales();
        int total = 0;
        for (OwnerViewSalesResponse sale : sales) {
            total += sale.getBalanceAmount();
        }
        model.addAttribute("sales", sales);
        model.addAttribute("total", total);
        return "view-owner";
    }

}
