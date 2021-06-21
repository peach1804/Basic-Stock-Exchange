package basepackage;

import DSA.Node;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private Server server;
    private Thread serverThread;
    private static int count;
    private static Stock currentStock;
    private Stage window;
    private static ListView<String> stockListView;

    @Override
    public void start(Stage stage) {

        window = stage;
        window.setTitle("Stock Exchange Server");

        Button addButton = new Button("Add Stock");
        addButton.setOnAction(e -> addButtonClicked());

        Button deleteButton = new Button("Remove Stock");
        deleteButton.setOnAction(e -> deleteButtonClicked());

        stockListView = new ListView<>();
        stockListView.setMaxHeight(100);

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10, 10, 10, 10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(addButton, deleteButton);

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.getChildren().addAll(stockListView, hBox);

        server = new Server();
        serverThread = new Thread(server);
        serverThread.start();

        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.setOnCloseRequest(e -> {
            closeProgram();
            window.close();
            System.exit(0);
        });
        window.show();
    }

    public void addButtonClicked() {
        AddWindow addWindow = new AddWindow();
        addWindow.display();
    }

    public void deleteButtonClicked() {
        count = 0;
        int index = stockListView.getSelectionModel().getSelectedIndex();
        findSongOfIndex(Server.getStockTree().getRoot(), index);
        Server.getStockTree().delete(currentStock);
        display();
    }

    public void findSongOfIndex(Node<Stock> node, int index) {

        if (node.getLeft() != null) {
            findSongOfIndex(node.getLeft(), index);
        }

        if (count == index) {
            currentStock = node.getData();
            return;
        }
        count++;

        if (node.getRight() != null) {
            findSongOfIndex(node.getRight(), index);
        }
    }

    public void closeProgram() {
        server.saveStocks();
        CSVHandle.saveUsers();
    }

    public static void display() {

        stockListView.getItems().remove(0, stockListView.getItems().size());

        if (Server.getStockTree().getRoot() != null) {
            printToListView(Server.getStockTree().getRoot());
        }
    }

    public static void printToListView(Node<Stock> node) {

        if (node.getLeft() != null) {
            printToListView(node.getLeft());
        }

        Stock stock = node.getData();
        stockListView.getItems().add(stock.getTicker() + " - " + stock.getName() + " - " + stock.getPrice());

        if (node.getRight() != null) {
            printToListView(node.getRight());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}