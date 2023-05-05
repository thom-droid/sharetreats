package department;

import department.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository {

    Department save(Department department);

    List<Department> findAll();

    Optional<Department> findOneByName(String name);

    void delete(Department department);

}
