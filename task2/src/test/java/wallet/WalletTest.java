package wallet;

import lottery.Lottery;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WalletTest {

    Wallet wallet;

    @BeforeEach
    public void setup() {
        wallet = new Wallet(10000);
    }

    @Test
    void topUpsAs10000() {

        //given
        int amount = 20000;
        wallet.topUp();

        //then
        assertEquals(wallet.getBalance(), amount);

    }

    @Test
    void topUpsCertainAmount() {

        //given
        int amount = 15000;
        wallet.topUp(5000);

        //then
        assertEquals(wallet.getBalance(), amount);

    }

    @Test
    void subtractsAsLotteryPrice() {

        //given
        int prev = wallet.getBalance();
        wallet.subtract();

        //then
        assertEquals(wallet.getBalance(), prev - Lottery.PRICE);

    }

    @Test
    void subtractsCertainAmount() {

        //given
        int prev = wallet.getBalance();
        int amount = 5000;
        wallet.subtract(amount);

        //then
        assertEquals(wallet.getBalance(), prev - amount);

    }

    @Test
    void hasEnoughBalance() {

        //given
        int amount = wallet.getBalance();
        wallet.subtract(amount);

        //then
        assertFalse(wallet.hasEnoughBalance());

    }
}