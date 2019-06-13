package com.app.stockmarket;


import com.app.stockmarket.domain.CommonStock;
import com.app.stockmarket.domain.FixedDividendStock;
import com.app.stockmarket.domain.Stock;
import com.app.stockmarket.exception.InvalidStockException;
import com.app.stockmarket.service.StockDataService;
import com.app.stockmarket.service.TradeService;
import com.app.stockmarket.service.StockExchangeService;
import com.app.stockmarket.types.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Rest service to call stock market APIs
 */
@RestController
@RequestMapping("/stockMarket")
public class StockMarketController {


    @Autowired
    StockDataService stockDS;

    @Autowired
    TradeService tradeService;

    @Autowired
    StockExchangeService stockExchange;

    /**
     *
     * @param stockSymbol
     * @param price
     * @return
     * @throws InvalidStockException
     */
    @GetMapping("/calculateDividendYield/{stockSymbol}/{price}")
    public ResponseEntity<Double> calculateDividendYield(@PathVariable String stockSymbol, @PathVariable double price) throws InvalidStockException
    {
        return new ResponseEntity<>(stockExchange.calculateDividendYield(stockSymbol, price), HttpStatus.OK);

    }

    /**
     *
     * @param stockSymbol
     * @param price
     * @return
     * @throws InvalidStockException
     */
    @GetMapping("/calculatePERatio/{stockSymbol}/{price}")
    public ResponseEntity<Double>  calculatePERatio(@PathVariable String stockSymbol, @PathVariable double price) throws InvalidStockException
    {
        return new ResponseEntity<>(stockExchange.priceOverDividendRatio(stockSymbol, price), HttpStatus.OK);

    }

    /**
     *
     * @return
     */
    @GetMapping("/getAllStocks")
    public ResponseEntity<List<Stock>> getAllStocks(){
        return new ResponseEntity<> (stockExchange.listAllStocksInMarket(), HttpStatus.OK);
    }

    /**
     *
     * @return
     */
    @GetMapping("/getAllStockSymbols")
    public ResponseEntity<List<String>> getAllStockSymbols(){
        return new ResponseEntity<> (stockExchange.listAllStockSymbols(), HttpStatus.OK);
    }

    /**
     *
     * @param stockSymbol
     * @param quantity
     * @param price
     * @return
     * @throws InvalidStockException
     */
    @PostMapping(value = "/sell")
    public ResponseEntity<String> sell(@Valid String stockSymbol, @Valid int quantity, @Valid double price) throws InvalidStockException{
        return new ResponseEntity<> (stockExchange.sellStock(stockSymbol,quantity,price) ? "Stock sold successfully": "Something went wrong, please try again",  HttpStatus.OK);
    }

    /**
     *
     * @param stockSymbol
     * @param quantity
     * @param price
     * @return
     * @throws InvalidStockException
     */
    @PostMapping(value = "/buy")
    public ResponseEntity<String>  buy(@Valid String stockSymbol, @Valid int quantity, @Valid double price) throws InvalidStockException{
        return new ResponseEntity<> (stockExchange.buyStock(stockSymbol,quantity,price) ? "Stock bought successfully" : "Something went wrong, please try again", HttpStatus.OK);
    }

    /**
     *
     * @param stockSymbol
     * @param minutes
     * @return
     * @throws InvalidStockException
     */
    @GetMapping("/volWeightedStockPrice/{stockSymbol}/{minutes}")
    public ResponseEntity<Double>  calculateVolumeWeightedStockPrice(@PathVariable String stockSymbol, @PathVariable int minutes) throws InvalidStockException
    {
        return new ResponseEntity<> (stockExchange.calculateVolumeWeightedStockPrice(stockSymbol, minutes), HttpStatus.OK);
    }


    /**
     *
     * @return
     * @throws InvalidStockException
     */
    @GetMapping("/calculateAllShareIndex")
    public ResponseEntity<Double> calculateAllShareIndex() throws InvalidStockException{
        return new ResponseEntity<> (stockExchange.calculateAllShareIndex(), HttpStatus.OK);
    }

    /**
     *
     * @return
     * @throws InvalidStockException
     */
    @GetMapping("/createStocksInMarket")
    public ResponseEntity<List<Stock>> createStocksInMarket() throws InvalidStockException{

        tradeService.setStockDataService(stockDS);
        stockExchange.setName("GBCE");
        stockExchange.setCountry("UK");
        stockExchange.registerStockDataService(stockDS);
        stockExchange.registerTradeService(tradeService);

        Stock stock = new CommonStock();
        stock.setSymbol("TEA");
        stock.setLastDividend(0);
        stock.setParValue(100);
        stock.setCurrency(Currency.USD);
        stockExchange.createStockInMarket(stock);

        stock = new CommonStock();
        stock.setSymbol("POP");
        stock.setParValue(100);
        stock.setLastDividend(8);
        stock.setCurrency(Currency.USD);
        stockExchange.createStockInMarket(stock);

        stock = new CommonStock();
        stock.setSymbol("ALE");
        stock.setLastDividend(23);
        stock.setParValue(60);
        stock.setCurrency(Currency.USD);
        stockExchange.createStockInMarket(stock);

        stock = new CommonStock();
        stock.setSymbol("JOE");
        stock.setLastDividend(13);
        stock.setParValue(250);
        stock.setLastDividend(23);
        stock.setCurrency(Currency.USD);
        stockExchange.createStockInMarket(stock);

        FixedDividendStock stock1 = new FixedDividendStock();
        stock1.setSymbol("GIN");
        stock1.setParValue(100);
        stock1.setCurrency(Currency.USD);
        stock1.setLastDividend(8);
        stock1.setFixedDividendPercentage(2);
        stockExchange.createStockInMarket(stock1);
       return new ResponseEntity<>(stockExchange.listAllStocksInMarket(), HttpStatus.OK);
    }

}
