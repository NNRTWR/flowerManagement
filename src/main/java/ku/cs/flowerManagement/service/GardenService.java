package ku.cs.flowerManagement.service;

import ku.cs.flowerManagement.entity.Garden;
import ku.cs.flowerManagement.model.GardenRequest;
import ku.cs.flowerManagement.repository.GardenRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GardenService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private GardenRepository gardenRepository;
    public void addGarden(GardenRequest gardenRequest){
        Garden record = modelMapper.map(gardenRequest, Garden.class);
        gardenRepository.save(record);
    }
    public List<Garden> getAllGarden() {
        return gardenRepository.findAll();
    }

}
