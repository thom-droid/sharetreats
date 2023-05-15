package command;

/** 명령어를 표현하는 인터페이스입니다. 각 명령어마다 추상 메서드인 {@code process(String command)} 를 구현하여
 * 수행 내용을 정의할 수 있습니다. 현재 정의된 기본 명령어는 {@link command.commands.Check}, {@link command.commands.Claim},
 * {@link command.commands.Exit}, {@link command.commands.Help} 입니다.
 * */

public interface Command {

    String process(String command);

}
