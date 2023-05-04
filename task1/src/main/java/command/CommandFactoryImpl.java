package command;

public class CommandFactoryImpl implements CommandFactory {

    private final Class<? extends CommandEnumWrapper> command;

    public CommandFactoryImpl(Class<? extends CommandEnumWrapper> command) {
        this.command = command;
    }

    //Todo 설명 구체적으로 적기
    // enum 의 supplier가 수행되어 리턴값이 싱글턴 인스턴스가 할당
    @Override
    public CommandEnumWrapper createCommandProcessor() {

        CommandEnumWrapper[] commands = command.getEnumConstants();

        if (commands.length == 0) {
            throw new IllegalArgumentException(command.getName() + "CommandEnumWrapper 구현 enum이 아닙니다.");
        }

        return commands[0];
    }

}
