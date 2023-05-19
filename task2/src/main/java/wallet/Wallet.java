package wallet;

import lottery.Lottery;

/**
 * 사용자의 지갑을 대표하는 클래스입니다. 금액 충전, 차감, 현재 잔액 확인 등의 메서드를 가지고 있습니다.
 * */

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
