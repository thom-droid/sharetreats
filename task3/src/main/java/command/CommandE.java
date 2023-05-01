package command;

public enum CommandE {

    INSERT("새로운 부서를 입력합니다."),
    FIND("부서명을 입력하여 부서의 정보를 얻어옵니다."),
    DETAIL("부서명을 입력하여 상세 정보를 얻어옵니다.");

    final String desc;

    CommandE(String desc) {
        this.desc = desc;
    }
}
