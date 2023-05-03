package command;

import code.CodeGeneratorConfigGetter;

import java.util.function.Supplier;

public enum CommandEnum implements Command {

    CHECK(() -> new Check()),
    HELP(() -> ),
    CLAIM("CLAIM"),
    EXIT("EXIT");


    final Supplier<Command> supplier;

    CommandEnum(Supplier<Command> supplier) {
        this.supplier = supplier;
    }

    @Override
    public String process(String command) {
        return supplier.get().process(command);
    }
}
