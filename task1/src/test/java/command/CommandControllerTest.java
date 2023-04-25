package command;

import code.CodeGeneratorConfigGetter;
import exception.CustomRuntimeException;
import exception.CustomRuntimeExceptionCode;
import mock.MockupData;
import mock.TestMockData;
import org.junit.jupiter.api.Test;
import voucher.VoucherRepositoryImpl;
import voucher.VoucherService;
import voucher.VoucherServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class CommandControllerTest {

    MockupData mockupData = new TestMockData();
    CodeGeneratorConfigGetter codeGeneratorConfigGetter = (CodeGeneratorConfigGetter) mockupData;
    VoucherService voucherService = new VoucherServiceImpl(new VoucherRepositoryImpl(mockupData));
    CommandController commandController = new CommandController(voucherService, codeGeneratorConfigGetter);

    @Test
    void givenBlankString_thenThrows() {

        //given
        String command = "";
        String command2 = "    ";

        //then
        Throwable throwable = assertThrows(CustomRuntimeException.class, () -> commandController.validateCommand(command));
        Throwable throwable2 = assertThrows(CustomRuntimeException.class, () -> commandController.validateCommand(command2));
        assertEquals(throwable.getMessage(), CustomRuntimeExceptionCode.EMPTY_COMMAND.getMessage());
        assertEquals(throwable2.getMessage(), CustomRuntimeExceptionCode.EMPTY_COMMAND.getMessage());

    }

    @Test
    void givenMistypedCommand_thenThrows() {

        //given
        String command = "checks 011 0111 01";
        String command2 = "checks 111111101";
        String command3 = "cliam asdw 111 111 222";
        String command4 = "hlep";

        //then
        Throwable throwable = assertThrows(CustomRuntimeException.class, () -> commandController.validateCommand(command));
        Throwable throwable2 = assertThrows(CustomRuntimeException.class, () -> commandController.validateCommand(command2));
        Throwable throwable3 = assertThrows(CustomRuntimeException.class, () -> commandController.validateCommand(command3));
        Throwable throwable4 = assertThrows(CustomRuntimeException.class, () -> commandController.validateCommand(command4));
        assertEquals(throwable.getMessage(), CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MISTYPED_COMMAND.getMessage());
        assertEquals(throwable2.getMessage(), CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MISTYPED_COMMAND.getMessage());
        assertEquals(throwable3.getMessage(), CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MISTYPED_COMMAND.getMessage());
        assertEquals(throwable4.getMessage(), CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MISTYPED_COMMAND.getMessage());

    }

    @Test
    void givenMalformedItemCodeForCheck_thenThrows() {

        //given
        String command = "CHECk 11a 101 111";
        String command2 = "CHeck 1111 11 111";
        String command3 = "checK 123 123 1233 ";
        String command4 = "check 11 1 111 111";

        //then
        Throwable throwable = assertThrows(CustomRuntimeException.class, () -> commandController.validateCommand(command));
        Throwable throwable2 = assertThrows(CustomRuntimeException.class, () -> commandController.validateCommand(command2));
        Throwable throwable3 = assertThrows(CustomRuntimeException.class, () -> commandController.validateCommand(command3));
        Throwable throwable4 = assertThrows(CustomRuntimeException.class, () -> commandController.validateCommand(command4));
        assertEquals(throwable.getMessage(), CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MALFORMED_CODE.getMessage());
        assertEquals(throwable2.getMessage(), CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MALFORMED_CODE.getMessage());
        assertEquals(throwable3.getMessage(), CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MALFORMED_CODE.getMessage());
        assertEquals(throwable4.getMessage(), CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MALFORMED_CODE.getMessage());

    }

    @Test
    void givenMalformedShopCodeForClaim_thenThrows() {

        //given
        String command = "Claim 1212as 111 111 111";
        String command2 = "claim aws aws 111 111 111";
        String command3 = "claiM 121212 111 111 111";
        String command4 = "CLAIM abcdefgh 111 111 111'";

        //then
        Throwable throwable = assertThrows(CustomRuntimeException.class, () -> commandController.validateCommand(command));
        Throwable throwable2 = assertThrows(CustomRuntimeException.class, () -> commandController.validateCommand(command2));
        Throwable throwable3 = assertThrows(CustomRuntimeException.class, () -> commandController.validateCommand(command3));
        Throwable throwable4 = assertThrows(CustomRuntimeException.class, () -> commandController.validateCommand(command4));
        assertEquals(throwable.getMessage(), CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MALFORMED_CODE.getMessage());
        assertEquals(throwable2.getMessage(), CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MALFORMED_CODE.getMessage());
        assertEquals(throwable3.getMessage(), CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MALFORMED_CODE.getMessage());
        assertEquals(throwable4.getMessage(), CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MALFORMED_CODE.getMessage());

    }

    @Test
    void givenMalformedItemCodeForClaim_thenThrows() {

        //given
        String command = "claim abcdefg 111 1311 11";
        String command2 = "claim abcdefg 12a 111 111";
        String command3 = "claim abcdefg 111111111";
        String command4 = "claim abcdefg 111 111 1112";

        //then
        Throwable throwable = assertThrows(CustomRuntimeException.class, () -> commandController.validateCommand(command));
        Throwable throwable2 = assertThrows(CustomRuntimeException.class, () -> commandController.validateCommand(command2));
        Throwable throwable3 = assertThrows(CustomRuntimeException.class, () -> commandController.validateCommand(command3));
        Throwable throwable4 = assertThrows(CustomRuntimeException.class, () -> commandController.validateCommand(command4));
        assertEquals(throwable.getMessage(), CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MALFORMED_CODE.getMessage());
        assertEquals(throwable2.getMessage(), CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MALFORMED_CODE.getMessage());
        assertEquals(throwable3.getMessage(), CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MALFORMED_CODE.getMessage());
        assertEquals(throwable4.getMessage(), CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MALFORMED_CODE.getMessage());

    }
}