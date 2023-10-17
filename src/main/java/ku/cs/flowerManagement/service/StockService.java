package ku.cs.flowerManagement.service;

import ku.cs.flowerManagement.entity.PlantOrder;
import ku.cs.flowerManagement.entity.Stock;
import ku.cs.flowerManagement.model.pRequest;
import ku.cs.flowerManagement.repository.PlantOrderRepository;
import ku.cs.flowerManagement.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {
    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private PlantOrderRepository plantOrderRepository;

    public List<Stock> getAllStocks(){
        return stockRepository.findAll();
    }

    public Stock findStockByPlantOrder(PlantOrder plantOrder){
        for (Stock stock: getAllStocks()) {
            if(stock.getPlantOrder().equals(plantOrder)){
                return stock;
            }

        }
        return null;
    }

    public void setStock(pRequest pRequest){
        System.out.println("pRequest id "+pRequest.getPlant_order_id());

        PlantOrder plantOrder = plantOrderRepository.findById(pRequest.getPlant_order_id()).get();

        Stock stock = new Stock();
        stock.setPlantOrder(plantOrder);
        stock.setQuantity(plantOrder.getQuantity());
        stockRepository.save(stock);
        plantOrder.setStock(stock);
        plantOrderRepository.save(plantOrder);
    }
}
