package ku.cs.flowerManagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.Transient;

import java.util.List;
import java.util.UUID;

@Data
@Transient
public class Statistic {
    private Flower flower;
    private List<Garden> garden;
}
