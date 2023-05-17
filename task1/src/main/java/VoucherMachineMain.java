import command.*;
import command.commands.Help;
import exception.CustomRuntimeException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class VoucherMachineMain {

    public static void main(String[] args) throws IOException {

        CommandEnumWrapperFactory commandEnumWrapperFactory = new CommandEnumWrapperFactoryImpl(CommandEnum.class);
        CommandEnumWrapper commandEnumWrapper = commandEnumWrapperFactory.createCommandEnumWrapper();
        CommandController commandController = new CommandController(commandEnumWrapper);

        boolean runtime = true;
        System.out.println(Help.MESSAGE);

        while (runtime) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String line = br.readLine();
            System.out.println("입력된 커맨드 : " + line);
            try {

                String result = commandController.process(line);
                if (result.equals("EXIT")) {
                    System.out.println("프로그램을 종료합니다.");
                    break;
                }

                System.out.println(result);
            } catch (CustomRuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

