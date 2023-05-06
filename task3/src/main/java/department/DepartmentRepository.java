package department;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository {

    Department save(Department department);

    List<Department> findAll();

    Optional<Department> findBy(String name);

    void delete(Department department);

}
