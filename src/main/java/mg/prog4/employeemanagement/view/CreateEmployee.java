package mg.prog4.employeemanagement.view;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateEmployee {
  private String firstName;
  private String lastName;
  private String birthDate;
  private String address;
  private MultipartFile image;
  private char gender;


  //email
  private String personalEmail;
  private String workEmail;

  //phone
  private String mobile;
  private String home;
  private String work;

  //identity
  private String serial;
  private String deliveredIn;
  private String deliveredAt;

  //company
  private String position;
  private Integer children;
  private String startedAt;
  private String departedAt;
  private String category;
  private String cnaps;

}
