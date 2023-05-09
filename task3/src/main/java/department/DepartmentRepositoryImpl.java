package department;

import java.util.*;

public class DepartmentRepositoryImpl implements DepartmentRepository {

    private final Map<String, Department> storage;

    public DepartmentRepositoryImpl() {
        this.storage = new HashMap<>();
        setup();
    }

    @Override
    public Department save(Department department) {
        put(department);
        return department;
    }

    @Override
    public List<Department> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<Department> findBy(String name) {
        return Optional.ofNullable(storage.get(name));
    }

    @Override
    public void delete(Department department) {
        storage.remove(department.getName());
    }

    private void setup() {
        Department dev = new Department(10, "DEV", true);
        dev.setAsRoot();
        Department backend = new Department(20, "BACKEND", false);
        Department frontend = new Department(20, "FRONTEND", false);
        Department devops = new Department(30, "DEVOPS", false);
        dev.add(backend);
        dev.add(frontend);
        dev.add(devops);

        put(dev);
        put(backend);
        put(frontend);
        put(devops);
    }

    private void put(Department department) {
        storage.put(department.getName(), department);
    }
}
