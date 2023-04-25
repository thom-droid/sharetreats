import code.CodeGeneratorConfigGetter;
import command.Command;
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

    public static final String MESSAGE =
            "\n===========================================================================================\n" +
                    "상품 교환 프로그램을 실행합니다. 콘솔에 예시로 출력된 20개의 교환권을 이용해 테스트 하실 수 있습니다. \n" +
                    "입력할 수 있는 명령어는 다음과 같습니다. CHECK, HELP, CLAIM, EXIT \n" +
                    "1. CHECK [상품코드] : 교환 가능 여부 확인. 예) CHECK 111 111 111 또는 check 111 111 111 \n" +
                    "2. HELP : 메뉴얼 확인 \n" +
                    "3. CLAIM [상점번호] [쿠폰번호] : 상품 교환. 예) CLAIM eGxmdj 111 111 111 또는 claim eGxmdj 111 111 111 \n" +
                    "4. EXIT : 프로그램 종료 \n" +
                    "명령어의 대소문자는 구분이 없으나, 상점코드는 대소문자를 구분합니다. \n" +
                    "전체 코드는 예시처럼 띄어쓰기를 맞추어서 입력하셔야 합니다. 예) claim xpgjlM 111231111 (x) \n" +
                    "위의 조건이 맞지 않는 문자열이 입력된 경우 예외가 발생하며 프로그램이 종료됩니다. " +
                    "\n===========================================================================================\n";

    public static void main(String[] args) throws IOException {

        MockupData mockupData = new MockupDataImpl();
        CodeGeneratorConfigGetter codeGeneratorConfigGetter = (CodeGeneratorConfigGetter) mockupData;
        VoucherService voucherService = new VoucherServiceImpl(new VoucherRepositoryImpl(mockupData));
        CommandController commandController = new CommandController(voucherService, codeGeneratorConfigGetter);

        boolean runtime = true;
        System.out.println(MESSAGE);

        while (runtime) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String line = br.readLine();
            System.out.println("입력된 커맨드 : " + line);

            String result = commandController.validateCommand(line);

            if (result.equals(Command.EXIT.getDesc())) {
                System.out.println("프로그램을 종료합니다.");
                break;
            } else if (result.equals(Command.HELP.getDesc())) {
                System.out.println(MESSAGE);
            } else {
                System.out.println(result);
            }
        }
    }
}

