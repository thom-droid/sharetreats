import department.DepartmentRepository;
import department.DepartmentRepositoryImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DepartmentMain {

    public static void main(String[] args) throws IOException {
        DepartmentRepository departmentRepository = new DepartmentRepositoryImpl();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String input = br.readLine();

    }

}
