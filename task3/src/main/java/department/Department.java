package department;

import exception.CustomRuntimeException;
import exception.CustomRuntimeExceptionCode;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Department {

    private static final String REGEX = "[A-Z]+";

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
        validate(headCount, name);
        return new Department(headCount, name, false);
    }

    public static Department of(int headCount, String name, boolean isRoot) {
        validate(headCount, name);
        return new Department(headCount, name, isRoot);
    }

    public void setAsRoot() {
        this.isRoot = true;
    }

    public void addSubordinate(Department subordinate) {
        if (subordinate == null) {
            return;
        }

        if (subordinate.isThisRoot()) {
            throw new CustomRuntimeException(CustomRuntimeExceptionCode.ROOT_CANNOT_BE_SUBORDINATED);
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

    public int getTotalHeadCountOfDepartment() {
        if (isThisRoot()) {
            return this.combinedHeadCount;
        } else if (this.root == null) {
            throw new CustomRuntimeException(CustomRuntimeExceptionCode.NO_ROOT_IS_SET);
        }
        return this.root.getCombinedHeadCount();
    }

    public boolean isThisRoot() {
        return isRoot;
    }

    public void throwDuplicatedNameException(){
        throw new CustomRuntimeException(CustomRuntimeExceptionCode.DUPLICATED_NAME);
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

        if (!isThisRoot()) {
            return findRootOf(this);
        }

        return this;
    }

    private boolean alreadyRelatedTo(Department subordinate) {
        return this.subordinates.contains(subordinate) || subordinate.superior == this;
    }

    private void updateSubordinatesAndCache(Department subordinate, Department newRoot) {
        Department oldRoot = subordinate.root;
        int subHeadCount = calculateHeadCountAndSetRoot(subordinate, newRoot);

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
            throw new CustomRuntimeException(CustomRuntimeExceptionCode.NO_SUPERIOR_IS_SET);
        if (!department.isRoot)
            department = findRootOf(department.superior);

        return department;
    }

    private static void validate(int headCount, String departmentName) {
        validate(departmentName);
        validate(headCount);
    }

    private static void validate(String departmentName) {
        if (!departmentName.matches(REGEX))
            throw new CustomRuntimeException(CustomRuntimeExceptionCode.NOT_VALID_NAME);
    }

    private static void validate(int headCount) {
        if (headCount <= 0 || headCount > 1000)
            throw new CustomRuntimeException(CustomRuntimeExceptionCode.NOT_VALID_HEADCOUNT);
    }


}
