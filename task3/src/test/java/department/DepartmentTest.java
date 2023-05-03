package department;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentTest {

    @Test
    public void givenDepartments_whenAddingNewRelation_thenCombinedHeadCountOfRootSumsUp() {

        //given
        Department root = Department.of(1, "ROOT", true);
        Department a = Department.of(2, "A");
        Department b = Department.of(3, "B");
        Department c = Department.of(4, "C");
        Department d = Department.of(5, "D");

        root.addSubordinate(a);
        root.addSubordinate(d);
        a.addSubordinate(b);
        b.addSubordinate(c);

        //expected hierarchy
        // root(1) - a(2) - b (3) - c (4)
        //         - d(5)

        int expected = 15;
        int actual = root.getCombinedHeadCount();

        assertEquals(expected, actual);

    }

    @Test
    public void givenDepartments_whenAddingNewRelation_thenWellRelated() {

        //given
        Department root = Department.of(1, "ROOT", true);
        Department a = Department.of(2, "A");
        Department b = Department.of(3, "B");
        Department c = Department.of(4, "C");
        Department d = Department.of(5, "D");

        root.addSubordinate(a);
        root.addSubordinate(d);
        a.addSubordinate(b);
        b.addSubordinate(c);

        //expected hierarchy
        // root(1) - a(2) - b (3) - c (4)
        //         - d(5)

        List<Department> roots = List.of(a.getRoot(), b.getRoot(), c.getRoot(), d.getRoot());

        for (Department r : roots) {
            assertEquals(root, r);
        }


    }

}
