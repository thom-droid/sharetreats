package mock;

import department.Department;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DepartmentRepositoryImpl implements DepartmentRepository {

    private final Map<String, Department> storage;

    public DepartmentRepositoryImpl() {
        this.storage = new HashMap<>();
        setup();
    }

    @Override
    public void save(Department department) {

    }

    @Override
    public List<Department> findAll() {
        return null;
    }

    @Override
    public Optional<Department> findOneByName(String name) {
        return null;
    }

    private void setup() {
        Department dev = new Department(10, "DEV", true);
        dev.setAsRoot();
        Department backend = new Department(20, "BACKEND", false);
        Department frontend = new Department(20, "FRONTEND", false);
        Department devops = new Department(30, "DEVOPS", false);
        dev.addSubordinate(backend);
        dev.addSubordinate(frontend);
        dev.addSubordinate(devops);
    }
}
