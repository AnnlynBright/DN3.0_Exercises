package ObserverPatternExample;

public class MobileApp implements Observer {
    private double stockPrice;

    @Override
    public void update(double stockPrice) {
        this.stockPrice = stockPrice;
        display();
    }

    private void display() {
        System.out.println("Mobile App - Stock price updated to: $" + stockPrice);
    }
}
