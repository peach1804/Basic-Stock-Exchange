package basepackage;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class Controller {

    @FXML
    TextField tickerField;
    @FXML
    TextField nameField;
    @FXML
    TextField priceField;
    @FXML
    TextField amountField;
    @FXML
    Button loginButton;
    @FXML
    Button buyButton;
    @FXML
    Button sellButton;
    @FXML
    ListView<String> stockListView = new ListView<>();
    @FXML
    ListView<String> portfolioListView = new ListView<>();

    Client client;
    LoginWindow loginWindow;

    public void setClient(Client client) {
        this.client = client;
    }

    public ListView<String> getPortfolioListView() {
        return portfolioListView;
    }

    public void buyButtonClick() throws IOException, ClassNotFoundException {

        int amount = Integer.parseInt(amountField.getText());
        String name = nameField.getText();
        String ticker = tickerField.getText();
        double price = Double.parseDouble(priceField.getText());

        client.buyStock(amount, name, ticker, price);
    }

    public void sellButtonClick() throws IOException, ClassNotFoundException {

        int amount = Integer.parseInt(amountField.getText());
        String name = nameField.getText();
        String ticker = tickerField.getText();
        double price = Double.parseDouble(priceField.getText());

        client.sellStock(amount, name, ticker, price);
    }

    public void loginButtonClick() {

        loginWindow = new LoginWindow(client);
        loginWindow.display();
    }

    public void stockListViewClick() {

        stockListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ObservableValue observableValue, Object o, Object t1) {
                        int index = stockListView.getSelectionModel().getSelectedIndex();
                        findStock(index);
                    }
                });
    }

    public void findStock(int index) {

        Stock selectedStock = client.getStockList().get(index);

        nameField.setText(selectedStock.getName());
        tickerField.setText(selectedStock.getTicker());
        priceField.setText(String.valueOf(selectedStock.getPrice()));
    }

    public void portfolioListViewClick() {

        portfolioListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ObservableValue observableValue, Object o, Object t1) {
                        int index = portfolioListView.getSelectionModel().getSelectedIndex();
                        findInventory(index);
                    }
                }
        );
    }

    public void findInventory(int index) {

        Inventory inventory = client.getPortfolio().get(index);

        nameField.setText(inventory.getStock().getName());
        tickerField.setText(inventory.getStock().getTicker());
        priceField.setText(String.valueOf(inventory.getStock().getPrice()));
        amountField.setText(String.valueOf(inventory.getQuantity()));
    }

}
