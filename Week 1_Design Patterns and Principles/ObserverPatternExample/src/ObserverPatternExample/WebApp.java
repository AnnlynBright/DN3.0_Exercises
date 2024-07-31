package ObserverPatternExample;

public class WebApp implements Observer {
    private double stockPrice;

    @Override
    public void update(double stockPrice) {
        this.stockPrice = stockPrice;
        display();
    }

    private void display() {
        System.out.println("Web App - Stock price updated to: $" + stockPrice);
    }
}

