package banking;

import java.sql.SQLOutput;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class BankService {
    private String cardNumber;
    private Card card;
    private Scanner sc = new Scanner(System.in);
    private boolean logIn = false;
    public void showMenu() {
        if (!logIn) {
            System.out.println(Menu.OUT.getText());
            this.bankService();
        } else {
            System.out.println(Menu.IN.getText());
            this.logInMenu();
        }
    }
    public void bankService() {
        String choice = sc.next();
        switch (choice) {
                case "1":
                    this.createAccount();
                    break;
                case "2":
                    this.logIn();
                    break;
                case "0":
                    this.exit();
                    break;
                default:
                    System.out.println("Wrong choice");
                    this.showMenu();
            }

    }
    private void exit() {
        System.out.println("Bye!");
        System.exit(0);

    }
    public void logInMenu() {
        try {
            String choice = sc.next();
            switch (choice) {
                case "4":
                    this.closeAccount();
                    break;
                case "5":
                    this.logOut();
                    break;
                case "1":
                    this.checkBalance();
                    break;
                case "2":
                    this.addIncome();
                    break;
                case "3":
                    this.doTransfer();
                    break;
                case "0":
                    this.exit();
                    break;
                default:
                    System.out.println("Wrong choice");
                    this.showMenu();
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error");
            this.showMenu();
        }
    }
    private void createAccount() {
        this.card = new Card();
        System.out.println("Your card has been created");
        System.out.println(this.card.toString());
        this.showMenu();
    }
    private void logIn() {
        try {
            System.out.println("Enter your card number:");
            String cardNumber = sc.next();
            System.out.println("Enter your PIN");
            String PIN = sc.next();
            if (SQLite.checkLogin(cardNumber,PIN)) {
                logIn = true;
                this.cardNumber = cardNumber;
                System.out.println("You have successfully logged in");
            } else {
                logIn = false;
                System.out.println("Wrong card number or PIN!");
            }
            this.showMenu();
        } catch (NoSuchElementException e) {
            logIn = false;
            System.out.println("Wrong card number or PIN!");
            this.showMenu();
        } catch (NullPointerException e) {
            logIn = false;
            System.out.println("You don't have an account");
            this.showMenu();
        }
    }
    private void logOut(){
        this.logIn = false;
        this.showMenu();
    }
    private void checkBalance() {
        int balance = SQLite.checkBalance(cardNumber);
        System.out.println("Balance: " + balance);
        this.showMenu();
    }
    private void addIncome() {
        System.out.println("Enter income:");
        try {
            int income = sc.nextInt();
            SQLite.addIncome(income, cardNumber);
            System.out.println("Income was added!");
        }catch (Exception e){
            System.out.println("Wrong income,please try again");
        } finally {
            this.showMenu();
        }
    }
    private void doTransfer(){
        try {
            System.out.println("Transfer");
            System.out.println("Enter card number:");
            String cardNumber = sc.next();
            if (!Card.luhnAlgorithm(cardNumber)) {
                System.out.println("Probably you made a mistake in the card number. Please try again!");
            } else {
                if (cardNumber.equals(this.cardNumber)) System.out.println("You can't transfer money to the same account!");
                else if (!SQLite.checkCard(cardNumber)) {
                    System.out.println("Such a card does not exist.");
                } else {
                    System.out.println("Enter how much money you want to transfer:");
                    int moneyTransfer = sc.nextInt();
                    if (SQLite.checkBalance(this.cardNumber) < moneyTransfer) {
                        System.out.println("Not enough money!");
                    } else {
                        System.out.println("Success");
                        SQLite.addIncome(-moneyTransfer,this.cardNumber);
                        SQLite.addIncome(moneyTransfer,cardNumber);
                    }
                }
            }
            this.showMenu();
        } catch (Exception e) {
            System.out.println("Wrong card number,please try again");
            this.showMenu();
        }
    }
    private void closeAccount(){
        SQLite.closeAccount(cardNumber);
        System.out.println("The account has been closed!");
        logIn = false;
        this.showMenu();
    }

}
