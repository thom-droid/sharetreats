package department;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentTest {

    @Test
    void countAllPeopleIn() {

        Department d1 = new Department(1, "A", null);
        Department d2 = new Department(2, "B", d1);
        Department d3 = new Department(3, "C", d1);
        Department d4 = new Department(4, "D", d2);
        Department d5 = new Department(5, "E", d3);

        d1.getSubordinates().add(d2);
        d1.getSubordinates().add(d3);
        d2.getSubordinates().add(d4);
        d4.getSubordinates().add(d5);

        int expected = 15;
        int actual = d1.countAllPeopleIn(d1);

        assertEquals(expected, actual);

    }

    @Test
    void givenDepartment_whenAddingSubordinates_thenAllPeopleInSubordinatesSumsUp() {

        //given, d1 target
        Department d1 = new Department(1, "A", null);
        Department d2 = new Department(2, "B", d1);
        Department d3 = new Department(3, "C", d2);
        Department d4 = new Department(4, "D", d2);
        Department d5 = new Department(5, "E", d3);

        int expected1 = 15;
        int expected2 = 14;
        int expected3 = 8;
        int expected4 = 4;
        int expected5 = 5;

        //when
        d3.addSubordinate(d5);
        d2.addSubordinate(d3);
        d2.addSubordinate(d4);
        d1.addSubordinate(d2);

        //then
        int actual1 = d1.getTotalPeopleUpToThis();
        int actual2 = d2.getTotalPeopleUpToThis();
        int actual3 = d3.getTotalPeopleUpToThis();
        int actual4 = d4.getTotalPeopleUpToThis();
        int actual5 = d5.getTotalPeopleUpToThis();

        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
        assertEquals(expected3, actual3);
        assertEquals(expected4, actual4);
        assertEquals(expected5, actual5);
    }

    @Test
    void givenNewSuperiorDepartment_whenSubDepartmentChangesItsSuperior_thenTotalPeopleCountChanges() {

        //given
        Department d1 = new Department(1, "A", null);
        Department d2 = new Department(2, "B", d1);
        Department d3 = new Department(3, "C", d2);
        Department d4 = new Department(4, "D", d2);
        Department d5 = new Department(5, "E", d3);

        // d1(15) - d2(14) - d3(8) - d5(5)
        //                 - d4(4)
        d3.addSubordinate(d5);
        d2.addSubordinate(d3);
        d2.addSubordinate(d4);
        d1.addSubordinate(d2);

        //when
        Department d6 = new Department(10, "G", null);

        // reference to superior of d2 (d1) is changed to d6
        // so total people of d1 and its subordinates will be subtracted as much of 14 (15 - 1)
        // and d6 will gain those number of people making its total people count to 10 + 14 = 24.
        d6.addSubordinate(d2);

        //then
        int expected1 = 1;
        int expected2 = 24;

        int actual1 = d1.getTotalPeopleUpToThis();
        int actual2 = d6.getTotalPeopleUpToThis();

        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);

    }

    @Test
    void findingRoot() {

        //given
        Department d1 = new Department(1, "A", null);
        Department d2 = new Department(2, "B", d1);
        Department d3 = new Department(3, "C", d2);
        Department d4 = new Department(4, "D", d2);
        Department d5 = new Department(5, "E", d3);

        d1.setAsRoot();

        d3.addSubordinate(d5);
        d2.addSubordinate(d3);
        d2.addSubordinate(d4);
        d1.addSubordinate(d2);

        String rootName = "A";
        Department root = Department.findRoot(d5);
        Department root2 = Department.findRoot(d4);
        Department root3 = Department.findRoot(d3);
        Department root4 = Department.findRoot(d2);

        assertEquals(root.getName(), rootName);
        assertEquals(root, root2);
        assertEquals(root2, root3);
        assertEquals(root3, root4);

    }

}