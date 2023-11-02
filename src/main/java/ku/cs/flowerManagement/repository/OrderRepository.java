package ku.cs.flowerManagement.repository;

import ku.cs.flowerManagement.common.OrderStatus;
import ku.cs.flowerManagement.entity.OrderItem;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findByStatus(OrderStatus status); //หา order ด้วย status
    OrderItem findByOID(int OID);


}
