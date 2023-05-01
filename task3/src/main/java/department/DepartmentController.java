package department;

import command.CommandE;

import java.util.Locale;

public class DepartmentController {

    public String parseCommand(String input) {
        // [insert] [department], [number of people]
        // [superior] > [sub]
        // insert new department
        String[] segments = input.trim().toUpperCase(Locale.ROOT).split(",");
        String command = segments[0];

        if (command.equals(CommandE.INSERT.toString())) {

        }

        return null;
    }

}
