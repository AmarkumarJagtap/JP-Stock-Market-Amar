/**
 * 
 */
package com.app.stockmarket.service;

import java.util.List;
import java.util.Locale;

import com.app.stockmarket.service.impl.StockExchangeServiceImpl;
import com.app.stockmarket.domain.Stock;
import com.app.stockmarket.exception.InvalidStockException;

/**
 * Interface which defines Stock Exchange API
 *
 */
public interface StockExchangeService {


	public void setCountry(String country);

	public void setName(String name);
	/**
	 * Create Stock in Market
	 * 
	 * @param stock Details of the new stock
	 * @return
	 * @throws InvalidStockException 
	 */
	public boolean createStockInMarket(Stock stock) throws InvalidStockException;
	
	
	/**
	 * Buy a stock denoted by a stock symbol
	 * 
	 * @param stockSymbol Symbol 
	 * @param quantity
	 * @param price
	 * @return
	 */
	public boolean buyStock(String stockSymbol, int quantity, double price) throws InvalidStockException;

	/**
	 * Buy a stock denoted by a stock symbol
	 * 
	 * @param stockSymbol
	 * @param quantity
	 * @param price
	 * @return
	 */
	public boolean sellStock(String stockSymbol, int quantity, double price) throws InvalidStockException;
	
	/**
	 * Calculate Dividend yield given price as input 
	 *  
	 * @param stockSymbol Symbol of the stock
	 * @param price Price of the stock
	 * @return Dividend yield for a given stock
	 * @throws InvalidStockException When no stock is associated with the stock symbol
	 */
	public double calculateDividendYield(String stockSymbol, double price) throws InvalidStockException;
	
	/**
	 * Calculate P/E ratio for a given stock, given price as input
	 * 
	 * @param stockSymbol Symbol of the stock
	 * @param price Price of the stock
	 * @return P/E ratio for a given stock,
	 * @throws InvalidStockException When no stock is associated with the stock symbol
	 */
	public double priceOverDividendRatio(String stockSymbol, double price) throws InvalidStockException;
	
	/**
	 * Calculate Volume Weighted Stock Price based on trades in past given input minutes
	 * 
	 * @param stockSymbol Symbol of the stock
	 * @return Volume Weighted Stock price of a given stock
	 * 
	 * @throws InvalidStockException  When no stock is associated with the stock symbol
	 */
	public double calculateVolumeWeightedStockPrice(String stockSymbol) throws InvalidStockException;

	/**
	 * Set all stock symbols
	 *  
	 * @return stock symbols of all stock in the market
	 */
	public List<String> listAllStockSymbols();
	
	/**
	 * List of all Stocks in Market
	 * 
	 * @return List of Stocks
	 */
	public List<Stock> listAllStocksInMarket();
	
	/**
	 * Calculate the GBCE All Share Index using the geometric mean of prices for all stocks
	 */
	public double calculateAllShareIndex() throws InvalidStockException;

	public int getConfiguredMinuntes();

	public Locale getClientLocale();
	public StockExchangeServiceImpl registerTradeService(TradeService tradeService);
	public StockExchangeServiceImpl registerStockDataService(StockDataService stockDataService);
}
