package item;

import java.time.LocalDateTime;
import java.util.Objects;

/** 경품을 의미하는 클래스입니다. 생성자를 통해서만 초기화할 수 있도록하여 불변한 인스턴스가 되도록 작성헀습니다. */

public final class Item {

    private final String name;
    private final Grade grade;
    private final LocalDateTime dueDate;

    private Item(String name, Grade grade, LocalDateTime dueDate) {
        this.name = name;
        this.grade = grade;
        this.dueDate = dueDate;
    }

    public static Item of( String name, Grade grade, LocalDateTime dueDate) {
        return new Item(name, grade, dueDate);
    }

    public enum Grade {

        A,
        B

    }

    public String toStringWhenSucceeds() {
        return "당첨! " +
                name + ", " + grade + ", " + dueDate;
    }

    @Override
    public String toString() {
        return name + ", " + grade + ", " + dueDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public String getName() {
        return name;
    }

    public Grade getGrade() {
        return grade;
    }

}
