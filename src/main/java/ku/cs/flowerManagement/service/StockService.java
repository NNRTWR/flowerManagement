package ku.cs.flowerManagement.service;

import ku.cs.flowerManagement.common.StockStatus;
import ku.cs.flowerManagement.entity.Flower;
import ku.cs.flowerManagement.entity.GardenerOrder;
import ku.cs.flowerManagement.entity.Stock;
import ku.cs.flowerManagement.model.FlowerRequest;
import ku.cs.flowerManagement.model.StockRequest;
import ku.cs.flowerManagement.repository.FlowerRepository;
import ku.cs.flowerManagement.repository.StockRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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
    AllocateService allocateService;

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

    public Page<Stock> getAllStockPage(int page, int size) {   //สำหรับ pagination
        Pageable pageable = PageRequest.of(page, size);
        return stockRepository.findAll(pageable);
    }

    public FlowerRequest getFlowerById(int id) {
        return  modelMapper.map(flowerRepository.findById(id).orElse(null), FlowerRequest.class);
    }

    public StockRequest addFlower(StockRequest stockRequest) {
        Stock stock = modelMapper.map(stockRequest, Stock.class);
        return modelMapper.map(stockRepository.save(stock), StockRequest.class);
    }

    public List<Stock> getStockByFID(int FID){
        List<Stock> stocks = stockRepository.findByFlower_FID(FID);
        return stocks;
    }

    public int calculateStock(int FID){
        int total = 0;
        List<Stock> stocks = stockRepository.findAll();
        for(Stock stock : stocks){
            if(stock.getFlower().getFID() == FID){
                total += stock.getTotal();
            }
        }
        return total;
    }
    public void updateStock(int total , int FID , int OID){
//        Optional<Stock> stockData = stockRepository.findById(SID);
//        if(stockData.isPresent()){
//            stockData.get().setTotal((int) (stockData.get().getTotal() - total));
//            stockRepository.save(stockData.get());
//        }
        Optional<Flower> flowerData = flowerRepository.findById(FID);
        List<Stock> stocksData =  stockRepository.findByFlower_FID(FID);
        if(!stocksData.isEmpty() && flowerData.isPresent()){
            for(Stock stock : stocksData){
                if(total <= stock.getTotal()){
                    int stockChange = stock.getTotal() - total;
                    stock.setTotal((int) (stockChange));
                    stockRepository.save(stock);
                    allocateService.createAllocate(OID , stock.getSID() , total * flowerData.get().getPrice(), total);
                    break;
                }else{
                    total = total - stock.getTotal();
                    allocateService.createAllocate(OID , stock.getSID() , stock.getTotal() * flowerData.get().getPrice() , stock.getTotal());
                    stock.setTotal(0);
                    stockRepository.save(stock);
                }
            }
        }
    }

    public void createStock(StockRequest stockRequest) {
        Stock stock = modelMapper.map(stockRequest, Stock.class);
        Flower flower = flowerRepository.findById(stockRequest.getFID()).orElse(null);
        if(flower == null) return;
        stock.setFlower(flower);
        stock.setQuantity(stockRequest.getQuantity());
        stock.setTime(LocalDateTime.now());
        stock.setTotal(stockRequest.getQuantity());
        stockRepository.save(stock);
    }


//    public Stock getStockById(int FID){
//            return stockRepository.findByFlowerFID(FID);
//    }
}

