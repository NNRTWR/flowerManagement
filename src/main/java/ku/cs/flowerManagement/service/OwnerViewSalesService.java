package ku.cs.flowerManagement.service;

import ku.cs.flowerManagement.common.OrderStatus;
import ku.cs.flowerManagement.entity.Flower;
import ku.cs.flowerManagement.entity.GardenerOrder;
import ku.cs.flowerManagement.model.OwnerViewSalesResponse;
import ku.cs.flowerManagement.repository.FlowerRepository;
import ku.cs.flowerManagement.repository.OrderRepository;
import ku.cs.flowerManagement.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OwnerViewSalesService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private FlowerRepository flowerRepository;

    public List<OwnerViewSalesResponse> getAllSales(){
        List<Flower> flowerList = flowerRepository.findAll();
        List<OwnerViewSalesResponse> responses = new ArrayList<>();
        flowerList.forEach(flower -> {
            OwnerViewSalesResponse response = new OwnerViewSalesResponse();
            response.setFlowerName(flower.getFName());
            response.setHarvestAmount(stockRepository.sumQuantityFromStockByFlower(flower.getFID())  == null ? 0 : stockRepository.sumQuantityFromStockByFlower(flower.getFID()));
            response.setDeadAmount(orderRepository.sumFlowerStatusFromOrder(flower.getFID(), OrderStatus.FAIL) == null ? 0 : orderRepository.sumFlowerStatusFromOrder(flower.getFID(), OrderStatus.FAIL));
            response.setSalesAmount(orderRepository.sumFlowerStatusFromOrder(flower.getFID(),OrderStatus.COMPLETED) == null ? 0 : orderRepository.sumFlowerStatusFromOrder(flower.getFID(),OrderStatus.COMPLETED));
            response.setBalanceAmount(orderRepository.sumPriceStatusFromOrder(flower.getFID(),OrderStatus.COMPLETED) == null ? 0 : orderRepository.sumPriceStatusFromOrder(flower.getFID(),OrderStatus.COMPLETED));
            responses.add(response);
        });
        return responses;
    }


}
