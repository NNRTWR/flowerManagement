package ku.cs.flowerManagement.model;

import lombok.Data;

import java.util.Date;

@Data
public class StockRequest {

    private int SID; //รหัส Stock.
    private int total; //คงเหลือ.
    private int quantity; //จำนวนดอกไม้
    private Date time; //วันที่
    private Enum stockStatus;
    private int FID; //รหัสดอกไม้




}
