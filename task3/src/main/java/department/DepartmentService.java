package department;

import java.util.List;

public interface DepartmentService {

    String getDepartment(String name);

    List<Department> getAllDepartments();

    String post(Department department);

    void delete(String departmentName);

    String update(Department department);

    String relate(String superior, String subordinate);

}
