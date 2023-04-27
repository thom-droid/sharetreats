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

    public static void main(String[] args) throws IOException {

        Wallet wallet = new Wallet(1000);
        LotteryInventory lotteryInventory = new LotteryInventoryImpl();
        Lottery lottery = new LotteryImpl(lotteryInventory);

        while (true) {

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String input = br.readLine();

            if (input.equals("exit")) {
                break;
            }

            int trials = Integer.parseInt(input);

            String answer = lottery.draw(wallet, trials, LocalDateTime.now());

            System.out.println(answer);
        }
    }
}
