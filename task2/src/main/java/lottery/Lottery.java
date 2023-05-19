package lottery;

import wallet.Wallet;

import java.time.LocalDateTime;

/**
 * 복권을 표현하는 인터페이스입니다. 현재 사용자의 지갑과, 복권 시도 횟수, 뽑기를 시도할 때의 시간을 인자로 받아
 * 복권을 뽑는 {@code draw()}를 추상 메서드로 가지고 있습니다.
 * */

public interface Lottery {

    int PRICE = 100;

    String draw(Wallet wallet, int count, LocalDateTime drawDate);

}
