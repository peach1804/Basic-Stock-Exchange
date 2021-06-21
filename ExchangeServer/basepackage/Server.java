package basepackage;

import DSA.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Server implements Runnable {

    private ServerSocket server;
    private Socket connection;
    private static Tree<Stock> stockTree = new Tree<>();

    @Override
    public void run() {

        try {
            readStocks();
            readUsers();
            startServer();

        } catch (IOException ioe) {
            System.out.println(ioe);
            saveStocks();
            CSVHandle.saveUsers();
        }
    }

    public void startServer() throws IOException {
        server = new ServerSocket(1991);
        connectToClient();
    }

    public void connectToClient() throws IOException {

        while (true) {

            System.out.println("Waiting for someone to connect...");
            connection = server.accept();
            System.out.println("Now connected to " + connection.getInetAddress().getHostName());

            Trader trader = new Trader(connection);
            new Thread(trader).start();
        }
    }

    public void readStocks() {
        Path file = Paths.get("stocks.csv");
        stockTree = CSVHandle.readStocks(file);
        Main.display();
    }

    public void readUsers() {
        Path file = Paths.get("users.csv");
        CSVHandle.readUsers(file);
    }

    public void saveStocks() {
        Tree<Stock> stocks = stockTree;
        CSVHandle.writeStocks(stocks);
        Main.display();
    }

    public static Tree<Stock> getStockTree() {
        return stockTree;
    }
}