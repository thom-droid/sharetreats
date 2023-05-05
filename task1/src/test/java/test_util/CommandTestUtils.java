package test_util;

import code.CodeGeneratorConfigGetter;
import command.*;
import command.commands.Check;
import command.commands.Claim;
import command.commands.Exit;
import command.commands.Help;
import exception.CustomRuntimeException;
import exception.CustomRuntimeExceptionCode;
import mock.MockupData;
import voucher.VoucherService;
import voucher.VoucherServiceImpl;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Locale;
import java.util.function.Supplier;

public class CommandTestUtils {

    public enum MockEnumCommand implements CommandEnumWrapper {

        CHECK(() -> {
            try {
                return MockCommandInitiator.getCheck();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }),

        CLAIM(() -> {
            try {
                return MockCommandInitiator.getClaim();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }),

        HELP(() -> {
            try {
                return MockCommandInitiator.getHelp();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }),

        EXIT(() -> {
            try {
                return MockCommandInitiator.getExit();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }),

        ;

        final Supplier<Command> supplier;
        final Command command;

        MockEnumCommand(Supplier<Command> supplier) {
            this.supplier = supplier;
            this.command = supplier.get();
        }

        @Override
        public String process(String command) {
            return supplier.get().process(command);
        }

        @Override
        public Command findCommand(String command) {
            MockEnumCommand enumCommand = Arrays.stream(this.getClass().getEnumConstants())
                    .filter(e -> e.name().equals(command.toUpperCase(Locale.ROOT)))
                    .findFirst()
                    .orElseThrow(() -> new CustomRuntimeException(CustomRuntimeExceptionCode.ILLEGAL_ARGUMENT_MISTYPED_COMMAND));

            return enumCommand.getCommand();
        }

        public Command getCommand() {
            return command;
        }
    }

    public static class MockCommandInitiator {

        private static final MockupData MOCKUP_DATA = new TestMockData();
        private static final CodeGeneratorConfigGetter CODE_GENERATOR_CONFIG_GETTER = (CodeGeneratorConfigGetter) MOCKUP_DATA;
        private static final VoucherService VOUCHER_SERVICE = VoucherServiceImpl.getInstance(MOCKUP_DATA);

        public static MockupData getMockupData() {
            return MOCKUP_DATA;
        }

        public static VoucherService getVoucherService() {
            return VOUCHER_SERVICE;
        }

        public static Check getCheck() throws Exception {
            Constructor<Check> constructor = Check.class.getDeclaredConstructor(CodeGeneratorConfigGetter.class, VoucherService.class);
            constructor.setAccessible(true);

            return constructor.newInstance(CODE_GENERATOR_CONFIG_GETTER, VOUCHER_SERVICE);
        }

        public static Claim getClaim() throws Exception {
            Constructor<Claim> constructor = Claim.class.getDeclaredConstructor(CodeGeneratorConfigGetter.class, VoucherService.class);
            constructor.setAccessible(true);

            return constructor.newInstance(CODE_GENERATOR_CONFIG_GETTER, VOUCHER_SERVICE);
        }

        public static Help getHelp() throws Exception {
            Constructor<Help> constructor = Help.class.getDeclaredConstructor(CodeGeneratorConfigGetter.class, VoucherService.class);
            constructor.setAccessible(true);

            return constructor.newInstance(CODE_GENERATOR_CONFIG_GETTER, VOUCHER_SERVICE);
        }

        public static Exit getExit() throws Exception {
            Constructor<Exit> constructor = Exit.class.getDeclaredConstructor(CodeGeneratorConfigGetter.class, VoucherService.class);
            constructor.setAccessible(true);

            return constructor.newInstance(CODE_GENERATOR_CONFIG_GETTER, VOUCHER_SERVICE);
        }
    }
}
