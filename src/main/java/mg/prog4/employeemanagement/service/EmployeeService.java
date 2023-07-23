package mg.prog4.employeemanagement.service;

import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import mg.prog4.employeemanagement.repository.EmployeeRepository;
import mg.prog4.employeemanagement.repository.dao.EmployeeDao;
import mg.prog4.employeemanagement.repository.model.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class EmployeeService {
  private final EmployeeRepository repository;
  private final EmployeeDao dao;

  public Employee getById(String id) {
    return repository.getById(id);
  }

  public List<Employee> getEmployees() {
    return repository.findAll();
  }

  public List<Employee> findByCriteria(String firstName, String lastName, char gender,
                                       String position, Instant startedAt, Instant departedAt,
                                       String sortField, String sortOrder, Pageable pageable) {
    return dao.findByCriteria(firstName, lastName, gender, position, startedAt, departedAt,
        sortField, sortOrder, pageable);
  }

  @Transactional
  public Employee createEmployee(Employee toCreate) {
    return repository.save(toCreate);
  }

}
