package ku.cs.flowerManagement.repository;

import ku.cs.flowerManagement.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StockRepository extends JpaRepository<Stock, Integer> {
    List<Stock> findByFlower_FID(int FID);
}
