import java.util.Scanner;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BudgetTracker {
    private double balance;
    private ArrayList<String> transactions;
    
    // ANSI Color codes
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String CYAN = "\u001B[36m";
    private static final String BOLD = "\u001B[1m";
    private static final String RESET = "\u001B[0m";

    public BudgetTracker() {
        balance = 0.0;
        transactions = new ArrayList<>();
    }

    public void addIncome(double amount) {
        if (amount <= 0) {
            System.out.println(RED + "❌ Income must be positive!" + RESET);
            return;
        }
        balance += amount;
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        transactions.add(GREEN + "✓ [" + timestamp + "] Income: +" + amount + RESET);
        System.out.println(GREEN + "✓ Income added: Rs" + amount + RESET);
    }

    public void addExpense(double amount) {
        if (amount <= 0) {
            System.out.println(RED + "❌ Expense must be positive!" + RESET);
            return;
        }
        if (amount > balance) {
            System.out.println(RED + "❌ Insufficient balance! Current: Rs" + balance + RESET);
            return;
        }
        balance -= amount;
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        transactions.add(RED + "✗ [" + timestamp + "] Expense: -" + amount + RESET);
        System.out.println(RED + "✗ Expense deducted: Rs" + amount + RESET);
    }

    public double getBalance() {
        return balance;
    }

    public void showBalance() {
        System.out.println(CYAN + "\n" + "═".repeat(50) + RESET);
        System.out.println(BOLD + BLUE + "           💰 CURRENT BALANCE 💰" + RESET);
        System.out.println(CYAN + "═".repeat(50) + RESET);
        if (balance >= 0) {
            System.out.println(GREEN + BOLD + "           Rs " + String.format("%.2f", balance) + RESET);
        } else {
            System.out.println(RED + BOLD + "           Rs " + String.format("%.2f", balance) + " (NEGATIVE)" + RESET);
        }
        System.out.println(CYAN + "═".repeat(50) + RESET + "\n");
    }

    public void showTransactionHistory() {
        System.out.println(CYAN + "\n" + "═".repeat(50) + RESET);
        System.out.println(BOLD + BLUE + "        📊 TRANSACTION HISTORY 📊" + RESET);
        System.out.println(CYAN + "═".repeat(50) + RESET);
        if (transactions.isEmpty()) {
            System.out.println(YELLOW + "        No transactions yet!" + RESET);
        } else {
            for (String transaction : transactions) {
                System.out.println(transaction);
            }
        }
        System.out.println(CYAN + "═".repeat(50) + RESET + "\n");
    }

    public static void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            // Fallback for non-Windows systems
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }

    public static void showHeader() {
        System.out.println(BOLD + CYAN + "\n" + "═".repeat(50) + RESET);
        System.out.println(BOLD + YELLOW + "  💳 SMART BUDGET TRACKER 💳" + RESET);
        System.out.println(BOLD + CYAN + "═".repeat(50) + RESET + "\n");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BudgetTracker tracker = new BudgetTracker();
        
        showHeader();

        while (true) {
            System.out.println(BOLD + "┌─ Main Menu ─────────────────────┐" + RESET);
            System.out.println(BOLD + "│  " + GREEN + "1" + RESET + BOLD + " ➕ Add Income           │" + RESET);
            System.out.println(BOLD + "│  " + RED + "2" + RESET + BOLD + " ➖ Add Expense          │" + RESET);
            System.out.println(BOLD + "│  " + BLUE + "3" + RESET + BOLD + " 💰 View Balance         │" + RESET);
            System.out.println(BOLD + "│  " + CYAN + "4" + RESET + BOLD + " 📜 View History        │" + RESET);
            System.out.println(BOLD + "│  " + YELLOW + "5" + RESET + BOLD + " 🚀 Exit                 │" + RESET);
            System.out.println(BOLD + "└─────────────────────────────────┘" + RESET);
            System.out.print(BOLD + "Enter your choice (1-5): " + RESET);
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Clear buffer

                switch (choice) {
                    case 1:
                        System.out.print(GREEN + "Enter income amount (Rs): " + RESET);
                        double income = scanner.nextDouble();
                        tracker.addIncome(income);
                        break;
                    case 2:
                        System.out.print(RED + "Enter expense amount (Rs): " + RESET);
                        double expense = scanner.nextDouble();
                        tracker.addExpense(expense);
                        break;
                    case 3:
                        tracker.showBalance();
                        break;
                    case 4:
                        tracker.showTransactionHistory();
                        break;
                    case 5:
                        System.out.println(BOLD + GREEN + "\n✨ Thank you for using Budget Tracker! ✨" + RESET);
                        System.out.println(BOLD + BLUE + "Final Balance: Rs" + String.format("%.2f", tracker.getBalance()) + RESET + "\n");
                        scanner.close();
                        return;
                    default:
                        System.out.println(RED + "❌ Invalid choice! Please enter 1-5." + RESET);
                }
            } catch (Exception e) {
                System.out.println(RED + "❌ Invalid input! Please enter a number." + RESET);
                scanner.nextLine(); // Clear buffer
            }
        }
    }
}