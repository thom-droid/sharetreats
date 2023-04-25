package command;

import code.CodeGeneratorConfigurer;
import code.CodeGeneratorConfigGetter;
import exception.CustomRuntimeException;
import exception.CustomRuntimeExceptionCode;
import voucher.VoucherService;

import java.util.Arrays;
import java.util.Objects;

/** <p> 요청받은 입력값을 검증하고 검증에 성공하면 {@link VoucherService}에 요청을 전달합니다.
 * 명령어 부분과 인자 부분을 검증하기 위해 입력값을 배열로 나눈 뒤 명령어 내용에 따라 인자를 다시 검증합니다.
 * </p>
 * */
public class CommandController {

    private final int itemCodeLength;
    private final int shopCodeLength;
    private final String itemCodeCharset;
    private final String shopCodeCharset;
    private final VoucherService voucherService;
    private final CodeGeneratorConfigGetter codeGeneratorConfigGetter;

    public CommandController(VoucherService voucherService, CodeGeneratorConfigGetter codeGeneratorConfigGetter) {
        this.voucherService = voucherService;
        this.codeGeneratorConfigGetter = codeGeneratorConfigGetter;
        this.itemCodeLength = codeGeneratorConfigGetter.getItemCodeConfig().getLength();
        this.itemCodeCharset = codeGeneratorConfigGetter.getItemCodeConfig().getCharset();
        this.shopCodeLength = codeGeneratorConfigGetter.getShopCodeConfig().getLength();
        this.shopCodeCharset = codeGeneratorConfigGetter.getShopCodeConfig().getCharset();
    }

    public String validateCommand(String input) {

        String[] segments = segmentInput(input);
        String commandSegment = segments[0].toUpperCase();

        Command command = Arrays.stream(Command.values())
                .filter(c -> commandSegment.equals(c.getDesc()))
                .findFirst()
                .orElseThrow( () -> new CustomRuntimeException(CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MISTYPED_COMMAND));

        if (command == Command.CHECK) {
            if (segments.length != 4) {
                throw new CustomRuntimeException(CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MALFORMED_CODE);
            }

            String[] codeSegment = Arrays.copyOfRange(segments, 1, segments.length);
            String code = createValidatedItemCodeFrom(codeSegment);

            return voucherService.check(code);

        } else if (command == Command.HELP) {

            return voucherService.help();

        } else if (command == Command.CLAIM) {

            String shopCodeRegex = getRegex(shopCodeCharset);

            if (segments.length != 5) {
                throw new CustomRuntimeException(CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MALFORMED_CODE);
            }

            String shopCode = segments[1];

            if (shopCode.length() != shopCodeLength || !shopCode.matches(shopCodeRegex)) {
                throw new CustomRuntimeException(CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MALFORMED_CODE);
            }

            String[] codeSegment = Arrays.copyOfRange(segments, 2, segments.length);
            String itemCode = createValidatedItemCodeFrom(codeSegment);

            return voucherService.claim(shopCode, itemCode);

        } else if (command == Command.EXIT) {
            return Command.EXIT.getDesc();
        }

        throw new CustomRuntimeException(CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MISTYPED_COMMAND);

    }

    private String[] segmentInput(String command) {
        Objects.requireNonNull(command);

        if (command.isBlank()) {
            throw new CustomRuntimeException(CustomRuntimeExceptionCode.EMPTY_COMMAND);
        }

        command = command.trim();
        return command.split(" ");
    }

    private String getRegex(String charset) {
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

    private String createValidatedItemCodeFrom(String[] codeSegment) {
        String itemCodeRegex = getRegex(itemCodeCharset);
        int len = 0;
        int part = codeGeneratorConfigGetter.getItemCodeConfig().getSpace();
        int partLength = itemCodeLength / part;

        for (String s : codeSegment) {
            if (!s.matches(itemCodeRegex)) {
                throw new CustomRuntimeException(CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MALFORMED_CODE);
            }

            int length  = s.length();

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
