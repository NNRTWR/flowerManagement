package ku.cs.flowerManagement.repository;

import ku.cs.flowerManagement.common.OrderStatus;
import ku.cs.flowerManagement.entity.GardenerOrder;
import ku.cs.flowerManagement.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface GardenerOrderRepository extends JpaRepository<GardenerOrder, UUID> {
    List<GardenerOrder> findByStatus(OrderStatus status); //หา order ด้วย status
    Page<GardenerOrder> findByStatus(OrderStatus status, Pageable pageable);
    List<GardenerOrder> findAllByStatus(OrderStatus orderStatus);
    Page<GardenerOrder> findAllByStatus(OrderStatus status, Pageable pageable);
    
    
    //หาด้วย flower
}
