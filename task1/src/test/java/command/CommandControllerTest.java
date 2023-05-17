package command;

import exception.CustomRuntimeException;
import exception.CustomRuntimeExceptionCode;
import test_util.CommandTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CommandControllerTest {

    CommandController commandController;

    @BeforeEach
    public void setup() {
        CommandEnumWrapperFactory commandEnumWrapperFactory = new CommandEnumWrapperFactoryImpl(CommandTestUtils.MockEnumCommand.class);
        CommandEnumWrapper commandWrapper = commandEnumWrapperFactory.createCommandEnumWrapper();
        commandController = new CommandController(commandWrapper);
    }

    @Test
    void givenBlankString_thenThrows() {

        //given
        String command = "";
        String command2 = "    ";

        //then
        Throwable throwable = assertThrows(CustomRuntimeException.class, () -> commandController.process(command));
        Throwable throwable2 = assertThrows(CustomRuntimeException.class, () -> commandController.process(command2));
        assertEquals(CustomRuntimeExceptionCode.EMPTY_COMMAND.getMessage(), throwable.getMessage());
        assertEquals(CustomRuntimeExceptionCode.EMPTY_COMMAND.getMessage(), throwable2.getMessage());

    }

    @Test
    void givenMistypedCommand_thenThrows() {

        //given
        String command = "checks 011 0111 01";
        String command2 = "checks 111111101";
        String command3 = "cliam asdw 111 111 222";
        String command4 = "hlep";

        //then
        Throwable throwable = assertThrows(CustomRuntimeException.class, () -> commandController.process(command));
        Throwable throwable2 = assertThrows(CustomRuntimeException.class, () -> commandController.process(command2));
        Throwable throwable3 = assertThrows(CustomRuntimeException.class, () -> commandController.process(command3));
        Throwable throwable4 = assertThrows(CustomRuntimeException.class, () -> commandController.process(command4));
        assertEquals(CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MISTYPED_COMMAND.getMessage(), throwable.getMessage());
        assertEquals(CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MISTYPED_COMMAND.getMessage(), throwable2.getMessage());
        assertEquals(CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MISTYPED_COMMAND.getMessage(), throwable3.getMessage());
        assertEquals(CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MISTYPED_COMMAND.getMessage(), throwable4.getMessage());

    }

    @Test
    void givenMalformedItemCodeForCheck_thenThrows() {

        //given
        String command = "CHECk 11a 101 111";
        String command2 = "CHeck 1111 11 111";
        String command3 = "checK 123 123 1233 ";
        String command4 = "check 11 1 111 111";

        //then
        Throwable throwable = assertThrows(CustomRuntimeException.class, () -> commandController.process(command));
        Throwable throwable2 = assertThrows(CustomRuntimeException.class, () -> commandController.process(command2));
        Throwable throwable3 = assertThrows(CustomRuntimeException.class, () -> commandController.process(command3));
        Throwable throwable4 = assertThrows(CustomRuntimeException.class, () -> commandController.process(command4));
        assertEquals(CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MALFORMED_CODE.getMessage(), throwable.getMessage());
        assertEquals(CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MALFORMED_CODE.getMessage(), throwable2.getMessage());
        assertEquals(CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MALFORMED_CODE.getMessage(), throwable3.getMessage());
        assertEquals(CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MALFORMED_CODE.getMessage(), throwable4.getMessage());

    }

    @Test
    void givenMalformedShopCodeForClaim_thenThrows() {

        //given
        String command = "Claim 1212as 111 111 111";
        String command2 = "claim aws aws 111 111 111";
        String command3 = "claiM 121212 111 111 111";
        String command4 = "CLAIM abcdefgh 111 111 111'";

        //then
        Throwable throwable = assertThrows(CustomRuntimeException.class, () -> commandController.process(command));
        Throwable throwable2 = assertThrows(CustomRuntimeException.class, () -> commandController.process(command2));
        Throwable throwable3 = assertThrows(CustomRuntimeException.class, () -> commandController.process(command3));
        Throwable throwable4 = assertThrows(CustomRuntimeException.class, () -> commandController.process(command4));
        assertEquals(CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MALFORMED_CODE.getMessage(), throwable.getMessage());
        assertEquals(CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MALFORMED_CODE.getMessage(), throwable2.getMessage());
        assertEquals(CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MALFORMED_CODE.getMessage(), throwable3.getMessage());
        assertEquals(CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MALFORMED_CODE.getMessage(), throwable4.getMessage());

    }

    @Test
    void givenMalformedItemCodeForClaim_thenThrows() {

        //given
        String command = "claim abcdefg 111 1311 11";
        String command2 = "claim abcdefg 12a 111 111";
        String command3 = "claim abcdefg 111111111";
        String command4 = "claim abcdefg 111 111 1112";

        //then
        Throwable throwable = assertThrows(CustomRuntimeException.class, () -> commandController.process(command));
        Throwable throwable2 = assertThrows(CustomRuntimeException.class, () -> commandController.process(command2));
        Throwable throwable3 = assertThrows(CustomRuntimeException.class, () -> commandController.process(command3));
        Throwable throwable4 = assertThrows(CustomRuntimeException.class, () -> commandController.process(command4));
        assertEquals(CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MALFORMED_CODE.getMessage(), throwable.getMessage());
        assertEquals(CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MALFORMED_CODE.getMessage(), throwable2.getMessage());
        assertEquals(CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MALFORMED_CODE.getMessage(), throwable3.getMessage());
        assertEquals(CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MALFORMED_CODE.getMessage(), throwable4.getMessage());

    }
}