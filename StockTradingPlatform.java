import java.util.*;

// Stock class
class Stock {
    String symbol;
    double price;

    Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }
}

// Transaction class
class Transaction {
    String stockSymbol;
    int quantity;
    double price;
    String type; // BUY or SELL

    Transaction(String stockSymbol, int quantity, double price, String type) {
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
        this.price = price;
        this.type = type;
    }
}

// User class
class User {
    String name;
    double balance;
    HashMap<String, Integer> portfolio = new HashMap<>();
    ArrayList<Transaction> transactions = new ArrayList<>();

    User(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }
}

// Main class
public class StockTradingPlatform {

    static Scanner sc = new Scanner(System.in);
    static ArrayList<Stock> marketStocks = new ArrayList<>();

    public static void main(String[] args) {

        // Market Data
        marketStocks.add(new Stock("AAPL", 150.0));
        marketStocks.add(new Stock("GOOG", 2800.0));
        marketStocks.add(new Stock("TSLA", 700.0));

        System.out.print("Enter user name: ");
        String name = sc.nextLine();
        User user = new User(name, 10000.0);

        int choice;
        do {
            System.out.println("\n--- Stock Trading Platform ---");
            System.out.println("1. View Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. View Transactions");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    displayMarket();
                    break;
                case 2:
                    buyStock(user);
                    break;
                case 3:
                    sellStock(user);
                    break;
                case 4:
                    viewPortfolio(user);
                    break;
                case 5:
                    viewTransactions(user);
                    break;
                case 6:
                    System.out.println("Thank you for trading!");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 6);
    }

    static void displayMarket() {
        System.out.println("\n--- Market Data ---");
        for (Stock s : marketStocks) {
            System.out.println(s.symbol + " : ₹" + s.price);
        }
    }

    static void buyStock(User user) {
        System.out.print("Enter stock symbol: ");
        String symbol = sc.next();
        System.out.print("Enter quantity: ");
        int qty = sc.nextInt();

        for (Stock s : marketStocks) {
            if (s.symbol.equalsIgnoreCase(symbol)) {
                double cost = s.price * qty;
                if (user.balance >= cost) {
                    user.balance -= cost;
                    user.portfolio.put(symbol,
                            user.portfolio.getOrDefault(symbol, 0) + qty);
                    user.transactions.add(new Transaction(symbol, qty, s.price, "BUY"));
                    System.out.println("Stock bought successfully!");
                } else {
                    System.out.println("Insufficient balance!");
                }
                return;
            }
        }
        System.out.println("Stock not found!");
    }

    static void sellStock(User user) {
        System.out.print("Enter stock symbol: ");
        String symbol = sc.next();
        System.out.print("Enter quantity: ");
        int qty = sc.nextInt();

        if (user.portfolio.containsKey(symbol) && user.portfolio.get(symbol) >= qty) {
            for (Stock s : marketStocks) {
                if (s.symbol.equalsIgnoreCase(symbol)) {
                    user.balance += s.price * qty;
                    user.portfolio.put(symbol, user.portfolio.get(symbol) - qty);
                    user.transactions.add(new Transaction(symbol, qty, s.price, "SELL"));
                    System.out.println("Stock sold successfully!");
                    return;
                }
            }
        } else {
            System.out.println("Not enough stock to sell!");
        }
    }

    static void viewPortfolio(User user) {
        System.out.println("\n--- Portfolio ---");
        System.out.println("Balance: ₹" + user.balance);
        for (String stock : user.portfolio.keySet()) {
            System.out.println(stock + " : " + user.portfolio.get(stock) + " shares");
        }
    }

    static void viewTransactions(User user) {
        System.out.println("\n--- Transaction History ---");
        for (Transaction t : user.transactions) {
            System.out.println(t.type + " | " + t.stockSymbol +
                    " | Qty: " + t.quantity + " | Price: ₹" + t.price);
        }
    }
}
