package ku.cs.flowerManagement.entity;

import jakarta.persistence.*;
import ku.cs.flowerManagement.common.OrderMethods;
import ku.cs.flowerManagement.common.PlantStatus;
import ku.cs.flowerManagement.common.Status;
import lombok.Data;

@Data
@Entity
@Table(name = "OrderFlower")
public class OrderFlower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int OID; //รหัส Order
    private int quantity; //จำนวนดอกไม้

    @Enumerated(EnumType.STRING)
    private Status status; // ตั้งค่าสถานะเริ่มต้น Complete, Pending, Canceled;
    private double price;
    @Enumerated(EnumType.STRING)
    private PlantStatus plant_status; // สถานะปลูก
    @Enumerated(EnumType.STRING)
    private OrderMethods order_method; // วิธีสั่งซื้อ

    @ManyToOne
    @JoinColumn(name = "FID")
    private Flower flower;


    // Constructors, getters, and setters
}
