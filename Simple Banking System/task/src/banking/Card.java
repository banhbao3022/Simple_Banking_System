package banking;

import java.util.Random;

public class Card {
    private final String cardNumber;
    private final int cardPIN;
    public Card() {
        this.cardNumber = generateNumber();
        this.cardPIN = generatePIN();
        SQLite.updateNewCard(cardNumber,Integer.toString(cardPIN));
    }
    private String generateNumber() {
        Random random = new Random();
        String number = "400000" + Math.abs(random.nextLong() / 1000000000);
        while (!luhnAlgorithm(number)) {
            number = "400000" + Math.abs(random.nextLong() / 1000000000);
        }
        return number;
    }
    private int generatePIN(){
        Random random = new Random();
        return random.nextInt(9000) + 1000;
    }
    static int sumOfNumber(int n) {
        if (n == 0) return 0;
        return n % 10 + sumOfNumber(n / 10);
    }
    static boolean luhnAlgorithm(String cardNumber) {
        int sumOdd = 0;
        int sumEven = 0;
        for (int i = 0; i < cardNumber.length(); i++) {
            int num = Integer.parseInt(String.valueOf(cardNumber.charAt(i)));
            if (i % 2 != 0) {
                sumOdd += num;
            } else {
                if (num * 2 >= 10) {
                    sumEven += sumOfNumber(num * 2);
                } else sumEven += num * 2;

            }
        }
        if ((sumEven + sumOdd) % 10 == 0) return true;
        else return false;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public int getCardPIN() {
        return cardPIN;
    }
    @Override
    public String toString() {
        return "Your card number:\n" +
                this.getCardNumber() +"\n" +
                "Your card PIN:\n" +
                this.getCardPIN();
    }
}
