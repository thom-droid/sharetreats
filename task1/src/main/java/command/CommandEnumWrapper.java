package command;

/**
 * 이 인터페이스는  {@link Command} 의 구현체인 {@link command.commands.Check}, {@link command.commands.Claim}
 * 과 같은 클래스의 인스턴스를 가지고 있는 enum 타입을 {@link CommandController}에 주입하기 위해 정의된 인터페이스입니다.
 * 기본 enum 은 {@link CommandEnum}입니다. {@code findCommand()}는 enum 원소 중에 입력값과 같은 커맨드가 있는지 확인하여 리턴합니다.
 */
public interface CommandEnumWrapper {

    Command findCommand(String command);

}
