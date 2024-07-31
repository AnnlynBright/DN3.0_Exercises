package ObserverPatternExample;

public class ObserverPatternTest {

    public static void main(String[] args) {
        // Create a StockMarket instance
        StockMarket stockMarket = new StockMarket();

        // Create observer instances
        Observer mobileApp = new MobileApp();
        Observer webApp = new WebApp();

        // Register observers
        stockMarket.registerObserver(mobileApp);
        stockMarket.registerObserver(webApp);

        // Change stock price and notify observers
        stockMarket.setStockPrice(100.00);
        stockMarket.setStockPrice(105.50);

        // Deregister an observer and update stock price
        stockMarket.deregisterObserver(mobileApp);
        stockMarket.setStockPrice(110.00);
    }
}

