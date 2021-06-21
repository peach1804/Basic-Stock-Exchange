package basepackage;

import DSA.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Client implements Runnable {

    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    private Controller controller;
    private ArrayList<Inventory> portfolio = new ArrayList<>();
    private ArrayList<Stock> stockList = new ArrayList<>();

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        connect();
    }

    public void connect() {

        boolean success = false;

        do {
            try {
                System.out.println("Trying to connect");
                socket = new Socket("localhost", 1991);
                System.out.println("Connected to server");
                setupStreams(socket);
                success = true;

            } catch (IOException e) {
                System.out.println(e);
            }
        } while (!success);
    }

    private void setupStreams(Socket socket) throws IOException {
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());
        System.out.println("Your streams are now set up");
    }

    public void userLogin(String id, String password) throws IOException, ClassNotFoundException {

        output.writeUTF("LOGIN");
        output.flush();

        output.writeUTF(id);
        output.flush();

        output.writeUTF(password);
        output.flush();

        boolean success = input.readBoolean();

        if (success) {
            System.out.println("Login successful");
            controller.loginButton.setDisable(true);
            receiveStockData();
            receivePortfolio();

        } else {
            System.out.println("Login failed");
        }

    }

    public void createAccount(String id, String password) throws IOException {

        output.writeUTF("CREATE");
        output.flush();

        output.writeUTF(id);
        output.flush();

        output.writeUTF(password);
        output.flush();
    }

    public void buyStock(int amount, String name, String ticker, double price) throws IOException, ClassNotFoundException {

        output.writeUTF("BUY");
        output.flush();

        Stock stock = new Stock();
        stock.setName(name);
        stock.setTicker(ticker);
        stock.setPrice(price);

        output.writeObject(stock);
        output.flush();
        output.writeInt(amount);
        output.flush();

        Inventory temp = new Inventory();
        temp.setStock(stock);

        BinarySearch<Inventory> bs = new BinarySearch<>();
        Inventory inventory = bs.binarySearch(portfolio, temp);

        if (inventory != null) {

            inventory.setQuantity(amount);

        } else {

            portfolio.add(temp);
            temp.setQuantity(amount);
        }

        receivePortfolio();
    }

    public void sellStock(int amount, String name, String ticker, double price) throws IOException, ClassNotFoundException {

        output.writeUTF("SELL");
        output.flush();

        Stock stock = new Stock();
        stock.setName(name);
        stock.setTicker(ticker);
        stock.setPrice(price);

        output.writeObject(stock);
        output.flush();
        output.writeInt(amount);
        output.flush();

        Inventory temp = new Inventory();
        temp.setStock(stock);

        BinarySearch<Inventory> bs = new BinarySearch<>();
        Inventory inventory = bs.binarySearch(portfolio, temp);

        if (inventory != null && inventory.getQuantity() >= amount) {

            inventory.setQuantity(amount * (-1));
            receivePortfolio();

        } else {
            System.out.println("You do not own enough of that stock");
        }
    }

    public void receiveStockData() throws IOException, ClassNotFoundException { ////////// called when logged in

        int stockCount = input.readInt();

        for (int i = 0; i < stockCount; i++) {

            Stock stock = (Stock) input.readObject();
            stockList.add(stock);
            displayStock(stock);
        }
    }

    public void receivePortfolio() throws IOException, ClassNotFoundException {

        portfolio.clear();
        controller.getPortfolioListView().getItems().remove(0, controller.portfolioListView.getItems().size());

        int inventoryCount = input.readInt();

        for (int i = 0; i < inventoryCount; i++) {

            Inventory inventory = new Inventory();
            Stock stock = (Stock) input.readObject();
            int quantity = input.readInt();

            inventory.setStock(stock);
            inventory.setQuantity(quantity);

            portfolio.add(inventory);
            displayInventory(inventory);
        }
    }

    public void displayStock(Stock stock) {
        controller.stockListView.getItems().add(stock.getTicker() + " - " + stock.getName() + " - " + stock.getPrice());
    }

    public void displayInventory(Inventory inventory) {
        controller.portfolioListView.getItems().add(inventory.getStock().getTicker() + " - " + inventory.getStock().getName() + " - " + inventory.getQuantity());
    }

    public ArrayList<Stock> getStockList() {
        return stockList;
    }

    public ArrayList<Inventory> getPortfolio() {
        return portfolio;
    }

}
