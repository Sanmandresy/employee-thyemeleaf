package mg.prog4.employeemanagement.controller.mapper;

import lombok.AllArgsConstructor;
import mg.prog4.employeemanagement.model.CreatePhone;
import mg.prog4.employeemanagement.model.Phone;
import mg.prog4.employeemanagement.repository.EmployeeRepository;
import mg.prog4.employeemanagement.repository.entity.Employee;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PhoneMapper {
  private final EmployeeRepository employeeRepository;
  public Phone toView(mg.prog4.employeemanagement.repository.entity.Phone entity) {
    return Phone.builder()
        .id(entity.getId())
        .employeeId(entity.getEmployee().getId())
        .value(entity.getValue())
        .code(entity.getCode())
        .build();
  }

  public mg.prog4.employeemanagement.repository.entity.Phone toEntity(CreatePhone model) {
    Employee employee = employeeRepository.getById(model.getEmployeeId());
    return mg.prog4.employeemanagement.repository.entity.Phone.builder()
        .employee(employee)
        .value(model.getValue())
        .code(model.getCode())
        .build();
  }
}
