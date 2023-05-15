package command;

public class CommandFactoryImpl implements CommandFactory {

    private final Class<? extends CommandEnumWrapper> command;

    public CommandFactoryImpl(Class<? extends CommandEnumWrapper> command) {
        this.command = command;
    }

    @Override
    public CommandEnumWrapper createCommandProcessor() {

        CommandEnumWrapper[] commands = command.getEnumConstants();

        if (commands.length == 0) {
            throw new IllegalArgumentException(command.getName() + "CommandEnumWrapper 구현 enum이 아닙니다.");
        }

        return commands[0];
    }

}
