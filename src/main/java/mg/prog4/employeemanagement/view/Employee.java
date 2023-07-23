package mg.prog4.employeemanagement.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Employee {
  private String registrationNumber;
  private String id;
  private String firstName;
  private String lastName;
  private String birthDate;
  private String address;
  private String image;
  private char gender;

  //email
  private String email;

  //phone
  private String phone;

  //identity
  private String identity;

  //company
  private String position;
  private Integer children;
  private String startedAt;
  private String departedAt;
  private String category;
  private String cnaps;
}
