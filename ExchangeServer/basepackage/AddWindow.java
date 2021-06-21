package basepackage;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddWindow {

    private Stage addWindow;
    private TextField nameField;
    private TextField tickerField;
    private TextField priceField;

    private Stock newStock = new Stock();

    public void display() {

        addWindow = new Stage();

        Label nameLabel = new Label();
        nameLabel.setText("Company name:");

        Label tickerLabel = new Label();
        tickerLabel.setText("Ticker:");

        Label priceLabel = new Label();
        priceLabel.setText("Price:");

        nameField = new TextField();
        tickerField = new TextField();
        priceField = new TextField();

        Button addButton = new Button();
        addButton.setText("Add");
        addButton.setOnAction(e -> addStock(newStock));

        VBox layout = new VBox();
        layout.getChildren().addAll(nameLabel, nameField, tickerLabel, tickerField,
                priceLabel, priceField, addButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        addWindow.setScene(scene);
        addWindow.show();
    }

    public void addStock(Stock stock) {

        stock.setName(nameField.getText());
        stock.setTicker(tickerField.getText());
        stock.setPrice(Double.parseDouble(priceField.getText()));

        Server.getStockTree().insert(newStock);

        Main.display();
        addWindow.close();
    }

}