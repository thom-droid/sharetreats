package department;

import java.util.List;

public interface DepartmentService {

    Department getDepartment(String name);

    List<Department> getAllDepartments();

    String post(Department department);

    void delete(Department department);

    void update(String departmentName, int headCount);

}
