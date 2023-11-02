package ku.cs.flowerManagement.service;

import ku.cs.flowerManagement.entity.Allocate;
import ku.cs.flowerManagement.entity.Flower;
import ku.cs.flowerManagement.entity.OrderItem;
import ku.cs.flowerManagement.entity.Stock;
import ku.cs.flowerManagement.model.AllocateModel;
import ku.cs.flowerManagement.model.AllocateRequest;
import ku.cs.flowerManagement.repository.AllocateRepository;
import ku.cs.flowerManagement.repository.FlowerRepository;
import ku.cs.flowerManagement.repository.OrderRepository;
import ku.cs.flowerManagement.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AllocateService {
    @Autowired
    private AllocateRepository allocateRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StockRepository stockRepository;


    public void createAllocate(int OID , int SID , double amount){
        Allocate allocate = new Allocate();
        Optional<OrderItem> orderItem = orderRepository.findById(OID);
        Optional<Stock> stock = stockRepository.findById(SID);
        if(orderItem.isPresent() || stock.isPresent()){
            allocate.setOID(orderItem.get());
            allocate.setSID(stock.get());
            allocate.setAmount(amount);
        }
        allocateRepository.save(allocate);
    }

    public AllocateRequest findAllAllocate(){
        Map<String, Double> dataFlower = new HashMap<>();
        List<Allocate> allocates = allocateRepository.findAll();

        for (Allocate allocate : allocates) {
            OrderItem orderItem = allocate.getOID();
            if (Objects.nonNull(orderItem)) {
                if (dataFlower.containsKey(orderItem.getFlower().getFName())) {
                    Double amount = dataFlower.get(orderItem.getFlower().getFName());
                    amount += orderItem.getPrice();
                    dataFlower.put(orderItem.getFlower().getFName(), amount);
                } else {
                    dataFlower.put(orderItem.getFlower().getFName(),orderItem.getPrice());
                }
            }
        }

        List<AllocateModel> allocateModels = new ArrayList<>();
        for (Map.Entry<String, Double> entry : dataFlower.entrySet()) {
            AllocateModel allocateModel = new AllocateModel();
            allocateModel.setFName(entry.getKey());
            allocateModel.setTotal(entry.getValue());
            allocateModels.add(allocateModel);
        }


        AllocateRequest allocateRequest = new AllocateRequest();
        allocateRequest.setAllocateModels(allocateModels);

        return allocateRequest;
    }

}
