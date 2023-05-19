package lottery;

import org.junit.jupiter.api.Test;
import testdb.TestLotteryInventory;
import wallet.Wallet;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class LotteryImplTest {

    LotteryInventory lotteryInventory = new LotteryInventoryImpl();
    Lottery lottery = new LotteryImpl(lotteryInventory);

    @Test
    void givenNotEnoughBalance_whenTrialsOutnumbers_thenWalletTopsUp() {

        //given
        Wallet wallet = new Wallet(1000);
        int trials = 11;

        //when
        String answer = lottery.draw(wallet, trials, LocalDateTime.now());
        String[] split = answer.split("\n");
        String topUpMessage = split[split.length - 2];

        //then
        assertEquals(topUpMessage, "10,000원 충전되었습니다. ");
        assertEquals(wallet.getBalance(), 10000);

    }

    @Test
    void whenDrawn_thenNoExpiredItemIsReturned() {

        //given
        Wallet wallet = new Wallet(1000);
        int trials = 10;
        LocalDateTime drawTime = LocalDateTime.now();

        //when
        String answer = lottery.draw(wallet, trials, drawTime);
        String[] split = answer.split("\n");

        // pick up date part from the answer
        List<LocalDateTime> dateParts = Arrays.stream(split)
                .filter(s -> s.startsWith("당첨"))
                .map(
                        s -> {
                            String dateString = s.split(", ")[2];
                            return LocalDateTime.parse(dateString);
                        })
                .collect(Collectors.toList());

        //then
        for (LocalDateTime datePart : dateParts) {
            assertTrue(datePart.isBefore(drawTime));
        }

    }
}