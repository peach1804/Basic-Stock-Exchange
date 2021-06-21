package basepackage;

import DSA.*;
import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class CSVHandle {

    public static Tree<Stock> readStocks(Path file) {

        Tree<Stock> stocks = new Tree<>();
        CSVReader reader;

        try {
            reader = new CSVReader(new FileReader(String.valueOf(file)));
            String[] nextLine;

            while ((nextLine = reader.readNext()) != null) {

                Stock nextStock = new Stock();
                nextStock.setTicker(nextLine[0]);
                nextStock.setName(nextLine[1]);
                nextStock.setPrice(Double.parseDouble(nextLine[2]));

                stocks.insert(nextStock);
            }

            reader.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        return stocks;
    }

    public static void writeStocks(Tree<Stock> stocks) {

        File file = new File("stocks.csv");
        CSVWriter writer;

        try {
            writer = new CSVWriter(new FileWriter(file));
            ascending(stocks.getRoot(), writer);
            writer.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void ascending(Node<Stock> node, CSVWriter writer) {

        if (node.getLeft() != null) {

            ascending(node.getLeft(), writer);
        }

        Stock stock = node.getData();
        String[] nextStock = {stock.getTicker(), stock.getName(), String.valueOf(stock.getPrice())};
        writer.writeNext(nextStock);

        if (node.getRight() != null) {

            ascending(node.getRight(), writer);
        }
    }

    public static ArrayList<Inventory> readPortfolio(Path file) {

        ArrayList<Inventory> portfolio = new ArrayList<>();
        CSVReader reader;

        try {
            reader = new CSVReader(new FileReader(String.valueOf(file)));
            String[] nextLine;

            while ((nextLine = reader.readNext()) != null) {

                Inventory nextInventory = new Inventory();

                Stock stock = new Stock();
                stock.setTicker(nextLine[0]);
                stock.setName(nextLine[1]);
                stock.setPrice(Double.parseDouble(nextLine[2]));

                nextInventory.setStock(stock);
                nextInventory.setQuantity(Integer.parseInt(nextLine[3]));

                portfolio.add(nextInventory);
            }

            reader.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        return portfolio;
    }

    public static void writePortfolio(ArrayList<Inventory> portfolio, UserData.User user) {

        CSVWriter writer;
        File file = new File(user.getUsername() + ".csv");

        try {

            writer = new CSVWriter(new FileWriter(file));

            for (Inventory inventory : portfolio) {

                Stock stock = inventory.getStock();
                String[] nextInventory = {stock.getTicker(), stock.getName(), String.valueOf(stock.getPrice()), String.valueOf(inventory.getQuantity())};
                writer.writeNext(nextInventory);
            }

            writer.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void readUsers(Path file) {

        CSVReader reader;

        try {
            reader = new CSVReader(new FileReader(String.valueOf(file)));
            String[] nextLine;

            while ((nextLine = reader.readNext()) != null) {

                String username = nextLine[0];
                String password = nextLine[1];
                String salt = nextLine[2];

                UserData ud = new UserData();
                ud.addUser(username, password, salt);
            }

            reader.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void saveUsers() {

        CSVWriter writer;
        File file = new File("users.csv");

        try {

            writer = new CSVWriter(new FileWriter(file));

            for (UserData.User user : UserData.getUsers()) {

                String[] nextUser = {user.getUsername(), user.getPassword(), user.getSalt()};
                writer.writeNext(nextUser);
            }

            writer.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
