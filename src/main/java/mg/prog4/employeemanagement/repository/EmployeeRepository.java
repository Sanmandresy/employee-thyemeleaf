package mg.prog4.employeemanagement.repository;


import mg.prog4.employeemanagement.repository.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
  Employee getById(String id);
}
