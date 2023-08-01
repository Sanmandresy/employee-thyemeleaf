package mg.prog4.employeemanagement.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@Builder(toBuilder = true)
@Entity(name = "User")
@Table(name = "\"user\"")
public class User implements Serializable {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private String id;
  private String username;
  private String password;

  @OneToOne(mappedBy = "user")
  private Session session;
}
