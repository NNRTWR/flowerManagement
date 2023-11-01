package ku.cs.flowerManagement.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import ku.cs.flowerManagement.common.StockStatus;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "Stock")
public class Stock {

    @Id
    @GeneratedValue
    private int SID; // This field will be auto-incremented รหัส Stock lot

    private int total; //คงเหลือ
    private Date time; //วันที่
    private int quantity; //จำนวนดอกไม้
    //    private UUID id;
//    @Enumerated(EnumType.STRING)
//    private StockStatus stockStatus;

    @ManyToOne
    @JoinColumn(name = "FID")
    @JsonIgnore
    private Flower flower;
}