package ku.cs.flowerManagement.entity;


import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import ku.cs.flowerManagement.common.StockStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "Stock")
public class Stock {

    @Id
    @GeneratedValue
    private int SID; //รหัส Stock lot

    private int total; //คงเหลือ
    private LocalDateTime time; //วันที่
    private int quantity; //จำนวนดอกไม้

    @ManyToOne
    private PlantOrder plantOrder;
    
//    private UUID id;
    @Enumerated(EnumType.STRING)
    private StockStatus stockStatus;

    @ManyToOne
    @JoinColumn(name = "FID")
    @JsonIgnore
    private Flower flower;
}
