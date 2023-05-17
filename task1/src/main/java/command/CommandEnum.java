package command;

import command.commands.Check;
import command.commands.Claim;
import command.commands.Exit;
import command.commands.Help;
import exception.CustomRuntimeException;
import exception.CustomRuntimeExceptionCode;

import java.util.Arrays;
import java.util.Locale;
import java.util.function.Supplier;

/** Command 구현체를 싱글턴으로 생성하고 이름으로 편하게 사용하기 위해 정의한 Enum 클래스입니다.
 * 각 원소마다 싱글턴으로 인스턴스화 된 Command 구현체를 가지고 있습니다. */

public enum CommandEnum implements CommandEnumWrapper {

    CHECK(Check::getInstance, "CHECK"),
    HELP(Help::getInstance, "HELP"),
    CLAIM(Claim::getInstance, "CLAIM"),
    EXIT(Exit::getInstance, "EXIT"),
    ;

    final Supplier<Command> supplier;
    final Command command;
    final String name;

    CommandEnum(Supplier<Command> supplier, String name) {
        this.supplier = supplier;
        this.command = supplier.get();
        this.name = name;
    }

    @Override
    public String process(String command) {
        return this.command.process(command);
    }

    @Override
    public Command findCommand(String command) {
        CommandEnum commandEnum = Arrays.stream(this.getClass().getEnumConstants())
                .filter(e -> e.name().equals(command.toUpperCase(Locale.ROOT)))
                .findFirst()
                .orElseThrow(() -> new CustomRuntimeException(CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MISTYPED_COMMAND));

        return commandEnum.getCommand();
    }

    public Command getCommand() {
        return command;
    }
}
