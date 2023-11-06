package ku.cs.flowerManagement.repository;

import ku.cs.flowerManagement.entity.Flower;
import ku.cs.flowerManagement.entity.Member;
import ku.cs.flowerManagement.model.FlowerRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import java.util.UUID;

public interface FlowerRepository extends JpaRepository<Flower, Integer> {
    Flower findByFID(int FID); //หาด้วยรหัสดอกไม้
    Flower findByFName(String FName);  
}
