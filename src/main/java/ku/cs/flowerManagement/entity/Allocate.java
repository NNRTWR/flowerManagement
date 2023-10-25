package ku.cs.flowerManagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
public class Allocate {

    @Id
    @GeneratedValue
    private UUID id;

    private int amount; //จำนวนการตัดสต็อค
    private Date time; //วันที่

    @ManyToOne
    private OrderItem order;
}
