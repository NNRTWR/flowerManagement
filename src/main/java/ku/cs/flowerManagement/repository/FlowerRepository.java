package ku.cs.flowerManagement.repository;

import ku.cs.flowerManagement.entity.Flower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FlowerRepository extends JpaRepository<Flower, UUID> {
    Flower findByFID(int FID); //หาด้วยรหัสดอกไม้

}
