package ku.cs.flowerManagement.repository;

import ku.cs.flowerManagement.entity.PlantOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlantOrderRepository extends JpaRepository<PlantOrder, UUID> {
    PlantOrder findByPID(int PID); //หาจากเลขที่แปลง
}
