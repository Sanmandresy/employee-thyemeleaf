package mg.prog4.employeemanagement.service;

import java.util.List;
import lombok.AllArgsConstructor;
import mg.prog4.employeemanagement.repository.PhoneRepository;
import mg.prog4.employeemanagement.repository.entity.Phone;
import mg.prog4.employeemanagement.repository.validator.PhoneValidator;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PhoneService {
  private final PhoneRepository repository;
  private final PhoneValidator validator;

  public List<Phone> crupdatePhones(List<Phone> toSave) {
    toSave.forEach(validator);
    return repository.saveAll(toSave);
  }

  public Phone registerPhone(String employeeId, String value) {
    Phone phone = new Phone();
    phone.setEmployeeId(employeeId);
    phone.setValue(value);
    return phone;
  }

  public List<Phone> getByEmployeeId(String employeeId) {
    return repository.getByEmployeeId(employeeId);
  }

}
