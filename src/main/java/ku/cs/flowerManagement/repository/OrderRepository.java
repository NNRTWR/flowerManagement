package ku.cs.flowerManagement.repository;

import ku.cs.flowerManagement.common.OrderStatus;
import ku.cs.flowerManagement.entity.Flower;
import ku.cs.flowerManagement.entity.OrderItem;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findByStatus(OrderStatus status); //หา order ด้วย status
    OrderItem findByOID(int OID);

    @Query("select sum(o.quantity) from OrderItem o where o.status = :status and o.flower.FID = :flower")
    Integer sumFlowerStatusFromOrder(@Param("flower") int flower, @Param("status") OrderStatus status);

    @Query("select sum(o.price) from OrderItem o where o.status = :status and o.flower.FID = :flower")
    Float sumPriceStatusFromOrder(@Param("flower") int flower, @Param("status") OrderStatus status);


}
