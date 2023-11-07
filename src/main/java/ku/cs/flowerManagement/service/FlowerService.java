package ku.cs.flowerManagement.service;

import ku.cs.flowerManagement.entity.Flower;
import ku.cs.flowerManagement.model.FlowerRequest;
import ku.cs.flowerManagement.repository.FlowerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Collections;

@Service
public class FlowerService {

    @Autowired
    private FlowerRepository flowerRepository;

    @Autowired
    private ModelMapper modelMapper;

    private int numFlower = 0;

    // Get Flower
    public List<FlowerRequest> getFlowers() {
        return flowerRepository.findAll().stream().map(flower -> modelMapper.map(flower, FlowerRequest.class)).collect(Collectors.toList());
    }

    public List<Flower> getAllFlower(){ //แสดง flower ทั้งหมด
        return flowerRepository.findAll();
    }

    public Page<Flower> getFlowers(int page, int size) {   //สำหรับ pagination
        Pageable pageable = PageRequest.of(page, size);
        return flowerRepository.findAll(pageable);
    }


    public Flower getOneFlower(int id){ //แสดง flower ที่เลือก
        return flowerRepository.findById(id).get();
    }

    
    public void addDefaultFlower() {
        FlowerRequest flower1 = new FlowerRequest();
        flower1.setFName("มะลิโตเต็มวัย");
        flower1.setPrice(60);
        flower1.setSeedPeriod(0);
        flower1.setSproutPeriod(0);
        flower1.setGrowingPeriod(0);
        flower1.setFullyGrownPeriod(0);
        flower1.setHarvestPeriod(1);
        flower1.setSeedPeriod(0);
        flower1.setHow_to_plant("ดินร่วน ไม่อมน้ำ");
        flower1.setHow_to_harvest(2);
        flower1.setHow_to_take_care("ห้ามรดน้ำเกิน 2 ครั้งต่อวัน โดนแดดไม่ได้");
        flower1.setQuantity(30);

      
        
    } 

    // Create Flower
    public FlowerRequest addFlower(FlowerRequest flowerRequest) {
        Flower existingFlower = flowerRepository.findByFName(flowerRequest.getFName());
        if (existingFlower != null) {
            throw new RuntimeException("ชื่อดอกไม้ห้ามซ้ำ");
        }
        Flower flower = modelMapper.map(flowerRequest, Flower.class);
        numFlower++ ; //ปลูกสำเร็จก็จะมาเพิ่มจำนวนดอกไม้ในระบบ
        flower.setFID(numFlower);
        return modelMapper.map(flowerRepository.save(flower), FlowerRequest.class);
    }

    public int getTotalFlowerCount() {
        List<Flower> flowers = flowerRepository.findAll();
        return flowers.size();
    }
    
    // Update Flower
    public FlowerRequest updateFlower(FlowerRequest flowerRequest) {
        //        System.out.println(flowerRequest.getFID());
                Flower flower1 =flowerRepository.findById(flowerRequest.getFID()).orElse(null);
                if (flower1 == null) return null;
                flower1.setFName(flowerRequest.getFName());
                flower1.setPrice(flowerRequest.getPrice());
                flower1.setSeedPeriod(flowerRequest.getSeedPeriod());
                flower1.setSproutPeriod(flowerRequest.getSproutPeriod());
                flower1.setGrowingPeriod(flowerRequest.getGrowingPeriod());
                flower1.setFullyGrownPeriod(flowerRequest.getFullyGrownPeriod());
                flower1.setHarvestPeriod(flowerRequest.getHarvestPeriod());
                flower1.setSeedPeriod(flowerRequest.getSeedPeriod());
                flower1.setHow_to_plant(flowerRequest.getHow_to_plant());
                flower1.setHow_to_harvest(flowerRequest.getHow_to_harvest());
                flower1.setHow_to_take_care(flowerRequest.getHow_to_take_care());
                
                flowerRepository.save(flower1);
        //        System.out.println("Flower1");
        //        System.out.println(flower1.getFID());
                return modelMapper.map(flower1, FlowerRequest.class);
            }
    }



