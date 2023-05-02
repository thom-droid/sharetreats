import code.CodeGeneratorConfigGetter;
import command.CommandEnum;
import command.Help;
import mock.MockupData;
import mock.MockupDataImpl;
import command.CommandController;
import voucher.VoucherRepositoryImpl;
import voucher.VoucherService;
import voucher.VoucherServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class VoucherMachineMain {

    public static void main(String[] args) throws IOException {

        MockupData mockupData = new MockupDataImpl();
        CodeGeneratorConfigGetter codeGeneratorConfigGetter = (CodeGeneratorConfigGetter) mockupData;
        VoucherService voucherService = new VoucherServiceImpl(new VoucherRepositoryImpl(mockupData));
        CommandController commandController;

        boolean runtime = true;
        System.out.println(Help.MESSAGE);

        while (runtime) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String line = br.readLine();
            System.out.println("입력된 커맨드 : " + line);

            String result = commandController.validateCommand(line);

            if (result.equals(CommandEnum.EXIT.getDesc())) {
                System.out.println("프로그램을 종료합니다.");
                break;
            } else if (result.equals(CommandEnum.HELP.getDesc())) {
                System.out.println(Help.MESSAGE);
            } else {
                System.out.println(result);
            }
        }
    }
}

