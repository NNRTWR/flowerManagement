package ku.cs.flowerManagement.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
public class Stock {

    @Id
    @GeneratedValue
    private int SID; //รหัส Stock
    private int total; //คงเหลือ
    private LocalDateTime time; //วันที่
    private int quantity; //จำนวนดอกไม้

    @ManyToOne
    private PlantOrder plantOrder;
}
