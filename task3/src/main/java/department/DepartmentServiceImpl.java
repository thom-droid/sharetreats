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
    public Department getDepartment(String name) {
        return departmentRepository.findOneByName(name)
                .orElseThrow(
                        () -> new CustomRuntimeException(CustomRuntimeExceptionCode.NO_SUCH_DEPARTMENT)
                );
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public String post(Department department) {
        String name = department.getName();
        departmentRepository.findOneByName(name).ifPresent(Department::throwDuplicatedNameException);

        return departmentRepository.save(department).getName();
    }

    @Override
    public void delete(Department department) {
        Department d = getDepartment(department.getName());
        if (d.isThisRoot()) throw new CustomRuntimeException(CustomRuntimeExceptionCode.ROOT_CANNOT_BE_DELETE);
    }
}
