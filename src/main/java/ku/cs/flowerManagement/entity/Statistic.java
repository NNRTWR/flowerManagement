package ku.cs.flowerManagement.entity;

import lombok.Data;
import org.springframework.security.core.Transient;

import java.util.List;

@Data
@Transient
public class Statistic {
    private Flower flower;
    private List<PlantOrder> plantOrder;

}
