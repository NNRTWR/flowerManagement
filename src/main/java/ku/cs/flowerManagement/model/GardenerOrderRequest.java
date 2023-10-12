package ku.cs.flowerManagement.model;



import lombok.Data;

import java.util.UUID;

@Data
public class GardenerOrderRequest {
    private UUID flowerID;
    private int quantity;
}
