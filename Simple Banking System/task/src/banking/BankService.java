package banking;

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
        int choice = sc.nextInt();
        switch (choice) {
                case 1:
                    this.createAccount();
                case 2:
                    this.logIn();
                case 0:
                    this.exit();
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
            double choice = sc.nextDouble();
            switch ((int) choice) {
                case 4:
                    this.closeAccount();
                case 5:
                    this.logOut();
                case 1:
                    this.checkBalance();
                case 2:
                    this.addIncome();
                case 3:
                    this.doTransfer();
                case 0:
                    this.exit();
                default:
                    System.out.println("Wrong choice");
                    this.showMenu();
            }
        } catch (NoSuchElementException e) {
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
        int income = sc.nextInt();
        SQLite.addIncome(income,cardNumber);
        System.out.println("Income was added!");
        this.showMenu();
    }
    private void doTransfer(){
        try {
            System.out.println("Transfer");
            System.out.println("Enter card number:");
            String cardNumber = sc.next();
            if (!Card.luhnAlgorithm(cardNumber)) {
                System.out.println("Probably you made a mistake in the card number. Please try again!");
            } else {
                if (!SQLite.checkCard(cardNumber)) {
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
            e.printStackTrace();
        }
    }
    private void closeAccount(){
        SQLite.closeAccount(cardNumber);
        System.out.println("The account has been closed!");
        logIn = false;
        this.showMenu();
    }

}
