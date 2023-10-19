package ku.cs.flowerManagement.entity;

import jakarta.persistence.*;
import ku.cs.flowerManagement.common.FlowerStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class PlantOrder {

    @Id
    @GeneratedValue
    private UUID id; // id and PID double primary key for what?????

    private int PID; //รหัสของแปลงปลูก

    @ManyToOne
    private Flower flower; //จากคำสั่งปลูกอันนี้ เราจะหาได้ว่ามันปลูกอะไร

    private int quantity; //จำนวนที่ปลูก
    private int total; //จำนวนดอกไม้ที่คงเหลืออยู่

    private LocalDateTime timePlant; //วันที่ปลูก

    @OneToOne
    private GardenerOrder gardener_order;

    private FlowerStatus flowerStatus; //ปลูกไปได้ระยะไหนแล้ว

    @OneToOne
    private Stock stock; //คำสั่งปลูกอันนี้ คือ stock อันไหน (ถ้ามี = เก็บเกี่ยวแล้ว)
    private int harvestable; //can harvest n times n-1 everytime

}
