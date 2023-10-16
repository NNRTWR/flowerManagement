package ku.cs.flowerManagement.service;

import ku.cs.flowerManagement.entity.Flower;
import ku.cs.flowerManagement.entity.Garden;
import ku.cs.flowerManagement.entity.Statistic;
import ku.cs.flowerManagement.model.GardenRequest;
import ku.cs.flowerManagement.repository.FlowerRepository;
import ku.cs.flowerManagement.repository.GardenRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class GardenService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private GardenRepository gardenRepository;
    @Autowired
    private FlowerRepository flowerRepository;
    public void addGarden(GardenRequest gardenRequest){
        Garden record = modelMapper.map(gardenRequest, Garden.class);
        Flower flower = flowerRepository.findById(gardenRequest.getId()).get();
        record.setFlower(flower);
        gardenRepository.save(record);
    }
    public List<Garden> getAllGarden() {
        return gardenRepository.findAll();
    }

    public List<Garden> getById(UUID id) {
        return gardenRepository.findAll();
    }
    public List<Statistic> getAllGardenWithFlower(){
        List<Statistic> statistics = new ArrayList<>();
        List<Flower> flowers = flowerRepository.findAll();
        List<Garden> gardens = gardenRepository.findAll();

        for (Flower flower : flowers) {
            Statistic statistic = new Statistic();
            statistic.setFlower(flower);
            statistics.add(statistic);
        }

        for (Garden garden : gardens) {
            Flower gardenFlower = garden.getFlower();
            for (Statistic statistic : statistics) {
                if (statistic.getFlower() == gardenFlower) {
                    statistic.setGarden(new ArrayList<>());
                    statistic.getGarden().add(garden);
                    break;
                }
            }
        }
        statistics.removeIf(statistic -> statistic.getGarden() == null || statistic.getGarden().isEmpty());
        return statistics;
    }

}
