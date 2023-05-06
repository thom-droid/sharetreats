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
        //Todo when removed, cache must be updated too
    }

    @Test
    void givenRootDepartment_whenDelete_thenThrows() {

        //given
        String randomRootName = departmentRepository.getRandomRootDepartment();

        //when and then
        Throwable t = assertThrows(CustomRuntimeException.class, () -> departmentService.delete(randomRootName));
        assertEquals(CustomRuntimeExceptionCode.ROOT_CANNOT_BE_DELETE.getMessage(), t.getMessage());

    }

    @Test
    void givenTwoExistingDepartments_whenRelatedWithRootSet_thenSucceed() {

        //given
        Department root = departmentRepository.save(Department.of(5, "TESTROOT", true));
        Department superior = departmentRepository.save(Department.of(10, "SUP"));
        Department subordinate = departmentRepository.save(Department.of(5, "SUB"));
        root.addSubordinate(superior);

        //when
        String result = assertDoesNotThrow(() -> departmentService.relate(superior.getName(), subordinate.getName()));

        System.out.println(result);
    }

    @Test
    void givenTwoExistingDepartments_whenRelatedWithoutRoot_thenThrows() {

        //given
        Department superior = departmentRepository.save(Department.of(10, "SUP"));
        Department subordinate = departmentRepository.save(Department.of(5, "SUB"));

        //when
        Throwable t = assertThrows(
                CustomRuntimeException.class, () -> departmentService.relate(superior.getName(), subordinate.getName())
        );

        assertEquals(CustomRuntimeExceptionCode.NO_SUPERIOR_IS_SET.getMessage(), t.getMessage());
    }

}