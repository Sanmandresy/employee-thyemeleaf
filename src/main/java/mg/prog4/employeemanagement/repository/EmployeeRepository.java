package mg.prog4.employeemanagement.repository;


import java.util.List;
import mg.prog4.employeemanagement.repository.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
  Employee getById(String id);
  @Query("select e from Employee e where e.id in (:ids)")
  List<Employee> findEmployeesByIds(@Param("ids") List<String> ids);
}
