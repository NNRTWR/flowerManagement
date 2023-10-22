package ku.cs.flowerManagement.controller;

import ku.cs.flowerManagement.entity.Flower;
import ku.cs.flowerManagement.entity.OrderItem;
import ku.cs.flowerManagement.model.FlowerRequest;
import ku.cs.flowerManagement.model.OrderItemRequest;
import ku.cs.flowerManagement.service.FlowerService;
import ku.cs.flowerManagement.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/{role}/order")

public class OrderController {

    @Autowired
    private OrderItemService orderService;

    @Autowired
    private FlowerService flowerService;

    @GetMapping //("/{role}/order")
    // private String showOrderPage(@PathVariable String role, Model model, @RequestParam(name = "id", defaultValue = "0" ) int id) {
    //     model.addAttribute("order", new OrderItemRequest());
    //     model.addAttribute("orders" , orderService.getOrders());
    //     model.addAttribute("options", flowerService.getFlowers());
    //     if (id != 0) {
    //         model.addAttribute("canceledOrder",orderService.getOrderById(id));
    //     }
    //     else {
    //         model.addAttribute("canceledOrder", new OrderItemRequest());
    //     }
    //     System.out.println(orderService.getOrders().toString());
    //     return "/seller/order";
    // }

    private String showOrderPage(@PathVariable String role, 
                                 @RequestParam(defaultValue = "0") int page, 
                                 @RequestParam(name = "id", defaultValue = "0") int id, 
                                 Model model) {
        // สำหรับคลิ๊กหน้า
        int pageSize = 4;
        Page<OrderItem> orderPage = orderService.getOrders(page, pageSize);
        model.addAttribute("pagedOrders", orderPage.getContent());
        model.addAttribute("currentPage", orderPage.getNumber());
        model.addAttribute("totalPages", orderPage.getTotalPages());

        //เหมือนด้านบน
        model.addAttribute("order", new OrderItemRequest());
        model.addAttribute("orders" , orderService.getOrders());
        model.addAttribute("options", flowerService.getFlowers());

        if (id != 0) {
            model.addAttribute("canceledOrder", orderService.getOrderById(id));
        } else {
            model.addAttribute("canceledOrder", new OrderItemRequest());
        }

        System.out.println(orderService.getOrders().toString());
        return "/seller/order";
    }

    @PostMapping //("/{role}/order")
    public String createOrder(@PathVariable String role, @ModelAttribute OrderItemRequest orderFlower, Model model) {
        System.out.println(orderFlower);
        orderFlower.setFlowerPrice(orderFlower.getFlowerPrice() * orderFlower.getOrderQuantity());
        orderService.createOrder(orderFlower);
        return "redirect:/seller/order";
    }

    @PutMapping("/{id}")
    public String cancelOrder(@PathVariable int id, Model model){
        orderService.cancelOrderById(id);
        model.addAttribute("order", new OrderItemRequest());
        model.addAttribute("orders", orderService.getOrders());
        model.addAttribute("options", flowerService.getFlowers());
        model.addAttribute("canceledOrder",orderService.getOrderById(id));
        return "/seller/order";
    }
}
