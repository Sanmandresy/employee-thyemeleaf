package mg.prog4.employeemanagement.service;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import mg.prog4.employeemanagement.repository.SessionRepository;
import mg.prog4.employeemanagement.repository.entity.Session;
import mg.prog4.employeemanagement.repository.entity.User;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SessionService {
  private final SessionRepository repository;
  private final UserService userService;

  public Session findById(String sessionId) {
    return repository.findById(sessionId).orElseThrow();
  }

  public Session checkValidity(String sessionId) {
    return repository.checkValidity(sessionId);
  }

  public void deleteSession(String sessionId) {
    repository.deleteById(sessionId);
  }

  public String redirectUser(HttpSession session,String username, String password, String path) {
    User toCheck = userService.findByUsername(username);
    if (userService.validCredentials(password, toCheck)) {
      Session toValidate = repository.findByUserId(toCheck.getId());
      if (toValidate == null ) {
        Session created = repository.save(Session.builder().user(toCheck).build());
        session.setAttribute("username", username);
        session.setAttribute("password", password);
        session.setAttribute("sessionId", created.getId());
        session.setAttribute("isAuthenticated", true);
        return "redirect:/" + path;
      }
      else {
        if (checkValidity(toValidate.getId()).isExpired()) {
          session.setAttribute("isAuthenticated", false);
          return "redirect:/";
        }
        else {
          session.setAttribute("sessionId", toValidate.getId());
          session.setAttribute("username", username);
          session.setAttribute("password", password);
          session.setAttribute("isAuthenticated", true);
          return "redirect:/" + path;
        }
      }
    }
    return "redirect:/" + path;
  }

}
