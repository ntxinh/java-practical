package example.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SetupEmployeeExampleData implements CommandLineRunner {
    @Autowired
    private EmployeeRepository employeeRepository;

    public SetupEmployeeExampleData() {
    }

    public void run(String... args) throws Exception {
        Employee bill = employeeRepository.save(new Employee("Bill"));
        System.out.println(bill.getId());
    }
}
