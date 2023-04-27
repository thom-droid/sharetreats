package wallet;

import lottery.Lottery;

public class Wallet {

    private int balance;

    public Wallet(int balance) {
        this.balance = balance;
    }

    public void topUp(int amount) {
        this.balance += amount;
    }

    public void topUp() {
        this.balance += 10000;
    }

    public int getBalance() {
        return balance;
    }

    public void subtract(int amount) {
        this.balance -= amount;
    }

    public void subtract() {
        this.balance -= Lottery.PRICE;
    }

    public boolean hasEnoughBalance() {
        return this.balance > 0;
    }

}
