package banking;

public class Main {
    public static void main(String[] args) {
        SQLite.createNewDataBase("mydatabase.db");
        BankService bankService = new BankService();
        bankService.showMenu();
    }
}