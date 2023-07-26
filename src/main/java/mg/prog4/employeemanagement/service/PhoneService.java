package mg.prog4.employeemanagement.service;

import java.util.List;
import lombok.AllArgsConstructor;
import mg.prog4.employeemanagement.repository.PhoneRepository;
import mg.prog4.employeemanagement.repository.entity.Employee;
import mg.prog4.employeemanagement.repository.entity.Phone;
import mg.prog4.employeemanagement.repository.validator.PhoneValidator;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PhoneService {
  private final PhoneRepository repository;
  private final PhoneValidator validator;
  private final EmployeeService employeeService;

  public List<Phone> crupdatePhones(List<Phone> toSave) {
    toSave.forEach(validator);
    return repository.saveAll(toSave);
  }

  public Phone registerPhone(String employeeId, String value,String code) {
    Phone phone = new Phone();
    Employee employee = employeeService.getById(employeeId);
    phone.setEmployee(employee);
    phone.setValue(value);
    phone.setCode(code);
    return phone;
  }

  public List<Phone> getByEmployeeId(String employeeId) {
    return repository.getByEmployeeId(employeeId);
  }

}
