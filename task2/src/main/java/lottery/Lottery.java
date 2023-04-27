package lottery;

import wallet.Wallet;

import java.time.LocalDateTime;

public interface Lottery {

    int PRICE = 100;

    String draw(Wallet wallet, int count, LocalDateTime drawDate);

}
