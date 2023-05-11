package department;

import exception.CustomNumberFormatException;
import exception.CustomRuntimeException;
import exception.CustomRuntimeExceptionCode;

import java.util.Arrays;

public class DepartmentController {

    private static final String COMMAND_REGEX = "^[A-Z>,*\\s\\d]+$";
    private static final String COMMA_REGEX = "^(?:[^,]*,){1}[^,]*$";
    private static final String RELATION_REGEX = "^(?:[^>]*>){1}[^>]*$";
    private static final String UPDATE_REGEX = "^(?:[^@]*>){1}[^@]*$";

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    public String parseCommand(String input) {

        if (!input.matches(COMMAND_REGEX)) {
            throw new CustomRuntimeException(CustomRuntimeExceptionCode.NOT_VALID_COMMAND);
        }

        if (input.matches(COMMA_REGEX)) {

            String[] segments = segment(input, ",");

            Department department = toDepartmentFrom(segments);

            return departmentService.post(department);

        } else if (input.matches(RELATION_REGEX)) {

            String[] segments = segment(input, ">");

            validateLength(segments);

            String sup = validateName(segments[0]);
            String sub = validateName(segments[1]);

            return departmentService.relate(sup, sub);

        } else if (input.matches(UPDATE_REGEX)) {

            String[] segments = segment(input, "@");

            Department department = toDepartmentFrom(segments);

            return departmentService.update(department);

        } else {
            String name = validateName(input.trim());
            return departmentService.getDepartment(name);
        }
    }

    private String[] segment(String input, String delimiter) {
        return Arrays.stream(input.trim().split(delimiter)).map(String::trim).toArray(String[]::new);
    }

    private void validateLength(String[] segments) {
        if (segments.length != 2)
            throw new CustomRuntimeException(CustomRuntimeExceptionCode.NOT_VALID_COMMAND);
    }

    private String validateName(String name) {
        if (!name.matches(Department.REGEX))
            throw new CustomRuntimeException(CustomRuntimeExceptionCode.NOT_VALID_NAME);
        return name;
    }

    private int validateHeadCount(String headCount) {
        try {
            return Integer.parseInt(headCount);
        } catch (IllegalArgumentException e) {
            throw new CustomRuntimeException(new CustomNumberFormatException(), CustomRuntimeExceptionCode.NOT_VALID_COMMAND);
        }
    }

    private Department toDepartmentFrom(String[] segments) {
        validateLength(segments);
        String name = validateName(segments[0]);
        int headcount = validateHeadCount(segments[1]);
        return Department.of(headcount, name);
    }

}
