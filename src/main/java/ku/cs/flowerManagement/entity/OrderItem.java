package ku.cs.flowerManagement.entity;

import jakarta.persistence.*;
import ku.cs.flowerManagement.common.OrderMethods;
import ku.cs.flowerManagement.common.FlowerStatus;
import ku.cs.flowerManagement.common.OrderStatus;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class OrderItem {
    @Id
    @GeneratedValue
    private UUID id;

    private int OID; //รหัส Order //ใช้ method เพิ่มเลขเอา

    @ManyToOne
    private Flower flower; //สั่งซื้อดอกไม้อะไร

    private int quantity; //จำนวนดอกไม้

    private OrderStatus status; // ตั้งค่าสถานะเริ่มต้น Complete, Pending, Canceled;
    private double price;

    private OrderMethods order_method; // วิธีสั่งซื้อ

    @CreationTimestamp
    private LocalDateTime date;


    @OneToMany(mappedBy = "order") // JPA join ให้
    private List<Allocate> listAllocate;

    @OneToOne(mappedBy = "order")
    private PlantOrder plantOrder;
}

