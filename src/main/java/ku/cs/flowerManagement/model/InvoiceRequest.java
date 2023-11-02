package ku.cs.flowerManagement.model;
import ku.cs.flowerManagement.common.OrderMethods;
import ku.cs.flowerManagement.common.FlowerStatus;
import ku.cs.flowerManagement.common.OrderStatus;
import lombok.Data;

@Data
public class InvoiceRequest {
    private int FID;
    private String OID;
    private String FName;
    private double flowerPrice;
    private int orderQuantity;
    private OrderStatus status;
    private OrderMethods order_method;
    private FlowerStatus plant_status;
    private double price;
}
