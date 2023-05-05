package department;

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

        assertThrows(IllegalArgumentException.class, () -> Department.of(15, "abC"));

    }

    @Test
    public void givenNewDepartment_whenHeadCountIsNotBetween1And1000_thenThrows() {
        assertThrows(IllegalArgumentException.class, () -> Department.of(0, "DEV"));
    }

    @Test
    public void givenNewDepartment_whenTryingToSubordinateRoot_thenThrows() {

        Department e = Department.of(10, "E");
        assertThrows(IllegalArgumentException.class, () -> e.addSubordinate(root));

    }

}
