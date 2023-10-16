package ku.cs.flowerManagement.entity;

import jakarta.persistence.*;
import ku.cs.flowerManagement.common.FlowerStatus;
import lombok.Data;

import java.sql.Date;
import java.util.UUID;
@Entity
@Data
public class Garden {
    @Id
    @GeneratedValue
    private UUID id;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int gardenNO; //show user which field is it
    @ManyToOne
    private Flower flower; //type of flower
    private FlowerStatus status;// as it name
    private Date plantDate; /* the day we start plant flower to this field,use java.sql
                               if u use util it will store time like hh:mm:ss which is useless*/
}
