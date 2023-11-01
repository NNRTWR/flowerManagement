package ku.cs.flowerManagement.controller;

import ku.cs.flowerManagement.model.OrderFlowerRequest;
import ku.cs.flowerManagement.model.StockRequest;
import ku.cs.flowerManagement.repository.FlowerRepository;
import ku.cs.flowerManagement.service.FlowerService;
import ku.cs.flowerManagement.service.OrderService;
import ku.cs.flowerManagement.service.StockService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StockController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private FlowerService flowerService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FlowerRepository flowerRepository;


    @Autowired
    private StockService stockService;



//    @RequestMapping

    @GetMapping("/stock")
    public String showStockPage(Model model) {
        model.addAttribute("stock", new StockRequest());
        model.addAttribute("stocks", stockService.getStockList());
        // ใช้ FlowerService getAllFlowers
        model.addAttribute("options", flowerService.getFlowers());
        return "stock";
    }


    @PostMapping("/stock")
    public String createStock(@ModelAttribute StockRequest stock, Model model) {
        stockService.createStock(stock);
        return "redirect:/stock";
    }

    @GetMapping("/stock{id}")
    public String showStockDetailPage(Model model, @PathVariable int id) {
        model.addAttribute("stock", stockService.getFlowerById(id));
        model.addAttribute("method", "PUT");
        return "stock-detail";
    }

    @GetMapping("/stock/create")
    public String showStockDetailPageCreate(Model model) {
        model.addAttribute("stock", new StockRequest());
        model.addAttribute("method", "POST");
        return "flower-detail";
    }
//    @GetMapping("/stock")
//    public String getStockPage(Model model) {
//        return "stock";
//    }
}