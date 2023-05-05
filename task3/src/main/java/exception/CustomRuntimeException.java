package exception;

public class CustomRuntimeException extends RuntimeException {

    private final CustomRuntimeExceptionCode customRuntimeExceptionCode;

    public CustomRuntimeException(CustomRuntimeExceptionCode customRuntimeExceptionCode) {
        super(customRuntimeExceptionCode.getMessage());
        this.customRuntimeExceptionCode = customRuntimeExceptionCode;
    }

    public String getMessage() {
        return customRuntimeExceptionCode.getMessage();
    }
}
