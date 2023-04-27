package lottery;

import item.Item;
import wallet.Wallet;

import java.time.LocalDateTime;
import java.util.Optional;

public class LotteryImpl implements Lottery {

    private final LotteryInventory lotteryInventory;

    public LotteryImpl(LotteryInventory lotteryInventory) {
        this.lotteryInventory = lotteryInventory;
    }

    @Override
    public String draw(Wallet wallet, int trials, LocalDateTime drawTime) {
        if (wallet == null || drawTime == null) {
            throw new NullPointerException("지갑 또는 뽑기 수행 날짜가 정확히 입력되지 않았습니다. ");
        }

        int tmp = trials;
        StringBuilder answer = new StringBuilder();

        while (wallet.hasEnoughBalance() && trials != 0) {
            Optional<Item> item = lotteryInventory.pick(drawTime);

            item.ifPresent(i -> answer.append(i.toStringWhenSucceeds()));

            if (item.isEmpty()) {
                answer.append("안타깝게도 꽝입니다!");
            }

            answer.append("\n");
            wallet.subtract();
            trials--;
        }

        if (trials != 0 && !wallet.hasEnoughBalance()) {
            answer.append("잔고가 부족합니다. 충전을 진행합니다. 다시 뽑기를 진행해주세요. \n");
            wallet.topUp();
            answer.append("10,000원 충전되었습니다. \n");
        }

        String msg =
                "총 " + tmp + "회 중 " + (tmp - trials) + " 회 뽑기를 하였습니다. " +
                "현재 잔액: [ " + wallet.getBalance() + " ] 원";
        answer.append(msg);

        return answer.toString();
    }

}
