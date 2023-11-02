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
    private int OID; //รหัส Order //ใช้ method เพิ่มเลขเอา

    @ManyToOne
    @JoinColumn(name = "FID")
    private Flower flower;

    private int quantity; //จำนวนดอกไม้

    private OrderStatus status; // ตั้งค่าสถานะเริ่มต้น Complete, Pending, Canceled;
    private double price;

    private OrderMethods order_method; // วิธีสั่งซื้อ

    @CreationTimestamp
    private LocalDateTime date;

    @OneToMany(mappedBy = "OID") // JPA join ให้
    private List<Allocate> listAllocate;
    // Constructors, getters, and setters
}

