package department;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class Department {

    private final int headCount;
    private Integer combinedHeadCount;
    private final String name;
    private final List<Department> subordinates;
    private Department superior;
    private Department root;
    private boolean visited;

    public Department(int headCount, String name, boolean isRoot) {
        this.headCount = headCount;
        this.name = name;
        this.subordinates = new LinkedList<>();
        if (isRoot) {
            setAsRoot();
        }
    }

    public static Department of(int headCount, String name) {
        return new Department(headCount, name, false);
    }

    public static Department of(int headCount, String name, boolean isRoot) {
        return new Department(headCount, name, isRoot);
    }

    public void setAsRoot() {
        this.superior = this;
        this.root = this;
    }

    public void addSubordinate(Department subordinate) {
        if (subordinate == null) {
            return;
        }

        if (alreadyRelatedTo(subordinate)) {
            return;
        }

        Department root = findRoot();
        updateSubordinatesAndCache(subordinate, root);
        relateTo(subordinate);
    }

    public Integer getCombinedHeadCount() {
        return combinedHeadCount;
    }

    public String getName() {
        return name;
    }

    public List<Department> getSubordinates() {
        return subordinates;
    }

    public void markVisited() {
        this.visited = true;
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

    @Override
    public String toString() {
        return "Department{" +
                "people=" + headCount +
                ", name='" + name + '\'' +
                ", subordinates=" + subordinates +
                ", superior=" + superior.getName() +
                ", totalPeopleUpToThis=" + combinedHeadCount +
                ", visited=" + visited +
                '}';
    }

    private Department findRoot() {

        Department root;
        if (this.root == null) {
            throw new NoSuchElementException("최상위 부서가 설정되어 있지 않습니다. 최상위 부서를 설정해주세요.");
        } else if (isThisNotRoot()) {
            root = findRootOf(this);
        } else {
            root = this.root;
        }

        return root;
    }

    private boolean isThisNotRoot() {
        return this.root != this;
    }

    private boolean alreadyRelatedTo(Department subordinate) {
        return this.subordinates.contains(subordinate) || subordinate.superior == this;
    }

    private void updateSubordinatesAndCache(Department subordinate, Department newRoot) {
        Department oldRoot = subordinate.root;
        int subHeadCount = calculateHeadCountAndSetRoot(subordinate, newRoot);

        // 기존에 연결된 최상위 부서의 캐시에서 이동된 부서 인원만큼 차감
        if (oldRoot != null) {
            oldRoot.combinedHeadCount -= subHeadCount;
        }

        newRoot.combinedHeadCount += subHeadCount;
    }

    private void relateTo(Department subordinate) {
        this.subordinates.add(subordinate);
        subordinate.superior = this;
    }

    private int calculateHeadCountAndSetRoot(Department department, Department root) {
        department.markVisited();
        int count = department.headCount;

        List<Department> subordinates = department.getSubordinates();

        for (Department s : subordinates) {
            if (!s.visited) {
                count += calculateHeadCountAndSetRoot(s, root);
            }
        }

        department.root = root;

        return count;
    }

    private Department findRootOf(Department department) {
        if (department.superior != department) {
            department = findRootOf(department.superior);
        }
        return department;
    }

}
