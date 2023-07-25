package mg.prog4.employeemanagement.controller;

import lombok.AllArgsConstructor;
import mg.prog4.employeemanagement.EmployeeConf;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
public class WebController {
  private final EmployeeConf conf;

  @RequestMapping(value = "/")
  public String index(Model model) {
    model.addAttribute("conf", conf);
    return "index";
  }

}
