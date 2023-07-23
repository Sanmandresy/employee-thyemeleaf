package mg.prog4.employeemanagement.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import mg.prog4.employeemanagement.controller.mapper.EmployeeMapper;
import mg.prog4.employeemanagement.service.EmployeeService;
import mg.prog4.employeemanagement.view.CreateEmployee;
import mg.prog4.employeemanagement.view.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class EmployeeController {
  private final EmployeeService service;
  private final EmployeeMapper mapper;

  @GetMapping("/employees")
  public String getEmployees(Model model) {
    List<Employee> employees = service.getEmployees().stream()
        .map(mapper::toView)
        .toList();
    model.addAttribute("employees", employees);
    return "employees";
  }

  @GetMapping("/employee")
  public String Employee(Model model, @RequestParam("id") String id) {
    Employee subject = mapper.toView(service.getById(id));
    model.addAttribute("employee", subject);
    return "employee";
  }

  @GetMapping("/add-employee")
  public String newEmployee(Model model) {
    model.addAttribute("employee", new CreateEmployee());
    return "create-employee";
  }
  @PostMapping("/save")
  public String saveEmployee(@ModelAttribute CreateEmployee employee) {
    service.createEmployee(mapper.toDomain(employee));
    return "redirect:/employees";
  }
}
