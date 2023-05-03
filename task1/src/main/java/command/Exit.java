package command;

import code.CodeGeneratorConfigGetter;
import voucher.VoucherService;

public class Exit extends AbstractCommand {

    public Exit(CodeGeneratorConfigGetter codeGeneratorConfigGetter, VoucherService voucherService) {
        super(codeGeneratorConfigGetter, voucherService);
    }

    @Override
    public String process(String command) {
        return null;
    }

    @Override
    public String[] parse(String command) {
        return null;
    }
}
