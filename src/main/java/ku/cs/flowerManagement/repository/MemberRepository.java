//Patcharin Khangwicha 6410406797
package ku.cs.flowerManagement.repository;

import ku.cs.flowerManagement.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface MemberRepository extends JpaRepository<Member, UUID> {
    Member findByUsername(String username); //จะหาด้วย username
}
