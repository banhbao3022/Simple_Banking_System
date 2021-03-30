# Simple_Banking_System
**A simple banking system with sqlite database work in Java Console. Maybe you need to add sqlite-jbdc to external librariies if you don't have it by sqlite.jar file**

**Main menu:**
1. Create an account:create new account with card number and PIN
2. Log in to account: login with card number and PIN
0. Exit

**If you login successful.A login menu will show:**
1. Balance: check your current balance
2. Add income: deposit money to the account.
3. Do transfer: transfer money to another account
* If the user tries to transfer more money than he/she has, output: Not enough money!
* If the user tries to transfer money to the same account, output: You can't transfer money to the same account!
* If the receiver's card number doesn’t pass the Luhn algorithm, output: Probably you made a mistake in the card number. Please try again!
* If the receiver's card number doesn’t exist, output: Such a card does not exist.
* If there is no error, ask the user how much money they want to transfer and make the transaction.
4. Close account: delete account from database
5. Log out: to the main menu
0. Exit

**Some test case:**

https://gist.github.com/banhbao3022/2ce4107e51a7c2c94f2c2d1ca920ae0d
