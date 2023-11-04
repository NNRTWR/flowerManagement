package ku.cs.flowerManagement.repository;

import ku.cs.flowerManagement.common.OrderStatus;
import ku.cs.flowerManagement.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface StockRepository extends JpaRepository<Stock, Integer> {
    List<Stock> findByFlower_FID(int FID);

    @Query("select sum(s.quantity) from Stock s where s.flower.FID = :flower")
    Integer sumQuantityFromStockByFlower(@Param("flower") int flower);
}
