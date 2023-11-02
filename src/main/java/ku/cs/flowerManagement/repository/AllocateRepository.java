package ku.cs.flowerManagement.repository;

import ku.cs.flowerManagement.entity.Allocate;
import ku.cs.flowerManagement.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AllocateRepository extends JpaRepository<Allocate, UUID> {
}
