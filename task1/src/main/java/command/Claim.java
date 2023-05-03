package command;

import code.CodeGeneratorConfigGetter;
import exception.CustomRuntimeException;
import exception.CustomRuntimeExceptionCode;
import voucher.VoucherService;

import java.util.Arrays;

public class Claim extends AbstractCommand {

    public Claim(CodeGeneratorConfigGetter codeGeneratorConfigGetter, VoucherService voucherService) {
        super(codeGeneratorConfigGetter, voucherService);
    }

    @Override
    public String process(String command) {
        String[] codes = parse(command);
        String shopCode = codes[0];
        String itemCode = codes[1];
        return voucherService.claim(shopCode, itemCode);
    }

    @Override
    public String[] parse(String command) {
        validateCommandFrom(command);

        String[] segments = segment(command);

        validate(segments);

        String itemCode = createValidatedItemCodeFrom(Arrays.copyOfRange(segments, 2, segments.length));
        String shopCode = segments[1];

        return new String[]{shopCode, itemCode};
    }

    private void validate(String[] segments) {
        validateSegments(segments);
        String shopCode = segments[1];
        validateShopCode(shopCode);
    }

    private void validateSegments(String[] segments) {
        if (segments.length != 5)
            throw new CustomRuntimeException(CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MALFORMED_CODE);
    }

    private void validateShopCode(String shopCode) {
        String shopCodeRegex = createRegex(shopCodeCharset);
        if (shopCode.length() != shopCodeLength || !shopCode.matches(shopCodeRegex)) {
            throw new CustomRuntimeException(CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MALFORMED_CODE);
        }
    }
}
