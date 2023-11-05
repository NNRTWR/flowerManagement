package ku.cs.flowerManagement.controller;

import ku.cs.flowerManagement.entity.Flower;
import ku.cs.flowerManagement.entity.OrderItem;
import ku.cs.flowerManagement.model.FlowerRequest;
import ku.cs.flowerManagement.model.OrderItemRequest;
import ku.cs.flowerManagement.service.FlowerService;
import ku.cs.flowerManagement.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller

@RequestMapping("/seller/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private FlowerService flowerService;

    @GetMapping
    private String showOrderPage( @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(name = "id", defaultValue = "0") int id, Model model) {
        // สำหรับคลิ๊กหน้า
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
        return "order";
    }

    @PostMapping
    public String createOrder(@ModelAttribute OrderItemRequest orderFlower, Model model) {
        System.out.println(orderFlower.getFlowerPrice());
        orderFlower.setFlowerPrice(orderFlower.getFlowerPrice() * orderFlower.getFlowerPrice());
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
        return "order";
    }

    @PutMapping("/confirmOrder/{id}")
    public String confirmOrder(@PathVariable int id, Model model){
        orderService.confirmOrderById(id);
        model.addAttribute("order", new OrderItemRequest());
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
