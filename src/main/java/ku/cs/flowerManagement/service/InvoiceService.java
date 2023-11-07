package ku.cs.flowerManagement.service;

import jakarta.persistence.EntityNotFoundException;
import ku.cs.flowerManagement.entity.Allocate;
import ku.cs.flowerManagement.entity.Flower;
import ku.cs.flowerManagement.entity.Invoice;
import ku.cs.flowerManagement.entity.OrderItem;
import ku.cs.flowerManagement.model.InvoiceRequest;
import ku.cs.flowerManagement.repository.AllocateRepository;
import ku.cs.flowerManagement.repository.FlowerRepository;
import ku.cs.flowerManagement.repository.InvoiceRepository;
import ku.cs.flowerManagement.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private FlowerRepository flowerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AllocateService allocateService;

    @Autowired
    private StockService stockService;


    @Autowired
    private AllocateRepository allocateRepository;

//    public List<InvoiceRequest> getInvoices() {
//        return invoiceRepository.findAll().stream().map(invoices -> modelMapper.map(invoices, InvoiceRequest.class)).collect(Collectors.toList());
//    }

    public List<InvoiceRequest> getInvoices() {
        List<Invoice> invoices = invoiceRepository.findAll();
        List<InvoiceRequest> invoiceRequests = new ArrayList<>();
        for (Invoice iv:invoices) {
            InvoiceRequest invoiceRequest = modelMapper.map(iv, InvoiceRequest.class);
            invoiceRequest.setFName(iv.getFlower().getFName());
            invoiceRequest.setFID(iv.getFlower().getFID());
            invoiceRequest.setOID(iv.getOID().toString());
            invoiceRequests.add(invoiceRequest);
        }
        return invoiceRequests;
    }
    public Page<OrderItem> getInvoicePage(int page, int size) {   //สำหรับ pagination
        Pageable pageable = PageRequest.of(page, size);
        return orderRepository.findAll(pageable);
    }

    // Get order By Id
    public InvoiceRequest getInvoiceById(int id) {
        Invoice invoice = invoiceRepository.findById(id).orElse(null);
        if (invoice == null) {
            throw new EntityNotFoundException();
        }
        InvoiceRequest invoiceRequest = modelMapper.map(invoice, InvoiceRequest.class);
        invoiceRequest.setFName(invoice.getFlower().getFName());
        invoiceRequest.setFID(invoice.getFlower().getFID());
        System.out.println(invoiceRequest.toString());
        return invoiceRequest;
    }


    public void createInvoice(InvoiceRequest invoiceRequest) {
        Invoice invoice = modelMapper.map(invoiceRepository, Invoice.class);
        System.out.println("Invoice"+invoice);

    }


    public void InvoiceComplete(int OID , int total , int FID) {
        orderService.confirmOrderById(Integer.parseInt(String.valueOf(OID)));
        stockService.updateStock(total , FID , OID);
//        allocateService.createAllocate(OID , SID , amount);
    }

}