package command;

/**
 * {@link CommandEnumWrapper}를 생성하기 위한 구현체입니다. 생성자를 통해 커맨드 구현체를 담고 있는 enum 타입을
 * 주입받고, 주입받은 enum 으로부터 {@link CommandEnumWrapper} 인스턴스를 생성합니다.
 * */
public class CommandEnumWrapperFactoryImpl implements CommandEnumWrapperFactory {

    private final Class<? extends CommandEnumWrapper> commandEnum;

    public CommandEnumWrapperFactoryImpl(Class<? extends CommandEnumWrapper> commandEnum) {
        this.commandEnum = commandEnum;
    }

    /**
     * <p>
     * {@link Command}의 구현체가 인스턴스화되고, enum의 원소들이 이를 참조하게 됩니다.
     * </p>
     *
     * <p>예를 들어 이 프로그램 기본 enum 타입인 {@link CommandEnum}이 이 클래스에 주입되고 이 메서드가 호출될 때,
     * 주입받은 {@code commandEnum}으로부터 {@code Class.getEnumConstants()}를 호출합니다. 이 때 {@code Check::getInstance}
     * {@code Claim::getInstance}와 같은 메서드가 순차적으로 호출되면서 Command 구현체가 생성되고, 이 인스턴스는
     * {@code CommandEnum.CHECK} 와 같이 enum의 원소들이 참조하게 됩니다.
     * </p>
     *
     * @throws IllegalArgumentException 주어진 enum에 원소가 없을 때
     * */

    @Override
    public CommandEnumWrapper createCommandEnumWrapper() {

        CommandEnumWrapper[] commands = commandEnum.getEnumConstants();

        if (commands.length == 0) {
            throw new IllegalArgumentException(commandEnum.getName() + "구현 내용이 없습니다.");
        }

        return commands[0];
    }

}
