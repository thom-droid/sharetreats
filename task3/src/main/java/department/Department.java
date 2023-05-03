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
    private boolean isRoot;
    private boolean visited;

    public Department(int headCount, String name, boolean isRoot) {
        this.headCount = headCount;
        this.name = name;
        this.subordinates = new LinkedList<>();
        this.isRoot = isRoot;
        if (isRoot) {
            this.combinedHeadCount = headCount;
        }
    }

    public static Department of(int headCount, String name) {
        return new Department(headCount, name, false);
    }

    public static Department of(int headCount, String name, boolean isRoot) {
        return new Department(headCount, name, isRoot);
    }

    public void setAsRoot() {
        this.isRoot = true;
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

    public Department getSuperior() {
        return superior;
    }

    public Department getRoot() {
        return root;
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
                " headCount=" + headCount +
                ", name=" + name +
                ", subordinates=" + subordinates +
                ", superior=" + superior +
                ", root =" + this.root +
                ", totalHeadCount=" + combinedHeadCount +
                " }";
    }

    private Department findRoot() {

        if (isThisNotRoot()) {
            return findRootOf(this);
        }

        return this;
    }

    private boolean isThisNotRoot() {
        return !isRoot;
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

    private int calculateHeadCountAndSetRoot(Department department, Department newRoot) {
        department.markVisited();
        int count = department.headCount;

        List<Department> subordinates = department.getSubordinates();

        for (Department s : subordinates) {
            if (!s.visited) {
                count += calculateHeadCountAndSetRoot(s, newRoot);
            }
        }

        department.root = newRoot;

        return count;
    }

    private void relateTo(Department subordinate) {
        this.subordinates.add(subordinate);
        subordinate.superior = this;
    }

    private Department findRootOf(Department department) {
        if (department == null)
            throw new NoSuchElementException("상위 부서가 설정되어 있지 않습니다");

        if (!department.isRoot)
            department = findRootOf(department.superior);

        return department;
    }

}
