package command;

import code.CodeGeneratorConfigGetter;
import exception.CustomRuntimeException;
import exception.CustomRuntimeExceptionCode;
import voucher.VoucherService;

import java.util.Arrays;

public class Check extends AbstractCommand {

    public Check(CodeGeneratorConfigGetter codeGeneratorConfigGetter, VoucherService voucherService) {
        super(codeGeneratorConfigGetter, voucherService);
    }

    @Override
    public String process(String command) {
        String code = parse(command)[0];
        return voucherService.check(code);
    }

    @Override
    public String[] parse(String command) {
        validateCommandFrom(command);

        String[] segments = segment(command);

        validate(segments);

        String itemCode = createValidatedItemCodeFrom(Arrays.copyOfRange(segments, 1, segments.length));

        return new String[]{itemCode};
    }

    private void validate(String[] segments) {
        if (segments.length != 4) {
            throw new CustomRuntimeException(CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MALFORMED_CODE);
        }
    }
}
