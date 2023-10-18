package ku.cs.flowerManagement.entity;

import jakarta.persistence.*;
import ku.cs.flowerManagement.common.OrderMethods;
import ku.cs.flowerManagement.common.OrderStatus;
import lombok.Data;
import org.apache.logging.log4j.util.PropertySource;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;


@Entity
@Data
public class GardenerOrder {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Flower flower; //สั่งซื้อดอกไม้อะไร

    private int quantity; //จำนวนดอกไม้

    private OrderStatus status; // ตั้งค่าสถานะเริ่มต้น Complete, Pending, Canceled;

    @CreationTimestamp
    private LocalDateTime date;

    @OneToOne(mappedBy = "gardener_order")
    private PlantOrder plantOrder;
}
