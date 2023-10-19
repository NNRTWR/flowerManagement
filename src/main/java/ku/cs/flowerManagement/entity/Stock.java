package ku.cs.flowerManagement.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
public class Stock {

    @Id
    @GeneratedValue
    private UUID id;

    private int SID; //รหัส Stock
    private Flower flower;
    private int total; //คงเหลือ
    private Date time; //วันที่
    private int quantity; //จำนวนดอกไม้

    @OneToOne
    private PlantOrder plantOrder;
}
