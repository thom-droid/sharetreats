package command;

import mock.CommandTestUtils;
import mock.MockupData;
import mock.TestMockData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import voucher.*;

import static org.junit.jupiter.api.Assertions.*;

class CommandEnumTest {

    CommandFactory commandFactory = new CommandFactoryImpl(CommandTestUtils.MockEnumCommand.class);
    CommandEnumWrapper commandWrapper = commandFactory.createCommandProcessor();
    CommandController commandController = new CommandController(commandWrapper);
    TestMockData mockupData = (TestMockData) CommandTestUtils.MockCommandInitiator.getMockupData();

    @Test
    public void givenCheckCommand_whenProcessInvoked_thenDoesNotThrow() {

        String itemCode = mockupData.getAnyCode();
        String input = "check " + itemCode;

        assertDoesNotThrow(() -> commandController.process(input));

    }

    @Test
    public void givenClaimCommand_whenProcessInvoked_thenDoesNotThrow() {

        Voucher voucher = mockupData.getRandomAvailableVoucher();
        String shopCode = voucher.getItem().getShop().getCode();
        String itemCode = voucher.getCode();

        String input = "claim " + shopCode + " " + itemCode;

        assertDoesNotThrow(() -> commandController.process(input));
    }
}