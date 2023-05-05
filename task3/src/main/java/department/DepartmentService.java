package department;

import java.util.List;

public interface DepartmentService {

    Department getDepartment(String name);

    List<Department> getAllDepartments();

    String post(Department department);

    void delete(Department department);

}
