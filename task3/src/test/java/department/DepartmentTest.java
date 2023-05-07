package department;

import exception.CustomRuntimeException;
import exception.CustomRuntimeExceptionCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DepartmentTest {

    Department root = Department.of(1, "ROOT", true);
    Department a = Department.of(2, "A");
    Department b = Department.of(3, "B");
    Department c = Department.of(4, "C");
    Department d = Department.of(5, "D");

    //expected hierarchy
    // root(1) - a(2) - b (3) - c (4)
    //         - d(5)

    @BeforeEach
    void setup() {
        root.addSubordinate(a);
        root.addSubordinate(d);
        a.addSubordinate(b);
        b.addSubordinate(c);
    }

    @Test
    public void givenDepartments_whenAddingNewRelation_thenCombinedHeadCountOfRootSumsUp() {

        int expected = 15;
        int actual = root.getCombinedHeadCount();

        assertEquals(expected, actual);

    }

    @Test
    public void givenDepartments_whenAddingNewRelation_thenWellRelated() {

        List<Department> roots = List.of(a.getRoot(), b.getRoot(), c.getRoot(), d.getRoot());

        for (Department r : roots) {
            assertEquals(root, r);
        }
    }

    @Test
    public void givenAnyDepartment_whenTryingToGetHeadCount_thenRootHeadCountIsReturned() {

        int expected = 15;

        List<Integer> results = List.of(
                a.getTotalHeadCountOfDepartment(),
                b.getTotalHeadCountOfDepartment(),
                c.getTotalHeadCountOfDepartment(),
                d.getTotalHeadCountOfDepartment()
        );

        for (Integer result : results) {
            assertEquals(expected, result);
        }
    }

    @Test
    public void givenNewHierarchy_whenADepartmentRelocatedFromOther_thenUpdateHeadCount() {

        Department newRoot = Department.of(10, "NEWROOT", true);
        Department e = Department.of(6, "E");

        // new hierarchy with headcount 10 + 6 = 16
        newRoot.addSubordinate(e);

        // when
        // relocate E from newRoot
        root.addSubordinate(e);

        // headcount in root and E. 15 + E = 21
        int expected = 21;

        List<Integer> results = List.of(
                root.getTotalHeadCountOfDepartment(),
                a.getTotalHeadCountOfDepartment(),
                b.getTotalHeadCountOfDepartment(),
                c.getTotalHeadCountOfDepartment(),
                d.getTotalHeadCountOfDepartment(),
                e.getTotalHeadCountOfDepartment()
        );

        for (Integer result : results) {
            assertEquals(expected, result);
        }

        // headCount in newRoot 16 - E = 10
        int expected2 = 10;

        assertEquals(expected2, newRoot.getTotalHeadCountOfDepartment());
    }

    @Test
    public void givenNewDepartment_whenNameIsNotUppercaseAlphabet_thenThrows() {
        Throwable t = assertThrows(CustomRuntimeException.class, () -> Department.of(15, "abC"));
        assertEquals(CustomRuntimeExceptionCode.NOT_VALID_NAME.getMessage(), t.getMessage());
    }

    @Test
    public void givenNewDepartment_whenHeadCountIsNotBetween1And1000_thenThrows() {
        Throwable t = assertThrows(CustomRuntimeException.class, () -> Department.of(-15, "DEV"));
        assertEquals(CustomRuntimeExceptionCode.NOT_VALID_HEADCOUNT.getMessage(), t.getMessage());
        Throwable t2 = assertThrows(CustomRuntimeException.class, () -> Department.of(1010, "DEV"));
        assertEquals(CustomRuntimeExceptionCode.NOT_VALID_HEADCOUNT.getMessage(), t2.getMessage());
    }

    @Test
    public void givenNewDepartment_whenTryingToSubordinateRoot_thenThrows() {
        Department e = Department.of(10, "E");
        Throwable t = assertThrows(CustomRuntimeException.class, () -> e.addSubordinate(root));
        assertEquals(CustomRuntimeExceptionCode.ROOT_CANNOT_BE_SUBORDINATED.getMessage(), t.getMessage());
    }

    @Test
    void givenDepartmentAndNewHeadCount_whenUpdateCache_thenSucceed() {

        //given
        int headCount = 10;
        int expected1 = 24;

        //when
        // a has 1, updating to 10, so combined headcount will be 15 + 10 - 1 = 24
        root.updateCache(headCount);

        List<Integer> results1 = List.of(
                a.getTotalHeadCountOfDepartment(),
                b.getTotalHeadCountOfDepartment(),
                c.getTotalHeadCountOfDepartment(),
                d.getTotalHeadCountOfDepartment()
        );

        //then
        for (Integer result : results1) {
            assertEquals(expected1, result);
        }

        //given
        int expected2 = 30;

        //when
        // c has 4 headcount, updating by 10 will result in the increment of 6 in total. 24 + 10 - 4 = 30
        c.updateCache(headCount);

        List<Integer> results2 = List.of(
                a.getTotalHeadCountOfDepartment(),
                b.getTotalHeadCountOfDepartment(),
                c.getTotalHeadCountOfDepartment(),
                d.getTotalHeadCountOfDepartment()
        );

        //then
        for (Integer result2 : results2) {
            assertEquals(expected2, result2);
        }
    }

    @Test
    void departmentCacheUpdateTest() {

        calculateHeadcount(root);
        calculateHeadcount(a);
        calculateHeadcount(b);
        calculateHeadcount(c);
        calculateHeadcount(d);
    }

    public static int calculateHeadcount(Department department) {
        int headcount = department.getHeadCount();

        if (department.getCombinedHeadCount() != null) {
            return department.getCombinedHeadCount();
        }

        List<Department> subs = department.getSubordinates();

        for (Department sub : subs) {
            headcount += calculateHeadcount(sub);
        }

        department.setCombinedHeadCount(headcount);

        return headcount;
    }
}
