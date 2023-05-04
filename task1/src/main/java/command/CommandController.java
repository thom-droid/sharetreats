package command;

import exception.CustomRuntimeException;
import exception.CustomRuntimeExceptionCode;
import voucher.VoucherService;

import java.util.Objects;

/** <p> 요청받은 입력값을 검증하고 검증에 성공하면 {@link VoucherService}에 요청을 전달합니다.
 * 명령어 부분과 인자 부분을 검증하기 위해 입력값을 배열로 나눈 뒤 명령어 내용에 따라 인자를 다시 검증합니다.
 * </p>
 * */
public class CommandController {

    private final CommandEnumWrapper commandEnumWrapper;

    public CommandController(CommandEnumWrapper commandEnumWrapper) {
        this.commandEnumWrapper = commandEnumWrapper;
    }

    public String process(String input) {

        String[] segments = segment(input);
        String commandSegment = segments[0].toUpperCase();

        Command command = commandEnumWrapper.findCommand(commandSegment);
        return command.process(input);
    }

    private String[] segment(String input) {
        Objects.requireNonNull(input);

        if (input.isBlank()) {
            throw new CustomRuntimeException(CustomRuntimeExceptionCode.EMPTY_COMMAND);
        }

        input = input.trim();
        return input.split(" ");
    }

}
