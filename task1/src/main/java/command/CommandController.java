package command;

import exception.CustomRuntimeException;
import exception.CustomRuntimeExceptionCode;
import voucher.VoucherService;

import java.util.Objects;

/** <p> 요청받은 입력값을 검증하고 검증에 성공하면 {@link VoucherService}에 요청을 전달합니다.
 * {@link CommandEnumWrapper} 를 통해 실행되어야할 {@link Command} 를 찾고, {@code Command.process()}
 * 를 통해 해당 커맨드에 할당된 업무를 수행합니다.
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
