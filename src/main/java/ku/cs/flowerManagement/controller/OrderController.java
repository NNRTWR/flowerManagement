package ku.cs.flowerManagement.controller;

import ku.cs.flowerManagement.model.OrderFlowerRequest;
import ku.cs.flowerManagement.service.FlowerService;
import ku.cs.flowerManagement.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class OrderController {


    @Autowired
    private OrderService orderService;

    @Autowired
    private FlowerService flowerService;

    @GetMapping("/order")
    private String showOrderPage(Model model, @RequestParam(name = "id", defaultValue = "0" ) int id) {
        model.addAttribute("order", new OrderFlowerRequest());
        model.addAttribute("orders" , orderService.getOrders());
        model.addAttribute("options", flowerService.getFlowers());
        if (id != 0) {
            model.addAttribute("canceledOrder",orderService.getOrderById(id));
        }
        else {
            model.addAttribute("canceledOrder", new OrderFlowerRequest());
        }
        System.out.println(orderService.getOrders().toString());
        return "order";
    }

    @PostMapping("/order")
    public String createOrder(@ModelAttribute OrderFlowerRequest orderFlower, Model model) {
        System.out.println(orderFlower.getFlowerPrice());

        orderFlower.setFlowerPrice(orderFlower.getFlowerPrice() * orderFlower.getOrderQuantity());
        orderService.createOrder(orderFlower);
        return "redirect:/order";
    }

    @PutMapping("/order/{id}")
    public String cancelOrder(@PathVariable int id, Model model){
        orderService.cancelOrderById(id);
        model.addAttribute("order", new OrderFlowerRequest());
        model.addAttribute("orders", orderService.getOrders());
        model.addAttribute("options", flowerService.getFlowers());
        model.addAttribute("canceledOrder",orderService.getOrderById(id));
        return "order";
    }

    @PutMapping("/confirmOrder/{id}")
    public String confirmOrder(@PathVariable int id, Model model){
        orderService.confirmOrderById(id);
        model.addAttribute("order", new OrderFlowerRequest());
        model.addAttribute("orders", orderService.getOrders());
        model.addAttribute("options", flowerService.getFlowers());
        model.addAttribute("confirmOrder", orderService.getOrderById(id));
        return "order";
    }

    @GetMapping("/queue")
    public String getQueuePage(Model model) {
        System.out.println("Inside getQueuePage method");
        return "queue";
    }
}
