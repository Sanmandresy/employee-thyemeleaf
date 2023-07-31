package mg.prog4.employeemanagement;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class EmployeeConf {
  private String id = "numer_id";
  private String name = "Numer";
  private String description = "Numer ESN";
  private String motto = "";
  private String address = "Fontenay Sous Bois 94120";
  private String email = "contact@numer.tech";
  private String code = "06";
  private String phone = "12 56 894 456";
  private String NIF = "";
  private String STAT = "";
  private String RCS = "";
  private String logo =
      "https://static.wixstatic.com/media/c6fa1c_88cd4438b50842cd89c29b47b21fdbcb~mv2.png/v1/fill/w_54,h_54,al_c,q_85,usm_0.66_1.00_0.01,enc_auto/image-fotor-20230713142318.png";
}
