package basepackage;

import java.io.Serializable;

public class Inventory implements Comparable<Inventory>, Serializable {

    private Stock stock;
    private int quantity = 0;

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity += quantity;
    }

    @Override
    public int compareTo(Inventory inventory) {
        return this.stock.compareTo(inventory.stock);
    }

}
