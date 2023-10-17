package ku.cs.flowerManagement.repository;

import ku.cs.flowerManagement.entity.PlantOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlantOrderRepository extends JpaRepository<PlantOrder, UUID> {
    List<PlantOrder> findByPID(int PID); //หาจากเลขที่แปลง


}
