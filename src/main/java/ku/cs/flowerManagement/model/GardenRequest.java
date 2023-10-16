package ku.cs.flowerManagement.model;

import ku.cs.flowerManagement.common.FlowerStatus;
import lombok.Data;

import java.util.UUID;


@Data
public class GardenRequest {
    private UUID id; //type of flower
    private int size; // size of this garden ex 1000 mean 1000 flower in this field
    private FlowerStatus status;// as it name
    private int plantDate; // the day we start plant flower to this field
}
