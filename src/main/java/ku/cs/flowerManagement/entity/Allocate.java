package ku.cs.flowerManagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "Allocate")
public class Allocate {

    @Id
    @GeneratedValue
    private UUID id;

    private double amount; //จำนวนการตัดสต็อค
    private int stockChanged;
    @ManyToOne
    @JoinColumn(name = "OID")
    private OrderItem OID; //รหัส Order

    @ManyToOne
    @JoinColumn(name = "SID")
    private Stock SID;

    //เก็บไว้แค่ดาต้าเบส
    /*
    SID ตัดจากออเดอร์ไหน
    OID ออเดอร์ไหนเป็นเจ้าของ
    amount จำนวนที่ตัดไป
     */
}