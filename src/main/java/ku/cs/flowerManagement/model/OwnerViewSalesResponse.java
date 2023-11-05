package ku.cs.flowerManagement.model;

import lombok.Data;

@Data
public class OwnerViewSalesResponse {

    private String flowerName; // ชื่อดอกไม้
    private int harvestAmount; // จำนวนดอกไม้ที่เก็บเกี่ยวได้
    private int deadAmount; // จำนวนดอกไม้ที่ตาย
    private int salesAmount; // จำนวนดอกไม้ที่ขายได้
    private float balanceAmount; // ยอดเงินที่ได้
}
