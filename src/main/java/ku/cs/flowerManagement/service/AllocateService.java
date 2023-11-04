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


    public void createAllocate(int OID ,  Integer SID , double amount, int stockChanged){
        Allocate allocate = new Allocate();
        Optional<OrderItem> orderFlower = orderRepository.findById(OID);
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
        Map<String, AllocateModel> dataFlower = new HashMap<>();
        List<Allocate> allocates = allocateRepository.findAll();

        for (Allocate allocate : allocates) {
            OrderItem orderFlower = allocate.getOID();
            if (Objects.nonNull(orderFlower)) {
                AllocateModel allocateModel = new AllocateModel();
                if (dataFlower.containsKey(orderFlower.getFlower().getFName())) {
                    Double amount = dataFlower.get(orderFlower.getFlower().getFName()).getTotal();
                    int allocateData = dataFlower.get(orderFlower.getFlower().getFName()).getStockFlower();
                    amount += allocate.getAmount();
                    allocateData += allocate.getStockChanged();
                    allocateModel.setStockFlower(allocateData);
                    allocateModel.setTotal(amount);
                    allocateModel.setFName(orderFlower.getFlower().getFName());
                } else {
                    allocateModel.setFName(orderFlower.getFlower().getFName());
                    allocateModel.setTotal(allocate.getAmount());
                    allocateModel.setStockFlower(allocate.getStockChanged());
                }
                dataFlower.put(orderFlower.getFlower().getFName(), allocateModel);
            }
        }

        List<AllocateModel> allocateModels = new ArrayList<>();
        for (Map.Entry<String, AllocateModel> entry : dataFlower.entrySet()) {
            allocateModels.add(entry.getValue());
        }


        AllocateRequest allocateRequest = new AllocateRequest();
        allocateRequest.setAllocateModels(allocateModels);

        return allocateRequest;
    }

}
