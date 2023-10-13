package ku.cs.flowerManagement.controller;

import ku.cs.flowerManagement.model.OrderItemRequest;
import ku.cs.flowerManagement.service.FlowerService;
import ku.cs.flowerManagement.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller

public class OrderController {

    @Autowired
    private OrderItemService orderService;

    @Autowired
    private FlowerService flowerService;

    @GetMapping("/{role}/order")
    private String showOrderPage(@PathVariable String role, Model model, @RequestParam(name = "id", defaultValue = "0" ) int id) {
        model.addAttribute("order", new OrderItemRequest());
        model.addAttribute("orders" , orderService.getOrders());
        model.addAttribute("options", flowerService.getFlowers());
        if (id != 0) {
            model.addAttribute("canceledOrder",orderService.getOrderById(id));
        }
        else {
            model.addAttribute("canceledOrder", new OrderItemRequest());
        }
        System.out.println(orderService.getOrders().toString());
        return "order";
    }

    @PostMapping("/{role}/order")
    public String createOrder(@PathVariable String role, @ModelAttribute OrderItemRequest orderFlower, Model model) {
        System.out.println(orderFlower);
        orderFlower.setFlowerPrice(orderFlower.getFlowerPrice() * orderFlower.getOrderQuantity());
        orderService.createOrder(orderFlower);
        return "redirect:/order";
    }

    @PutMapping("/order/{id}")
    public String cancelOrder(@PathVariable int id, Model model){
        orderService.cancelOrderById(id);
        model.addAttribute("order", new OrderItemRequest());
        model.addAttribute("orders", orderService.getOrders());
        model.addAttribute("options", flowerService.getFlowers());
        model.addAttribute("canceledOrder",orderService.getOrderById(id));
        return "order";
    }
}
