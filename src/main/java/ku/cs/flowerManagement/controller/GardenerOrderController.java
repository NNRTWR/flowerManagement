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
import ku.cs.flowerManagement.service.OrderService;
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
@RequestMapping("/{role}/orders")
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
    public String getAllOrder(@PathVariable("role") String role, Model model, @RequestParam(defaultValue = "0") int page,
    @RequestParam(name = "sort", defaultValue = "PENDING") String sort){
        int pageSize = 4;
        List<GardenerOrder> sortedOrders;
        if ("PENDING".equals(sort)) {
            sortedOrders = gardenerOrderService.getAllPendingGardenerOrderForSort(); 
        } 
        else if ("IN_PROCESS".equals(sort)) {
            sortedOrders = gardenerOrderService.getAllInprocessGardenerOrder(); 
        } 
        else if ("FAIL".equals(sort)) {
            sortedOrders = gardenerOrderService.getAllFailGardenerOrder(); 
        } 
        else if ("COMPLETED".equals(sort)) {
            sortedOrders = gardenerOrderService.getAllFailGardenerOrder(); 
        } 
        else {
            Page<GardenerOrder> gardenOrderPage = gardenerOrderService.getAllGardenerOrderPage(page, pageSize);
            sortedOrders = gardenOrderPage.getContent();
        }

       
        model.addAttribute("orderItems", sortedOrders);
        model.addAttribute("flowers", flowerService.getAllFlower()); // ย้ายมา
        model.addAttribute("commonService",commonService);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (int) Math.ceil((double) sortedOrders.size() / pageSize));
        model.addAttribute("total", sortedOrders.size());
        
        return "gardener-order-all";
    }


    @PostMapping 
    public String addOrder(@PathVariable("role") String role,@ModelAttribute GardenerOrderRequest gardenerOrder, Model model){
        gardenerOrderService.addOrder(gardenerOrder);
        
        model.addAttribute("orderItems", gardenerOrderService.getAllGardenerOrder(dateTimeComparator));
        return "redirect:/{role}/orders";
    }
}
