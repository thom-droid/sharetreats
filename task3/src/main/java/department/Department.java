package department;

import exception.CustomRuntimeException;
import exception.CustomRuntimeExceptionCode;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Department {

    private static final String MESSAGE_WITHOUT_ROOT = "최상위 부서가 설정되어 있지 않아 현재 부서의 상위 부서 중 최고 부서의 정보가 표시됩니다.";
    private static final String REGEX = "[A-Z]+";

    private int headCount;
    private int combinedHeadCount;
    private final String name;
    private final List<Department> subordinates;
    private Department superior;
    private Department root;
    private boolean isRoot;

    public Department(int headCount, String name, boolean isRoot) {
        this.headCount = headCount;
        this.name = name;
        this.subordinates = new LinkedList<>();
        this.isRoot = isRoot;
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

    public void add(Department subordinate) {
        // 추가하려는 부서가 null 이면 리턴
        if (subordinate == null) {
            return;
        }

        // 추가하려는 부서가 최상위 부서면 예외 처리
        if (subordinate.isThisRoot()) {
            throw new CustomRuntimeException(CustomRuntimeExceptionCode.ROOT_CANNOT_BE_SUBORDINATED);
        }

        // 이미 관계가 설정된 부서면 리턴
        if (alreadyRelatedTo(subordinate)) {
            return;
        }

        // 캐시 업데이트를 위해 현재 부서의 루트(최상위)를 찾음. 없는 경우 가장 상위의 부서를 리턴
        // 새로 추가하는 부서의 사람 수를 현재 부서에 더해 캐시를 업데이트. 루트가 존재하는 경우 루트도 하위 부서에 설정
        Department root = findRootOrHighest();
        updateSubordinatesAndCache(subordinate, root);
    }

    public int getCombinedHeadCount() {
        return combinedHeadCount;
    }

    public String getName() {
        return name;
    }

    public List<Department> getSubordinates() {
        return subordinates;
    }

    public Department getRoot() {
        return root;
    }

    public void updateHeadcount(int headCount) {
        this.headCount = headCount;
        updateHeadcount();
    }

    public int getTotalHeadCountOfDepartment() {
        if (isThisRoot()) {
            return this.combinedHeadCount;
        }
        Department highest = findRootOrHighest();
        if (highest == null) {
            return this.combinedHeadCount;
        }
        return highest.combinedHeadCount;
    }

    public boolean isThisRoot() {
        return isRoot;
    }

    public void throwDuplicatedNameException() {
        throw new CustomRuntimeException(CustomRuntimeExceptionCode.DUPLICATED_NAME);
    }

    public String relationToString() {
        if (this.root == null) {
            Department highest = findRootOrHighest();
            return MESSAGE_WITHOUT_ROOT + "\n" +
                    "현재부서: [ " + this.getName() + " ], " +
                    "상위부서: [ " + highest.getName()+ " ], " +
                    "총 인원: [ " + highest.combinedHeadCount +" ]";
        }

        return root.getName() + ", " + root.getCombinedHeadCount();
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

    private Department findRootOrHighest() {
        if (!isThisRoot()) {
            return findRootOrHighest(this, null);
        }
        return this;
    }

    private boolean alreadyRelatedTo(Department subordinate) {
        return this.subordinates.contains(subordinate) || subordinate.superior == this;
    }

    private void updateSubordinatesAndCache(Department subordinate, Department newRoot) {

        updateCombinedHeadCountFrom(subordinate);
        updateRelationOf(subordinate);

        // 현재 부서의 최상위 부서가 있다면 추가하려는 하위 부서의 모든 하위 부서에도 최상위 부서를 설정하고 캐시 업데이트
        if(newRoot != null) {
            setRoot(subordinate, newRoot);
        }

        // 현재 부서의 최상위부서까지 부서 인원 수 업데이트
        if (this.superior != null) {
            Department sup = this.superior;
            sup.updateHeadcount();
        }
    }

    private void updateCombinedHeadCountFrom(Department subordinate) {
        // cache 설정
        if (this.combinedHeadCount == 0) {
            this.combinedHeadCount = headCount;
        }

        int subHeadcount = subordinate.calculateHeadCount();

        if (this.combinedHeadCount + subHeadcount > 1000) {
            throw new CustomRuntimeException(CustomRuntimeExceptionCode.NOT_VALID_HEADCOUNT);
        }

        this.combinedHeadCount += subHeadcount;
    }

    private void updateRelationOf(Department subordinate) {
        // 추가하려는 하위 부서가 최상위 루트를 가지고 있었다면 그 상위 부서로부터 제거하고, 그 상위 부서의 캐시 업데이트
        Department oldSup = subordinate.superior;
        if (oldSup != null) {
            oldSup.remove(subordinate);
        }

        // 상위 부서 - 하위 부서 관계 설정
        relateTo(subordinate);
    }

    private void remove(Department subordinate) {
        if (this.getSubordinates().remove(subordinate)) {
            updateHeadcount();
        }
    }

    private void updateHeadcount() {
        int count = headCount;

        for (Department subordinate : subordinates) {
            count += subordinate.calculateHeadCount();
        }

        combinedHeadCount = count;
        Department sup = this.superior;

        if (sup != null) {
            sup.updateHeadcount();
        }
    }

    private int calculateHeadCount() {
        int count = headCount;

        // 캐시된 부서 총원이 있다면 바로 리턴
        if (combinedHeadCount != 0) return combinedHeadCount;

        List<Department> subordinates = getSubordinates();

        for (Department s : subordinates) count += s.calculateHeadCount();

        // 하위 부서가 다른 하위 부서를 가지는 경우를 탐색 시간을 줄이기 위해 캐싱
        combinedHeadCount = count;

        return count;
    }

    private void setRoot(Department subordinate, Department root) {
        if (subordinate.root == root) {
            return;
        }

        subordinate.root = root;

        for (Department sub : subordinate.getSubordinates()) {
            setRoot(sub, root);
        }
    }

    private void relateTo(Department subordinate) {
        this.subordinates.add(subordinate);
        subordinate.superior = this;
    }

    private Department findRootOrHighest(Department department, Department prev) {
        if (department == null)
            return prev;
        if (!department.isRoot)
            department = findRootOrHighest(department.superior, department);

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
