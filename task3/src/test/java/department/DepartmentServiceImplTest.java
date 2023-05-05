package department;

import exception.CustomRuntimeException;
import exception.CustomRuntimeExceptionCode;
import org.junit.jupiter.api.Test;
import test_utils.DepartmentRepositoryTestImpl;

import static org.junit.jupiter.api.Assertions.*;

public class DepartmentServiceImplTest {

    DepartmentRepositoryTestImpl departmentRepository = new DepartmentRepositoryTestImpl();
    DepartmentService departmentService = new DepartmentServiceImpl(departmentRepository);

    @Test
    void givenNewDepartmentWithDuplicatedName_whenPost_thenThrows() {

        //given
        String duplicatedName = departmentRepository.getRandomName();
        Department department = Department.of(10, duplicatedName);

        //then
        Throwable t = assertThrows(
                CustomRuntimeException.class, () -> departmentService.post(department));
        assertEquals(CustomRuntimeExceptionCode.DUPLICATED_NAME.getMessage(), t.getMessage());

    }

    @Test
    void givenDepartmentNameNotMatchedInStorage_whenGet_thenThrows() {

        //given
        String removedName = departmentRepository.getRemovedName();

        //when and then

        Throwable t = assertThrows(
                CustomRuntimeException.class, () -> departmentService.getDepartment(removedName));
        assertEquals(CustomRuntimeExceptionCode.NO_SUCH_DEPARTMENT.getMessage(), t.getMessage());
        //Todo department which field should be final? if there is new employee,
        //Todo when removed, cache must be updated too
    }

}