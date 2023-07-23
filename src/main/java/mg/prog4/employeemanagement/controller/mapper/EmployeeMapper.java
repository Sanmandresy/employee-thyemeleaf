package mg.prog4.employeemanagement.controller.mapper;

import java.util.List;
import mg.prog4.employeemanagement.repository.model.Employee;
import mg.prog4.employeemanagement.view.CreateEmployee;
import org.springframework.stereotype.Component;

import static mg.prog4.employeemanagement.service.utils.DataFormatterUtils.byteAToBase64;
import static mg.prog4.employeemanagement.service.utils.DataFormatterUtils.instantToCommonDate;
import static mg.prog4.employeemanagement.service.utils.DataFormatterUtils.multipartFileToByte;
import static mg.prog4.employeemanagement.service.utils.DataFormatterUtils.stringToInstant;

@Component
public class EmployeeMapper {

  private String formatEmail(CreateEmployee toCreate) {
    return toCreate.getPersonalEmail() +
        "-" +
        toCreate.getWorkEmail();
  }

  private String formatPhone(CreateEmployee toCreate) {
    return toCreate.getMobile() +
        "-" +
        toCreate.getHome() +
        "-" +
        toCreate.getWork();
  }

  private String formatIdentity(CreateEmployee toCreate) {
    return toCreate.getSerial() +
        "-" +
        toCreate.getDeliveredIn() +
        "-" +
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
        .phone(formatPhone(toCreate))
        .position(toCreate.getPosition())
        .identity(formatIdentity(toCreate))
        .build();
  }

  public mg.prog4.employeemanagement.view.Employee toView(Employee toShow) {
    return mg.prog4.employeemanagement.view.Employee.builder()
        .id(toShow.getId())
        .registrationNumber(toShow.getRegistrationNumber())
        .lastName(toShow.getLastName())
        .firstName(toShow.getFirstName())
        .birthDate(instantToCommonDate(toShow.getBirthDate()))
        .image(byteAToBase64(toShow.getImage()))
        .email(toShow.getEmail())
        .address(toShow.getAddress())
        .phone(toShow.getPhone())
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
