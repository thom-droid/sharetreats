package command;

import code.CodeGeneratorConfigGetter;
import code.CodeGeneratorConfigurer;
import exception.CustomRuntimeException;
import exception.CustomRuntimeExceptionCode;
import voucher.VoucherService;

import java.util.Objects;

/** 각 명령어가 입력받은 내용을 검증할 수 있도록 공통 메서드를 정의한 추상 클래스입니다. */

public abstract class AbstractCommand implements Command {

    protected final int itemCodeLength;
    protected final int shopCodeLength;
    protected final String itemCodeCharset;
    protected final String shopCodeCharset;
    protected final VoucherService voucherService;
    private final CodeGeneratorConfigGetter codeGeneratorConfigGetter;

    protected AbstractCommand(CodeGeneratorConfigGetter codeGeneratorConfigGetter, VoucherService voucherService) {
        this.codeGeneratorConfigGetter = codeGeneratorConfigGetter;
        this.itemCodeLength = codeGeneratorConfigGetter.getItemCodeConfig().getLength();
        this.itemCodeCharset = codeGeneratorConfigGetter.getItemCodeConfig().getCharset();
        this.shopCodeLength = codeGeneratorConfigGetter.getShopCodeConfig().getLength();
        this.shopCodeCharset = codeGeneratorConfigGetter.getShopCodeConfig().getCharset();
        this.voucherService = voucherService;
    }

    protected abstract String[] parse(String command);

    protected String[] segment(String input) {
        Objects.requireNonNull(input);

        if (input.isBlank()) {
            throw new CustomRuntimeException(CustomRuntimeExceptionCode.EMPTY_COMMAND);
        }

        input = input.trim();
        return input.split(" ");
    }

    protected String createRegex(String charset) {
        if (charset.equals(CodeGeneratorConfigurer.Charset.NUMERIC)) {
            return "[0-9]+";
        }

        if (charset.equals(CodeGeneratorConfigurer.Charset.ALPHABET_LOWERCASE)) {
            return "[a-z]+";
        }

        if (charset.equals(CodeGeneratorConfigurer.Charset.ALPHABET_UPPERCASE)) {
            return "[A-Z]+";
        }

        if (charset.equals(CodeGeneratorConfigurer.Charset.ALPHABET)) {
            return "[A-Za-z]+";
        }

        return "[0-9]+";
    }

    protected String createValidatedItemCodeFrom(String[] codeSegment) {
        String itemCodeRegex = createRegex(itemCodeCharset);
        int len = 0;
        int part = codeGeneratorConfigGetter.getItemCodeConfig().getSpace();
        int partLength = itemCodeLength / part;

        for (String s : codeSegment) {
            if (!s.matches(itemCodeRegex)) {
                throw new CustomRuntimeException(CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MALFORMED_CODE);
            }

            int length = s.length();

            if (length != partLength) {
                throw new CustomRuntimeException(CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MALFORMED_CODE);
            }

            len += length;
        }

        if (len != itemCodeLength) {
            throw new CustomRuntimeException(CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MALFORMED_CODE);
        }

        return String.join(" ", codeSegment[0], codeSegment[1], codeSegment[2]);
    }
}
