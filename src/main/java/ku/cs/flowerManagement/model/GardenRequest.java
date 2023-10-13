package ku.cs.flowerManagement.model;

import ku.cs.flowerManagement.common.FlowerStatus;
import lombok.Data;


@Data
public class GardenRequest {
    private String flowerName; //type of flower
    private int size; // size of this garden ex 1000 mean 1000 flower in this field
    private FlowerStatus status;// as it name
    private int plantDate; // the day we start plant flower to this field
}
