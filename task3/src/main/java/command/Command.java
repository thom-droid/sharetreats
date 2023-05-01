package command;

public interface Command {

    void parse(String input);

    String process(String command);

}
