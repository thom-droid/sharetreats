package test_utils;

import department.Department;
import department.DepartmentRepository;

import java.util.*;

public class DepartmentRepositoryTestImpl implements DepartmentRepository {

    private final Map<String, Department> storage;

    public DepartmentRepositoryTestImpl() {
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
    public Optional<Department> findOneByName(String name) {
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
        dev.addSubordinate(backend);
        dev.addSubordinate(frontend);
        dev.addSubordinate(devops);

        put(dev);
        put(backend);
        put(frontend);
        put(devops);
    }

    private void put(Department department) {
        storage.put(department.getName(), department);
    }

    public String getRandomName() {
        return storage.values().stream().findAny().get().getName();
    }

    public String getRemovedName() {
        String randomName = getRandomName();
        storage.remove(randomName);
        return randomName;
    }

}
