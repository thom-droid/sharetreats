package command;

public interface CommandEnumWrapper {

    String process(String command);

    Command findCommand(String command);

}
