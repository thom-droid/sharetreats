package department;

import exception.CustomRuntimeException;
import exception.CustomRuntimeExceptionCode;

import java.util.List;

public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public String getDepartment(String name) {
        Department d = findBy(name);

        return d.relationToString();
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public String post(Department department) {
        String name = department.getName();
        departmentRepository.findBy(name).ifPresent(Department::throwDuplicatedNameException);

        return departmentRepository.save(department).getName();
    }

    @Override
    public void delete(String departmentName) {
        Department d = findBy(departmentName);
        if (d.isThisRoot()) throw new CustomRuntimeException(CustomRuntimeExceptionCode.ROOT_CANNOT_BE_DELETE);
    }

    @Override
    public void update(String departmentName, int headcount) {
        Department d = findBy(departmentName);
        d.updateHeadcount(headcount);
    }

    @Override
    public String relate(String superior, String subordinate) {
        Department sup = findBy(superior);
        Department sub = findBy(subordinate);

        sup.add(sub);
        return sub.relationToString();
    }

    private Department findBy(String name) {
        return departmentRepository.findBy(name)
                .orElseThrow(
                        () -> new CustomRuntimeException(CustomRuntimeExceptionCode.NO_SUCH_DEPARTMENT)
                );
    }
}
