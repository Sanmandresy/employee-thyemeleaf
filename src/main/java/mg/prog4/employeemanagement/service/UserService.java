package mg.prog4.employeemanagement.service;

import java.util.Objects;
import lombok.AllArgsConstructor;
import mg.prog4.employeemanagement.repository.UserRepository;
import mg.prog4.employeemanagement.repository.entity.User;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

  private final UserRepository repository;

  public User findByUsername(String username) {
    return repository.findByUsername(username);
  }

  public boolean validCredentials(String password, User user) {
    return Objects.equals(password, user.getPassword());
  }

  public User saveUser(User toSave) {
    return repository.save(toSave);
  }

}
