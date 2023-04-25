package exception;

public enum CustomRuntimeExceptionCode {

    NOT_VALID_CODE(1, "존재하지 않거나 유효하지 않은 코드입니다"),
    EXPIRED_CODE(2, "사용기간이 만료된 코드입니다"),
    ALREADY_USED_CODE(3, "이미 사용된 코드입니다"),
    SHOPCODE_NOT_MATCHED(4, "상점 코드가 일치하지 않습니다."),

    EMPTY_COMMAND(100, "공백만 입력되었습니다."),
    ILLEGAL_ARGUMENT(101, "잘못된 인자가 입력되었습니다."),
    ILLEGAL_ARGUMENT_MALFORMED_CODE(102, "코드 입력 내용이 올바르지 않습니다."),
    ILLEGAL_ARGUMENT_MISTYPED_COMMAND(103, "명령어가 잘못 입력되었습니다."),
    ILLEGAL_ARGUMENT_NOT_MATCHED_SPACE(104, "입력된 코드의 형태가 잘못되었습니다."),

    ;

    final int code;
    final String message;

    CustomRuntimeExceptionCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
