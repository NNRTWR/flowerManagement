package ku.cs.flowerManagement.model;

import lombok.Data;

import java.util.UUID;

@Data
public class gRequest {
    private UUID gardener_order_ID;
    private int PID;
}
