package command;

public enum Command {

    CHECK("CHECK"),
    HELP("HELP"),
    CLAIM("CLAIM"),
    EXIT("EXIT");

    final String desc;

    Command(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

}
