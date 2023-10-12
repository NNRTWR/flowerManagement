package ku.cs.flowerManagement.repository;

import ku.cs.flowerManagement.common.OrderStatus;
import ku.cs.flowerManagement.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GardenerOrderRepository extends JpaRepository<OrderItem, LocalDateTime> {
    List<OrderItem> findByStatus(OrderStatus status); //หา order ด้วย status

    //หาด้วย flower
}
