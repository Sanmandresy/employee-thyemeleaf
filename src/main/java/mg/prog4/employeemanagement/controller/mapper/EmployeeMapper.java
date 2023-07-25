package mg.prog4.employeemanagement.controller.mapper;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import mg.prog4.employeemanagement.model.CsvEmployee;
import mg.prog4.employeemanagement.repository.PhoneRepository;
import mg.prog4.employeemanagement.repository.entity.Employee;
import mg.prog4.employeemanagement.model.CreateEmployee;
import mg.prog4.employeemanagement.repository.entity.Phone;
import org.springframework.stereotype.Component;

import static mg.prog4.employeemanagement.service.utils.DataFormatterUtils.byteAToBase64;
import static mg.prog4.employeemanagement.service.utils.DataFormatterUtils.instantToCommonDate;
import static mg.prog4.employeemanagement.service.utils.DataFormatterUtils.multipartFileToByte;
import static mg.prog4.employeemanagement.service.utils.DataFormatterUtils.stringToInstant;

@Component
@AllArgsConstructor
public class EmployeeMapper {

  private final PhoneRepository phoneRepository;
  private final PhoneMapper phoneMapper;

  private String formatEmail(CreateEmployee toCreate) {
    return toCreate.getPersonalEmail() +
        "-" +
        toCreate.getWorkEmail();
  }

  private String formatIdentity(CreateEmployee toCreate) {
    return toCreate.getSerial() +
        "|" +
        toCreate.getDeliveredIn() +
        "|" +
        toCreate.getDeliveredAt();
  }

  public Employee toDomain(CreateEmployee toCreate) {
    return Employee.builder()
        .firstName(toCreate.getFirstName())
        .lastName(toCreate.getLastName())
        .cnaps(toCreate.getCnaps())
        .email(formatEmail(toCreate))
        .birthDate(stringToInstant(toCreate.getBirthDate()))
        .image(multipartFileToByte(toCreate.getImage()))
        .address(toCreate.getAddress())
        .gender(toCreate.getGender())
        .category(toCreate.getCategory())
        .children(toCreate.getChildren())
        .departedAt(stringToInstant(toCreate.getDepartedAt()))
        .startedAt(stringToInstant(toCreate.getStartedAt()))
        .position(toCreate.getPosition())
        .identity(formatIdentity(toCreate))
        .build();
  }

  public CsvEmployee toCsv(Employee entity) {
    return CsvEmployee.builder()
        .id(entity.getId())
        .lastName(entity.getLastName())
        .firstName(entity.getFirstName())
        .birthDate(instantToCommonDate(entity.getBirthDate()))
        .startedAt(instantToCommonDate(entity.getStartedAt()))
        .departedAt(instantToCommonDate(entity.getDepartedAt()))
        .gender(entity.getGender())
        .position(entity.getPosition())
        .build();
  }

  public mg.prog4.employeemanagement.model.Employee toView(Employee toShow) {
    List<Phone> phone = phoneRepository.getByEmployeeId(toShow.getId());
    return mg.prog4.employeemanagement.model.Employee.builder()
        .id(toShow.getId())
        .registrationNumber(toShow.getRegistrationNumber())
        .lastName(toShow.getLastName())
        .firstName(toShow.getFirstName())
        .birthDate(instantToCommonDate(toShow.getBirthDate()))
        .image(byteAToBase64(toShow.getImage()))
        .email(toShow.getEmail())
        .address(toShow.getAddress())
        .phone(phone.stream().map(phoneMapper::toView).collect(Collectors.toList()))
        .identity(toShow.getIdentity())
        .position(toShow.getPosition())
        .children(toShow.getChildren())
        .startedAt(instantToCommonDate(toShow.getStartedAt()))
        .departedAt(instantToCommonDate(toShow.getDepartedAt()))
        .category(toShow.getCategory())
        .cnaps(toShow.getCnaps())
        .gender(toShow.getGender())
        .build();
  }

}
