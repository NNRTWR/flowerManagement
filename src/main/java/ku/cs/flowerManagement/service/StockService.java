package ku.cs.flowerManagement.service;

import ku.cs.flowerManagement.entity.Stock;
import ku.cs.flowerManagement.model.FlowerRequest;
import ku.cs.flowerManagement.model.StockRequest;
import ku.cs.flowerManagement.repository.FlowerRepository;
import ku.cs.flowerManagement.repository.StockRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {
    @Autowired
    private FlowerRepository flowerRepository;
    @Autowired
    StockRepository stockRepository;

    @Autowired
    FlowerService flowerService;

    @Autowired
    private ModelMapper modelMapper;
    public List<Stock> getStockList() {
        List<Stock> stocks = stockRepository.findAll();

        List<Stock> stockList = new ArrayList<>();

        for (Stock s:stocks) {
            Stock stock = modelMapper.map(s, Stock.class);
            stockList.add(stock);
        }
        System.out.println(List.of(stockList));
        return stockList;
    }

    public FlowerRequest getFlowerById(int id) {
        return  modelMapper.map(flowerRepository.findById(id).orElse(null), FlowerRequest.class);
    }

    public StockRequest addFlower(StockRequest stockRequest) {
        Stock stock = modelMapper.map(stockRequest, Stock.class);
        return modelMapper.map(stockRepository.save(stock), StockRequest.class);
    }

    public Stock getStockByFID(int FID){
        List<Stock> stocks = stockRepository.findAll();
    for(Stock stock : stocks){
        if(stock.getFlower().getFID() == FID){
            return stock;
        }
    }
    return null;
    }

    public void updateStock(int SID , int total){
        Optional<Stock> stockData = stockRepository.findById(SID);
        if(stockData.isPresent()){
            stockData.get().setTotal((int) (stockData.get().getTotal() - total));
            stockRepository.save(stockData.get());
        }
    }

//    public Stock getStockById(int FID){
//            return stockRepository.findByFlowerFID(FID);
//    }
}
