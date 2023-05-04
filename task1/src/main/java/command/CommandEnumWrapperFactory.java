package command;

public class CommandEnumWrapperFactory {

    private final CommandFactory commandFactory;

    public CommandEnumWrapperFactory(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    public CommandEnumWrapper createCommandProcessor() {
        return commandFactory.createCommandProcessor();
    }

}