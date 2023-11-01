package ku.cs.flowerManagement.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import ku.cs.flowerManagement.common.PlantStatus;
import ku.cs.flowerManagement.common.Status;
import ku.cs.flowerManagement.entity.Flower;
import ku.cs.flowerManagement.entity.OrderFlower;
import ku.cs.flowerManagement.entity.Stock;
import ku.cs.flowerManagement.model.FlowerRequest;
import ku.cs.flowerManagement.model.InvoiceRequest;
import ku.cs.flowerManagement.model.OrderFlowerRequest;
import ku.cs.flowerManagement.model.StockRequest;
import ku.cs.flowerManagement.repository.AllocateRepository;
import ku.cs.flowerManagement.repository.FlowerRepository;
import ku.cs.flowerManagement.service.FlowerService;
import ku.cs.flowerManagement.service.InvoiceService;
import ku.cs.flowerManagement.service.OrderService;
import ku.cs.flowerManagement.service.StockService;
import org.json.JSONArray;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
public class InvoiceController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private FlowerService flowerService;

    @Autowired
    private StockService stockService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FlowerRepository flowerRepository;






    @GetMapping("/invoice")
    public String showInvoicePage(Model model, @RequestParam(name = "id", defaultValue = "0" ) int id) throws JsonProcessingException, IllegalAccessException {
//        JSONArray options = new JSONArray(orderService.getOrders());
//        String options = new Gson().toJson(orderService.getOrders());
        model.addAttribute("invoice", new InvoiceRequest());
        model.addAttribute("invoices", orderService.getOrders());
        model.addAttribute("options", orderService.getOrders());
        model.addAttribute("stock",null);
//        model.addAttribute("stocks", stockService.getStockList());
//        System.out.println(stockService.getStockList());

        // ใช้ FlowerService getAllFlowers
//        model.addAttribute("options", flowerService.getFlowers());
//        System.out.println("Test: "+ orderService.getOrders());

        if (id != 0) {
            model.addAttribute("confirmInvoice",orderService.getOrderById(id));
        }
        else {
            model.addAttribute("confirmInvoice", new OrderFlowerRequest());
        }
//        System.out.println(orderService.getOrders().toString());


        return "invoice";
    }

    @PostMapping("/invoice")
    public String createInvoice(@ModelAttribute InvoiceRequest invoice,
                                Model model) {
        System.out.println("Invoice จ้า : "+invoice);
        invoice.setFlowerPrice(invoice.getFlowerPrice() * invoice.getOrderQuantity());
        invoiceService.createInvoice(invoice);
        return "redirect:/invoice";
    }

    @PostMapping("/invoiceConfirm")
    public String stockConfirm(Model model ,@RequestParam("FID") int FID , @RequestParam("OID") int OID) {
        int stockData = stockService.calculateStock(FID);
        List<Stock> stockLists = stockService.getStockByFID(FID);
        model.addAttribute("invoices", orderService.getOrders());
        model.addAttribute("invoice", orderService.getOrderById(OID));
        model.addAttribute("options", orderService.getOrders());
        model.addAttribute("stock",stockData);
        model.addAttribute("stockList",stockLists);

        // ใช้ FlowerService getAllFlowers
//        model.addAttribute("options", flowerService.getFlowers());
        return "invoice";
    }


    @PostMapping("/invoiceCompleteButton")
    public String stockComplete(Model model , @RequestParam("OID") int OID ,@RequestParam("SIDList") List<Integer> SIDList, @RequestParam("amount") String amount , @RequestParam("orderQuantity") int orderQuantity , @RequestParam("FID") int FID){
        invoiceService.InvoiceComplete(OID , SIDList , Double.parseDouble(amount) , orderQuantity ,FID);
        return "redirect:/allocate";
    }



    @PutMapping("/invoice/{id}")
    public String confirmInvoice(@PathVariable int id, Model model) throws JsonProcessingException, IllegalAccessException {
        orderService.cancelOrderById(id);
        model.addAttribute("invoice", new InvoiceRequest());
        model.addAttribute("invoices", invoiceService.getInvoices());
        model.addAttribute("options", orderService.getOrders());
        //       System.out.println("Order List: "+orderService.getOrderList());
        model.addAttribute("confirmInvoice",invoiceService.getInvoiceById(id));
        model.addAttribute("stock");
        return "invoice";
    }

//    @PostMapping("/invoices")
//    public String printAllInvoices(@RequestBody InvoiceRequest invoice) {
//        System.out.println("Invoice: " +invoice);
//        return "Success";
//    }


    @GetMapping("/charge-money")
    public String getChargeMoneyPage(Model model) {
        return "charge-money";
    }
}


//import ku.cs.flowerManagement.model.CancelOrderRequest;
//import ku.cs.flowerManagement.model.InvoiceRequest;
//import ku.cs.flowerManagement.model.OrderFlowerRequest;
//import ku.cs.flowerManagement.service.FlowerService;
//import ku.cs.flowerManagement.service.InvoiceService;
//import ku.cs.flowerManagement.service.OrderService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//public class InvoiceController {
//
//    @Autowired
//    private OrderService orderService;
//
//    @Autowired
//    private FlowerService flowerService;
//
//    @Autowired
//    private InvoiceService invoiceService;
//
//    @GetMapping("/invoice")
//    private String showOrderPage(Model model, @RequestParam(name = "id", defaultValue = "0" ) int id) {
//        model.addAttribute("invoice", new InvoiceRequest());
//
//        model.addAttribute("invoices", invoiceService.getInvoices());
//        model.addAttribute("options", flowerService.getFlowers());
////        model.addAttribute("confirmOrder", orderService.getOrders());
//        model.addAttribute("confirmOrder", invoiceService.getInvoiceById(id));
//        System.out.println(orderService.getOrders().toString());
//        return "invoice";
//    }
//
//    @PostMapping("/invoice")
//    public String createOrder(@ModelAttribute OrderFlowerRequest orderFlower, Model model) {
//        System.out.println(orderFlower.getFlowerPrice());
//
//        orderFlower.setFlowerPrice(orderFlower.getFlowerPrice() * orderFlower.getOrderQuantity());
//        orderService.createOrder(orderFlower);
//        return "redirect:/invoice";
//    }
//
////    @PutMapping("/order/{id}")
////    public String cancelOrder(@PathVariable int id, Model model){
////        orderService.cancelOrderById(id);
////        model.addAttribute("order", new OrderFlowerRequest());
////        model.addAttribute("orders", orderService.getOrders());
////        model.addAttribute("options", flowerService.getFlowers());
////        model.addAttribute("canceledOrder",orderService.getOrderById(id));
////        return "order";
////    }
//
//    @PutMapping("/invoice/{id}")
//    public String confirmOrder(@PathVariable int id, Model model){
//        orderService.confirmOrderById(id);
//        model.addAttribute("order", new OrderFlowerRequest());
//        model.addAttribute("orders", orderService.getOrders());
//        model.addAttribute("options", flowerService.getFlowers());
//        model.addAttribute("confirmOrder", orderService.getOrderById(id));
//
//        return "invoice";
//    }
//
//
//}
