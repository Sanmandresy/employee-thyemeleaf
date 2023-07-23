package mg.prog4.employeemanagement.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(allowedHeaders = "*")
public class HealthController {
  @GetMapping({"hello", "hello_there"})
  public String helloThere() {
    return "General Kenobi !";
  }
}
