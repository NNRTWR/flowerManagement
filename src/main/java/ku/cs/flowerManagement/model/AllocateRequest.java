package ku.cs.flowerManagement.model;

import ku.cs.flowerManagement.entity.Allocate;
import lombok.Data;

import java.util.List;

@Data
public class AllocateRequest {
    private List<AllocateModel> allocateModels;
}
