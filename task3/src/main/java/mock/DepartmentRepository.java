package mock;

import department.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository {

    void save(Department department);

    List<Department> findAll();

    Optional<Department> findOneByName(String name);

}
