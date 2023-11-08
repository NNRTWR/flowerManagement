package ku.cs.flowerManagement.service;

import ku.cs.flowerManagement.entity.Flower;
import ku.cs.flowerManagement.entity.Invoice;
import ku.cs.flowerManagement.entity.OrderItem;
import ku.cs.flowerManagement.model.InvoiceRequest;
import ku.cs.flowerManagement.repository.InvoiceRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InvoiceServiceTest {
    @InjectMocks
    private InvoiceService invoiceService;

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private OrderService orderService;

    @Mock
    private StockService stockService;

    @Spy
    private ModelMapper modelMapper;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetInvoice(){
        MockitoAnnotations.openMocks(this);

        Flower flower = new Flower();
        flower.setFName("rose");
        flower.setFID(5);

        Flower flower1 = new Flower();
        flower1.setFName("lily");
        flower1.setFID(1);

        Invoice invoice1 = new Invoice();
        invoice1.setFlower(flower);
        invoice1.setOID(new OrderItem());


        Invoice invoice2 = new Invoice();
        invoice2.setFlower(flower);
        invoice2.setOID(new OrderItem());

        List<Invoice> mockInvoices = new ArrayList<>();
        mockInvoices.add(invoice1);
        mockInvoices.add(invoice2);

        when(invoiceRepository.findAll()).thenReturn(mockInvoices);

        List<InvoiceRequest> invoiceRequests = new ArrayList<>();
        invoiceRequests = invoiceService.getInvoices();

        assertEquals(2, invoiceRequests.size());

    }
}
