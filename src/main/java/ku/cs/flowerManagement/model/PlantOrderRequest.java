package ku.cs.flowerManagement.model;

import lombok.Data;

import java.util.UUID;

@Data
public class PlantOrderRequest {
    // กดเลือก id ดอกไม้
    private UUID flowerID;
}
