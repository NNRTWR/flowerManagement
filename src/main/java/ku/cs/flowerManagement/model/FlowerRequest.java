package ku.cs.flowerManagement.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Data
public class FlowerRequest {

    private int FID; //รหัสของดอกไม้
//    private String FName; //ชื่อดอกไม้
//    private String how_to_plant; //วิธีการปลูก
//    private String how_to_take_care; //วิธีการดูแล
//    private int time; //ระยะเวลาในการเติบโต (วัน)
//    private int how_to_harvest; //รูปแบบการเก็บดอกไม้ (เก็บครั้งเดียว=1, เก็บหลายครั้ง=ใส่จำนวนที่สามารถเก็บเกี่ยวได้)
//    private double price; //ราคาของดอกไม้
//    private String pic; //รูปภาพของดอกไม้
//    private int quantity; //จำนวนดอกไม้

    private String FName; //ชื่อดอกไม้
    private String how_to_plant; //วิธีการปลูก
    private String how_to_take_care; //วิธีการดูแล
    private int seedPeriod;
    private int sproutPeriod;
    private int growingPeriod;
    private int fullyGrownPeriod;
    private int harvestPeriod;
    private int how_to_harvest; //รูปแบบการเก็บดอกไม้ (เก็บครั้งเดียว=1, เก็บหลายครั้ง=ใส่จำนวนที่สามารถเก็บเกี่ยวได้)
    private double price; //ราคาของดอกไม้
    //    private String pic; //รูปภาพของดอกไม้ //ต้องมีด้วยรึ ไว้ก่อน
    private int quantity; //จำนวนดอกไม้

}
