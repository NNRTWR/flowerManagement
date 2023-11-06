package ku.cs.flowerManagement.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import ku.cs.flowerManagement.entity.Flower;

import ku.cs.flowerManagement.entity.OrderItem;
import ku.cs.flowerManagement.entity.Stock;
import ku.cs.flowerManagement.model.FlowerRequest;
import ku.cs.flowerManagement.model.InvoiceRequest;

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




    @RequestMapping("/seller/invoice")
    public ModelAndView invoiceView(Map<String,Object> model) {
        model.put("stocks",stockService.getStockList());
        return new ModelAndView("invoice/invoicePage");
    }


    @GetMapping("/seller/invoice")
    public String showInvoicePage(Model model, @RequestParam(name = "id", defaultValue = "0" ) int id) throws JsonProcessingException, IllegalAccessException {
//        JSONArray options = new JSONArray(orderService.getOrders());
//        String options = new Gson().toJson(orderService.getOrders());
        
        model.addAttribute("invoice", new InvoiceRequest());
        model.addAttribute("invoices", orderService.getPendingOrders());
        model.addAttribute("options", orderService.getPendingOrders());
        model.addAttribute("stock",null);
        model.addAttribute("stock",new Stock());
        model.addAttribute("totalOrders", orderService.getTotalOrderCount());
//        model.addAttribute("stocks", stockService.getStockList());
//        System.out.println(stockService.getStockList());

        // ใช้ FlowerService getAllFlowers
//        model.addAttribute("options", flowerService.getFlowers());
//        System.out.println("Test: "+ orderService.getOrders());

        if (id != 0) {
            model.addAttribute("confirmInvoice",orderService.getOrderById(id));
        }
        else {
            model.addAttribute("confirmInvoice", new OrderItem());
        }
//        System.out.println(orderService.getOrders().toString());


        return "invoice";
    }

    @PostMapping("/seller/invoice")
    public String createInvoice(@ModelAttribute InvoiceRequest invoice,
                                Model model) {
        System.out.println("Invoice จ้า : "+invoice);
        invoice.setFlowerPrice(invoice.getFlowerPrice() * invoice.getOrderQuantity());
        invoiceService.createInvoice(invoice);
        return "redirect:/seller/invoice";
    }

    @PostMapping("/seller/invoiceConfirm")
    public String stockConfirm(Model model ,@RequestParam("FID") int FID , @RequestParam("OID") int OID) {
        int stockData = stockService.calculateStock(FID);
        List<Stock> stockLists = stockService.getStockByFID(FID);
//        Stock stockData = stockService.getStockByFID(FID);

        model.addAttribute("invoices", orderService.getPendingOrders());
        model.addAttribute("invoice", orderService.getOrderById(OID));
        model.addAttribute("options", orderService.getPendingOrders());
        model.addAttribute("stock",stockData);
        model.addAttribute("stockList",stockLists);
        // ใช้ FlowerService getAllFlowers
//        model.addAttribute("options", flowerService.getFlowers());
        return "invoice";
    }


    @PostMapping("/seller/invoiceCompleteButton")
    public String stockComplete(Model model , @RequestParam("OID") int OID  , @RequestParam("orderQuantity") int orderQuantity , @RequestParam("FID") int FID){
        invoiceService.InvoiceComplete(OID  , orderQuantity ,FID);
        return "redirect:/seller/allocate";
    }



    @PutMapping("/seller/invoice/{id}")
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


//    @GetMapping("/charge-money")
//    public String getChargeMoneyPage(Model model) {
//        return "charge-money";
//    }
}
