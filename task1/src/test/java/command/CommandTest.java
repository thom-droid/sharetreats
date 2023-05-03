package command;

import code.CodeGeneratorConfigGetter;
import mock.TestMockData;
import org.junit.jupiter.api.Test;
import voucher.*;

import static org.junit.jupiter.api.Assertions.*;

class CommandTest {

    TestMockData mockData = new TestMockData();
    CodeGeneratorConfigGetter codeGeneratorConfigGetter = mockData;
    VoucherRepository voucherRepository = new VoucherRepositoryImpl(mockData);
    VoucherService voucherService = new VoucherServiceImpl(voucherRepository);

    @Test
    public void givenCheckCommand_whenProcessInvoked_thenDoesNotThrow() {

        Command command = new Check(codeGeneratorConfigGetter, voucherService);
        String itemCode = mockData.getAnyCode();
        String input = "check " + itemCode;

        assertDoesNotThrow(() -> command.process(input));

    }

    @Test
    public void givenClaimCommand_whenProcessInvoked_thenDoesNotThrow() {

        Command command = new Claim(codeGeneratorConfigGetter, voucherService);
        Voucher voucher = mockData.getRandomAvailableVoucher();
        String shopCode = voucher.getItem().getShop().getCode();
        String itemCode = voucher.getCode();

        String input = "claim " + shopCode + " " + itemCode;

        assertDoesNotThrow(() -> command.process(input));
    }
}