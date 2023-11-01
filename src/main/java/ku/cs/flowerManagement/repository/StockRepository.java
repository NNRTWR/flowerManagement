package ku.cs.flowerManagement.repository;

import ku.cs.flowerManagement.entity.Flower;
import ku.cs.flowerManagement.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {

    List<Stock> findByFlower_FID(int FID);
}