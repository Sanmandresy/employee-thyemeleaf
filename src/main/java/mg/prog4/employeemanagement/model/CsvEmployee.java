package mg.prog4.employeemanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CsvEmployee {
  private String registrationNumber;
  private String id;
  private String firstName;
  private String lastName;
  private String birthDate;
  private String position;
  private char gender;
  private String startedAt;
  private String departedAt;

}
