package command.commands;

import code.CodeGeneratorConfigGetter;
import command.AbstractCommand;
import mock.MockupData;
import mock.MockupDataImpl;
import voucher.VoucherService;
import voucher.VoucherServiceImpl;

public class Exit extends AbstractCommand {

    private static final Exit instance;

    public static Exit getInstance() {
        return instance;
    }

    static {
        MockupData mockupData = MockupDataImpl.getInstance();
        CodeGeneratorConfigGetter codeGeneratorConfigGetter1 = (CodeGeneratorConfigGetter) mockupData;
        VoucherService voucherService1 = VoucherServiceImpl.getInstance();
        instance = new Exit(codeGeneratorConfigGetter1, voucherService1);
    }

    private Exit(CodeGeneratorConfigGetter codeGeneratorConfigGetter, VoucherService voucherService) {
        super(codeGeneratorConfigGetter, voucherService);
    }

    @Override
    public String process(String command) {
        return "EXIT";
    }

    @Override
    public String[] parse(String command) {
        return null;
    }
}
