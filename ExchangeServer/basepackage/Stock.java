package basepackage;

import java.io.Serializable;

public class Stock implements Comparable<Stock>, Serializable {

    private String name;
    private String ticker;
    private double price;

    @Override
    public int compareTo(Stock stock){
        return this.ticker.compareTo(stock.ticker);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}