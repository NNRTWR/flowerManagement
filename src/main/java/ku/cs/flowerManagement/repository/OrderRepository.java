package ku.cs.flowerManagement.repository;

import ku.cs.flowerManagement.common.OrderStatus;
import ku.cs.flowerManagement.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findByStatus(OrderStatus status); //หา order ด้วย status

}
