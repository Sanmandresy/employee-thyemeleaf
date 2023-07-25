package mg.prog4.employeemanagement.controller.mapper;

import mg.prog4.employeemanagement.model.CreatePhone;
import mg.prog4.employeemanagement.model.Phone;
import org.springframework.stereotype.Component;

@Component
public class PhoneMapper {
  public Phone toView(mg.prog4.employeemanagement.repository.entity.Phone entity) {
    return Phone.builder()
        .id(entity.getId())
        .employeeId(entity.getEmployeeId())
        .value(entity.getValue())
        .build();
  }

  public mg.prog4.employeemanagement.repository.entity.Phone toEntity(CreatePhone model) {
    return mg.prog4.employeemanagement.repository.entity.Phone.builder()
        .employeeId(model.getEmployeeId())
        .value(model.getValue())
        .build();
  }
}
