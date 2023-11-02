package ku.cs.flowerManagement.entity;

import jakarta.persistence.*;
import ku.cs.flowerManagement.common.OrderMethods;
import ku.cs.flowerManagement.common.FlowerStatus;
import ku.cs.flowerManagement.common.OrderStatus;
import lombok.Data;
import org.springframework.lang.NonNull;

@Data
@Entity
@Table(name = "Invoice")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int IID; //รหัส invoice

    @Enumerated(EnumType.STRING)
    @NonNull
    private OrderMethods order_method; // วิธีสั่งซื้อ

    private int quantity; //จำนวนดอกไม้

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // ตั้งค่าสถานะเริ่มต้น Complete, Pending, Canceled;

    private double total ;

    @ManyToOne
    @JoinColumn(name = "FID")
    private Flower flower;

    @OneToOne
    @JoinColumn(name = "OID")
    private OrderItem OID; //รหัส Order

    public Invoice() {

    }
}
