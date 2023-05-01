package department;

import java.util.List;

public interface DepartmentService {

    Department getDepartment(String name);

    Department getTotalDepartment(String departmentName);

    List<Department> getAllDepartments();

    Department saveDepartment(Department department);


}
