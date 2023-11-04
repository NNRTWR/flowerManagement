package ku.cs.flowerManagement.controller;

import ku.cs.flowerManagement.entity.Stock;
import ku.cs.flowerManagement.model.FlowerRequest;
import ku.cs.flowerManagement.model.StockRequest;
import ku.cs.flowerManagement.repository.FlowerRepository;
import ku.cs.flowerManagement.service.FlowerService;
import ku.cs.flowerManagement.service.InvoiceService;
import ku.cs.flowerManagement.service.OrderService;
import ku.cs.flowerManagement.service.StockService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
public class StockController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private FlowerService flowerService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FlowerRepository flowerRepository;


    @Autowired
    private StockService stockService;



//    @RequestMapping

    @GetMapping("/stock")
    public String showFlowerPage(Model model) {
        model.addAttribute("stock", new StockRequest());
        model.addAttribute("stocks", stockService.getStockList());
        // ใช้ FlowerService getAllFlowers
//        model.addAttribute("options", flowerService.getFlowers());
        model.addAttribute("options", flowerService.getFlowers());
        return "stock";
    }



    @GetMapping("/stock{id}")
    public String showStockDetailPage(Model model, @PathVariable int id) {
        model.addAttribute("stock", stockService.getFlowerById(id));
        model.addAttribute("method", "PUT");
        return "stock-detail";
    }

    @PostMapping("/stock")
    public String createStock(@ModelAttribute StockRequest stock, Model model) {
        stockService.createStock(stock);
        return "redirect:/stock";
    }

    @GetMapping("/stock/create")
    public String showStockDetailPageCreate(Model model) {
        model.addAttribute("stock", new StockRequest());
        model.addAttribute("method", "POST");
        return "flower-detail";
    }
//
//    @PostMapping("/stock")
//    public String createStock(@ModelAttribute StockRequest stock) {
//        stockService.addStock(stock);
//        return "redirect:/stock";
//    }
//
//    @PutMapping("/stock/{id}")
//    public String updateStock(@ModelAttribute FlowerRequest flower, @PathVariable int id) {
//        flower.setFID(id);
//        flowerService.updateFlower(flower);
//        return "redirect:/flower";
//    }

}
