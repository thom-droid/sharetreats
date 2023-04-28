package department;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Department {

    private final int people;
    private final String name;
    private final List<Department> subordinates;
    private Department superior;
    private Integer totalPeopleUpToThis;
    private boolean visited;

    public Department(int people, String name, Department superior) {
        this.people = people;
        this.name = name;
        this.superior = superior;
        this.subordinates = new LinkedList<>();
    }

    public void setAsRoot() {
        this.superior = this;
    }

    public int getPeople() {
        return people;
    }

    public Integer getTotalPeopleUpToThis() {
        return totalPeopleUpToThis;
    }

    public String getName() {
        return name;
    }

    public Department getSuperior() {
        return superior;
    }

    public List<Department> getSubordinates() {
        return subordinates;
    }

    public void addSubordinate(Department subordinate) {
        if (subordinate == null) {
            return;
        }

        this.subordinates.add(subordinate);

        if(subordinate.totalPeopleUpToThis == null) {
            subordinate.totalPeopleUpToThis = countAllPeopleIn(subordinate);
        }

        if (this.totalPeopleUpToThis == null) {
            this.totalPeopleUpToThis = subordinate.totalPeopleUpToThis + this.people;
        } else {
            this.totalPeopleUpToThis += subordinate.totalPeopleUpToThis;
        }

        if (subordinate.superior != this) {
            subordinate.superior.totalPeopleUpToThis -= subordinate.totalPeopleUpToThis;
            subordinate.superior = this;
        }
    }

    public void markVisited() {
        this.visited = true;
    }

    public int countAllPeopleIn(Department department) {
        department.markVisited();
        int count = department.people;

        List<Department> subordinates = department.getSubordinates();

        for (Department s : subordinates) {
            if (!s.visited) {
                count += countAllPeopleIn(s);
            }
        }

        return count;
    }

    public static Department findRoot(Department department) {
        if (department.superior != department) {
            department = findRoot(department.superior);
        }
        return department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
