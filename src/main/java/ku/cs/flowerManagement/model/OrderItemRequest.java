package ku.cs.flowerManagement.model;

import ku.cs.flowerManagement.common.OrderMethods;
import ku.cs.flowerManagement.common.FlowerStatus;
import ku.cs.flowerManagement.common.OrderStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class OrderItemRequest {

    private int FID;
    private int OID;
    
    private String FName;
    private double flowerPrice;
    private int orderQuantity;
    private OrderStatus status;
    private OrderMethods order_method;
    private FlowerStatus plant_status;
    private UUID flowerID;
    private int quantity;
    private double price;

}
