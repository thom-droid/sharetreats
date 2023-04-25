package exception;

public class CustomRuntimeException extends RuntimeException {

    private final String message;

    public CustomRuntimeException(CustomRuntimeExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.message = exceptionCode.getMessage();
    }

    public String getMessage() {
        return message;
    }

}
