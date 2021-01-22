package banking;

public class Main {
    public static void main(String[] args) {
        SQLite.createNewDataBase("card.s3db");
        BankService bankService = new BankService();
        bankService.showMenu();
    }
}