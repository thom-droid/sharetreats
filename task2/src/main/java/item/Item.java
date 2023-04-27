package item;

import java.time.LocalDateTime;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(name, item.name) && grade == item.grade && Objects.equals(dueDate, item.dueDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, grade, dueDate);
    }
}
