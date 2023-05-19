import lottery.Lottery;
import lottery.LotteryImpl;
import lottery.LotteryInventory;
import lottery.LotteryInventoryImpl;
import wallet.Wallet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

public class LotteryMachineMain {

    private static final String MESSAGE =
            "================================================================\n" +
            "상품 추첨 프로그램을 시작합니다. \n" +
            "추첨 횟수를 입력하여 입력된 횟수만큼 상품 뽑기를 시도합니다.\n" +
            "입력할 수 있는 값은 자연수입니다. 이외의 값을 입력하면 정상 동작하지 않습니다.\n" +
            "추첨 1회 당 100원이 소모됩니다. 현재 지갑은 1000원이 충전되어 있으며, \n" +
            "잔액이 부족할 때는 자동으로 10,000원이 충전됩니다.\n" +
            "아래에는 뽑을 수 있는 등급별 상품 목록입니다.\n";

    public static void main(String[] args) throws IOException {

        System.out.println(MESSAGE);
        Wallet wallet = new Wallet(1000);
        LotteryInventory lotteryInventory = new LotteryInventoryImpl();
        Lottery lottery = new LotteryImpl(lotteryInventory);

        while (true) {

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String input = br.readLine();

            if (input.equals("exit")) {
                break;
            }

            try {
                int trials = Integer.parseInt(input);
                String answer = lottery.draw(wallet, trials, LocalDateTime.now());
                System.out.println(answer);
            } catch (IllegalArgumentException e) {
                System.out.println("1 이상의 자연수만 입력가능합니다.");
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }

        }
    }
}
