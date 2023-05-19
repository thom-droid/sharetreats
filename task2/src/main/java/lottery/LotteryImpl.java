package lottery;

import item.Item;
import wallet.Wallet;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * {@link Lottery} 구현 클래스입니다.
 * */

public class LotteryImpl implements Lottery {

    private final LotteryInventory lotteryInventory;

    public LotteryImpl(LotteryInventory lotteryInventory) {
        this.lotteryInventory = lotteryInventory;
    }

    /**
     * <p>
     *     인자를 검증하고, 문제가 없을 경우 지갑의 잔고가 남아있는 동안 뽑기를 시도합니다.
     * 뽑기는 뽑기를 시도한 시간을 기준으로 유통기간이 지나지 않은 상품에 대해 뽑기를 하게 됩니다.
     * </p>
     * <p>
     *     확률에 따라 뽑기를 한 후 {@code Optional<Item>}을 확인합니다. 값이 비어있을 경우 꽝에 대한 메세지를,
     * 값이 있을 경우 그에 맞는 메세지를 만들어 리턴합니다.
     * </p>
     * <p>
     *     시도횟수가 남았는데 잔고가 부족한 경우 요구사항대로 10,000원을 충전합니다.
     * </p>
     * <p>
     *      모든 뽑기를 한 뒤에는 남은 잔고와 정상적으로 시도한 횟수를 포함한 메시지를 리턴합니다.
     * </p>
     *
     * @throws NullPointerException 사용자의 지갑 또는 시도하려는 날짜가 null 인 경우
     * */

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
