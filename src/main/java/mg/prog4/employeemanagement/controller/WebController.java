package mg.prog4.employeemanagement.controller;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import mg.prog4.employeemanagement.EmployeeConf;
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

  @GetMapping
  public String index(Model model) {
    model.addAttribute("conf", conf);
    return "index";
  }

  @PostMapping
  public String login(@RequestParam String email, @RequestParam String password, HttpSession session) {
    session.setAttribute("email", email);
    session.setAttribute("password", password);
    session.setAttribute("isAuthenticated", true);
    return "redirect:/employees";
  }

}
