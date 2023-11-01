package ku.cs.flowerManagement.service;

import ku.cs.flowerManagement.entity.Allocate;
import ku.cs.flowerManagement.entity.Flower;
import ku.cs.flowerManagement.entity.OrderFlower;
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


    public void createAllocate(int OID ,  Integer SID , double amount, int stockChanged){
        Allocate allocate = new Allocate();
        Optional<OrderFlower> orderFlower = orderRepository.findById(OID);
        Optional<Stock> stock = stockRepository.findById(SID);
        if(orderFlower.isPresent() || stock.isPresent()){
            allocate.setOID(orderFlower.get());
            allocate.setSID(stock.get());
            allocate.setAmount(amount);
            allocate.setStockChanged(stockChanged);
        }
        allocateRepository.save(allocate);
    }

    public AllocateRequest findAllAllocate(){
        Map<String, Double> dataFlower = new HashMap<>();
        List<Allocate> allocates = allocateRepository.findAll();

        for (Allocate allocate : allocates) {
            OrderFlower orderFlower = allocate.getOID();
            if (Objects.nonNull(orderFlower)) {
                if (dataFlower.containsKey(orderFlower.getFlower().getFName())) {
                    Double amount = dataFlower.get(orderFlower.getFlower().getFName());
                    amount += allocate.getAmount();
                    dataFlower.put(orderFlower.getFlower().getFName(), amount);
                } else {
                    dataFlower.put(orderFlower.getFlower().getFName(), allocate.getAmount());
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
