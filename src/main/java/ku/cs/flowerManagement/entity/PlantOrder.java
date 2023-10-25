package ku.cs.flowerManagement.entity;

import jakarta.persistence.*;
import ku.cs.flowerManagement.common.FlowerStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
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

    @OneToMany(mappedBy = "plantOrder")
    private List<Stock> listStock; //คำสั่งปลูกอันนี้ ถูกเก็บเป็น stock อันไหนบ้าง

    private int harvestable; //can harvest n times n-1 everytime

}
