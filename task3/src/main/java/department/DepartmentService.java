package department;

import java.util.List;

public interface DepartmentService {

    String getDepartment(String name);

    List<Department> getAllDepartments();

    String post(Department department);

    void delete(String departmentName);

    void update(String departmentName, int headcount);

    String relate(String superior, String subordinate);

}
