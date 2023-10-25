//package ku.cs.flowerManagement.controller;
//
//import ku.cs.flowerManagement.model.pRequest;
//import ku.cs.flowerManagement.service.PlantOrderService;
//import ku.cs.flowerManagement.service.StockService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//@RequestMapping("/stocks")
//public class StockController {
//    @Autowired
//    private StockService stockService;
//
//    @Autowired
//    private PlantOrderService plantOrderService;
//
//    @GetMapping
//    private String getAllStocks(Model model){
//        model.addAttribute("stocks", stockService.getAllStocks());
//        model.addAttribute("plantOrders", plantOrderService.getAllPlantOrderButNoHarvested());
//        return "stock";
//    }
//
//    @PostMapping("/create")
//    private String getForm(@ModelAttribute pRequest pRequest, Model model){
//        stockService.setStock(pRequest);
//        model.addAttribute("stocks", stockService.getAllStocks());
//        model.addAttribute("plantOrders",plantOrderService.getAllPlantOrderButNoHarvested());
//        return "stock";
//    }
//
//}
