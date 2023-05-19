package command;

import java.util.function.Supplier;
import java.util.regex.Pattern;

public enum CommandRegex {

    COMMAND(() -> Pattern.compile("^[A-Z>,*\\s\\d]+$")),
    COMMA(() -> Pattern.compile("^(?:[^,]*,){1}[^,]*$")),
    RELATION(() -> Pattern.compile("^(?:[^>]*>){1}[^>]*$")),
    UPDATE(() -> Pattern.compile("^(?:[^@]*>){1}[^@]*$")),
    UPPERCASE(() -> Pattern.compile("[A-Z]+")),

    ;
    final Pattern pattern;

    CommandRegex(Supplier<Pattern> supplier) {
        this.pattern = supplier.get();
    }

    public boolean matches(String input) {
        return pattern.matcher(input).matches();
    }
}
