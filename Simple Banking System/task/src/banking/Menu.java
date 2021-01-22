package banking;

public enum Menu {
    IN("1. Balance\n" +
            "2. Add income\n" +
            "3. Do transfer\n" +
            "4. Close account\n" +
            "5. Log out\n" +
            "0. Exit"),
    OUT("1. Create an account\n" +
            "2. Log in to account\n" +
            "0. Exit");

    private final String text;
    Menu(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}

