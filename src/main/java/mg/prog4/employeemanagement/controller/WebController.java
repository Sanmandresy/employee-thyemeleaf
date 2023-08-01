package mg.prog4.employeemanagement.controller;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import mg.prog4.employeemanagement.EmployeeConf;
import mg.prog4.employeemanagement.service.SessionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/")
public class WebController {
  private final EmployeeConf conf;
  private final SessionService service;

  @GetMapping
  public String index(Model model) {
    model.addAttribute("conf", conf);
    return "index";
  }

  @PostMapping
  public String login(@RequestParam String username, @RequestParam String password, HttpSession session) {
    return service.redirectUser(session, username, password, "employees");
  }

  @GetMapping("logout")
  public String logout(HttpSession session) {
    String sessionId = (String) session.getAttribute("sessionId");
    service.deleteSession(sessionId);
    return "index";
  }

}
