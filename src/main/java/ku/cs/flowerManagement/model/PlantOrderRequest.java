package ku.cs.flowerManagement.model;

import lombok.Data;

import java.util.UUID;

@Data
public class PlantOrderRequest {
    // กดเลือก id ดอกไม้
    private int PID;
//    private UUID gardener_order_ID;
    private int deadPlant;//number of plant that died this time
}
