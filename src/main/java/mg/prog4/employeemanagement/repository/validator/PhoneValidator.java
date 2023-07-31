package mg.prog4.employeemanagement.repository.validator;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import mg.prog4.employeemanagement.EmployeeConf;
import mg.prog4.employeemanagement.repository.PhoneRepository;
import mg.prog4.employeemanagement.repository.entity.Phone;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PhoneValidator implements Consumer<Phone> {

  private final PhoneRepository repository;
  private final EmployeeConf conf;

  @Override
  public void accept(Phone phone) {
    boolean alreadyExists = repository.existsByValueAndCode(phone.getValue(), phone.getCode());
    boolean alreadyOwnedBySociety = String.join("", phone.getValue().split(" ")).equals(String.join("", conf.getPhone().split(" ")));
    Set<String> violationMessages = new HashSet<>();
    if (phone.getId() == null && alreadyExists && alreadyOwnedBySociety) {
      violationMessages.add("Phone number already exists");
    }
    if (String.join("", phone.getValue().split(" ")).length() < 10) {
      violationMessages.add("Value length must be equal or superior to 10.");
    }
    if (!violationMessages.isEmpty()) {
      String formattedViolationMessages = violationMessages.stream()
          .map(String::toString)
          .collect(Collectors.joining(". "));
      throw new RuntimeException(formattedViolationMessages);
    }
  }
}
