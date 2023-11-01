package ku.cs.flowerManagement.controller;


import ku.cs.flowerManagement.adapter.DateTimeComparator;
import ku.cs.flowerManagement.adapter.GardenOrderTimeComparator;
import ku.cs.flowerManagement.entity.Flower;
import ku.cs.flowerManagement.entity.GardenerOrder;
import ku.cs.flowerManagement.model.GardenerOrderRequest;
import ku.cs.flowerManagement.model.OrderItemRequest;
import ku.cs.flowerManagement.service.CommonService;
import ku.cs.flowerManagement.service.FlowerService;
import ku.cs.flowerManagement.service.GardenerOrderService;
import ku.cs.flowerManagement.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/garden/orders")
public class GardenerOrderController {
    @Autowired
    private GardenerOrderService gardenerOrderService;

    @Autowired
    private FlowerService flowerService;

    @Autowired
    private DateTimeComparator dateTimeComparator;
    @Autowired
    private CommonService commonService;


    @GetMapping
    // public String getAllOrder(@PathVariable String role, Model model)
    
    public String getAllOrder( Model model, @RequestParam(defaultValue = "0") int page){
        int pageSize = 4;
        Page<GardenerOrder> gardenOrderPage = gardenerOrderService.getAllGardenerOrderPage(page, pageSize);
        model.addAttribute("orderItems",  gardenOrderPage.getContent());
        model.addAttribute("flowers", flowerService.getAllFlower()); // ย้ายมา
        model.addAttribute("commonService",commonService);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", gardenOrderPage.getTotalPages());
        return "gardener-order-all";
    }

    // @GetMapping("/form")
    // public String getOrderForm(Model model){
    //     model.addAttribute("flowers", flowerService.getAllFlower());
    //     return "redirect:/gardener/orders/";
    // }

    @PostMapping //("/add")
    public String addOrder(@ModelAttribute GardenerOrderRequest gardenerOrder, Model model){
        gardenerOrderService.addOrder(gardenerOrder);
        
        model.addAttribute("orderItems", gardenerOrderService.getAllGardenerOrder(dateTimeComparator));
        return "redirect:/garden/orders";
    }
}
