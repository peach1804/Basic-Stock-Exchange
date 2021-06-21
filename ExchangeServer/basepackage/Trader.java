package basepackage;

import DSA.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Trader implements Runnable {

    private UserData.User user;
    private ArrayList<Inventory> portfolio = new ArrayList<>();
    private MergeSort<Inventory> ms = new MergeSort<>();

    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    public Trader(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());

            clientCommands();
            savePortfolio();
            output.close();
            input.close();

        } catch (IOException | NoSuchAlgorithmException | ClassNotFoundException e) {

            savePortfolio();
            System.out.println(e);
        }
    }

    private enum Command {

        BUY,
        SELL,
        LOGIN,
        CREATE
    }

    public void clientCommands() throws IOException, NoSuchAlgorithmException, ClassNotFoundException {

        while (true) {
            String message = input.readUTF();
            processCommand(message);
        }
    }

    public void processCommand(String message) throws IOException, NoSuchAlgorithmException, ClassNotFoundException {

        Command command = Command.valueOf(message);

        switch (command) {

            case CREATE:
                createAccount();
                break;
            case LOGIN:
                clientLogin();
                break;
            case BUY:
                sellToClient();
                break;
            case SELL:
                buyFromClient();
                break;
            default:
                System.out.println(command + " not recognised.");
                break;
        }
    }

    public void createAccount() throws IOException, NoSuchAlgorithmException {

        String ID = input.readUTF();
        String password = input.readUTF();

        UserData ud = new UserData();

        boolean userExists = ud.checkUser(ID);

        if (!userExists) {
            ud.createUser(ID, password);
        } else {
            //////////////////// send result to client
        }
    }

    public void clientLogin() throws IOException, NoSuchAlgorithmException {

        String ID = input.readUTF();
        String password = input.readUTF();

        UserData ud = new UserData();

        this.user = ud.checkLogin(ID, password);

        if (user != null) {

            Path file = Paths.get(user.getUsername() + ".csv");
            portfolio = CSVHandle.readPortfolio(file);

            output.writeBoolean(true);
            output.flush();
            sendStockData();
            sendPortfolio();

        } else {
            output.writeBoolean(false);
            output.flush();
        }
    }

    public void sellToClient() throws IOException, ClassNotFoundException {

        Stock stock = (Stock) input.readObject();
        int amount = input.readInt();

        Inventory temp = new Inventory();
        temp.setStock(stock);

        BinarySearch<Inventory> bs = new BinarySearch<>();
        Inventory inventory = bs.binarySearch(portfolio, temp);

        if (inventory != null) {
            inventory.setQuantity(amount);

        } else {

            temp.setQuantity(amount);
            portfolio.add(temp);
            ms.mergeSort(portfolio);
        }

        sendPortfolio();
    }

    public void buyFromClient() throws IOException, ClassNotFoundException {

        Stock stock = (Stock) input.readObject();
        int amount = input.readInt();

        Inventory temp = new Inventory();
        temp.setStock(stock);

        BinarySearch<Inventory> bs = new BinarySearch<>();
        Inventory inventory = bs.binarySearch(portfolio, temp);

        if (inventory != null && inventory.getQuantity() > 0) {

            inventory.setQuantity(amount * (-1));

            if (inventory.getQuantity() == 0) {
                portfolio.remove(inventory);
            }

            sendPortfolio();

        } else {
            System.out.println("Client does not own that stock.");
        }
    }

    public void sendStockData() throws IOException {

        Tree<Stock> tree = Server.getStockTree();
        tree.countNodes();

        output.writeInt(tree.getNodeCount());
        output.flush();

        if (Server.getStockTree().getRoot() != null) {
            sendStocks(Server.getStockTree().getRoot());
        }
    }

    // Traverses the tree in ascending order and sends each stock to the client.
    public void sendStocks(Node<Stock> node) throws IOException {

        if (node.getLeft() != null) {
            sendStocks(node.getLeft());
        }

        output.writeObject(node.getData());
        output.flush();

        if (node.getRight() != null) {
            sendStocks(node.getRight());
        }
    }

    public void sendPortfolio() throws IOException {

        MergeSort<Inventory> ms = new MergeSort<>();
        ms.mergeSort(portfolio);

        output.writeInt(portfolio.size());
        output.flush();

        for (Inventory inventory : portfolio) {

            output.writeObject(inventory.getStock());
            output.flush();
            output.writeInt(inventory.getQuantity());
            output.flush();
        }
    }

    private void savePortfolio() {
        CSVHandle.writePortfolio(portfolio, user);
    }
}