package mg.prog4.employeemanagement.controller;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import mg.prog4.employeemanagement.EmployeeConf;
import mg.prog4.employeemanagement.controller.mapper.EmployeeMapper;
import mg.prog4.employeemanagement.controller.mapper.PhoneMapper;
import mg.prog4.employeemanagement.model.CsvEmployee;
import mg.prog4.employeemanagement.repository.entity.Phone;
import mg.prog4.employeemanagement.service.EmployeeService;
import mg.prog4.employeemanagement.model.CreateEmployee;
import mg.prog4.employeemanagement.model.Employee;
import mg.prog4.employeemanagement.service.PhoneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static mg.prog4.employeemanagement.service.utils.DataFormatterUtils.stringToInstant;

@Controller
@AllArgsConstructor
public class EmployeeController {
  private final EmployeeService service;
  private final EmployeeMapper mapper;
  private final PhoneService phoneService;
  private final EmployeeConf conf;

  @GetMapping("/employees")
  public String getEmployees(Model model, HttpSession session) {
    Boolean isAuthenticated = (Boolean) session.getAttribute("isAuthenticated");
    if (isAuthenticated == null || !isAuthenticated) {
      return "redirect:/";
    }
    List<Employee> employees = service.getEmployees().stream()
        .map(mapper::toView)
        .toList();
    model.addAttribute("employees", employees);
    model.addAttribute("conf", conf);
    return "employees";
  }

  @GetMapping("/employees/filter")
  public String filterByCriteria(
      @RequestParam(value = "lastName", required = false) String lastName,
      @RequestParam(value = "firstName", required = false) String firstName,
      @RequestParam(value = "gender", required = false) char gender,
      @RequestParam(value = "position", required = false) String position,
      @RequestParam(value = "code", required = false) String code,
      @RequestParam(value = "start", required = false) String start,
      @RequestParam(value = "departure", required = false) String departure,
      @RequestParam(value = "field", required = false) String field,
      @RequestParam(value = "order", required = false) String order,
      Model model, HttpSession session) {
    Boolean isAuthenticated = (Boolean) session.getAttribute("isAuthenticated");
    if (isAuthenticated == null || !isAuthenticated) {
      return "redirect:/";
    }
    List<Employee> employees =
        service.findByCriteria(firstName, lastName, gender, position, code, stringToInstant(start),
                stringToInstant(departure), field, order).stream()
            .map(mapper::toView)
            .collect(Collectors.toList());
    model.addAttribute("employees", employees);
    model.addAttribute("conf", conf);
    return "employees";
  }


  @GetMapping("/employee")
  public String Employee(Model model, @RequestParam("id") String id, HttpSession session) {
    Boolean isAuthenticated = (Boolean) session.getAttribute("isAuthenticated");
    if (isAuthenticated == null || !isAuthenticated) {
      return "redirect:/";
    }
    model.addAttribute("conf", conf);
    Employee employee = mapper.toView(service.getById(id));
    model.addAttribute("employee", employee);
    String[] emailParts = employee.getEmail().split("-");
    String persEmail = emailParts[0];
    String workEmail = emailParts[1];
    model.addAttribute("persEmail", persEmail);
    model.addAttribute("workEmail", workEmail);
    String[] identity = employee.getIdentity().split("\\|");
    model.addAttribute("serial", identity[0]);
    model.addAttribute("deliveredIn", identity[1]);
    model.addAttribute("deliveredAt", identity[2]);
    return "employee";
  }

  @GetMapping("/add-employee")
  public String newEmployee(Model model, HttpSession session) {
    Boolean isAuthenticated = (Boolean) session.getAttribute("isAuthenticated");
    if (isAuthenticated == null || !isAuthenticated) {
      return "redirect:/";
    }
    model.addAttribute("employee", new CreateEmployee());
    model.addAttribute("conf", conf);
    return "create-employee";
  }

  @PostMapping("/save")
  public String saveEmployee(@ModelAttribute CreateEmployee employee, HttpSession session) {
    Boolean isAuthenticated = (Boolean) session.getAttribute("isAuthenticated");
    if (isAuthenticated == null || !isAuthenticated) {
      return "redirect:/";
    }
    mg.prog4.employeemanagement.repository.entity.Employee saved =
        service.createEmployee(mapper.toDomain(employee));
    List<Phone> phones = new ArrayList<>();
    phones.add(
        phoneService.registerPhone(saved.getId(), employee.getHome(), employee.getCodeHome()));
    phones.add(
        phoneService.registerPhone(saved.getId(), employee.getMobile(), employee.getCodeMobile()));
    phones.add(phoneService.registerPhone(saved.getId(), employee.getWork(), employee.getCodeWork()));
    phoneService.crupdatePhones(phones);
    return "redirect:/employees";
  }

  @PostMapping("/csv")
  public void exportToCSV(HttpServletResponse response,
                          @RequestParam("employeeIds") String employeeIdsStr) throws
      IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {

    response.setContentType("text/csv");
    response.setHeader("Content-Disposition", "attachment; filename=employees.csv");

    List<String> employeeIds = Arrays.asList(employeeIdsStr.split(","));

    StatefulBeanToCsv<CsvEmployee> writer =
        new StatefulBeanToCsvBuilder<CsvEmployee>(response.getWriter())
            .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
            .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
            .withOrderedResults(false)
            .build();

    List<CsvEmployee> employees = service.findEmployeesByIds(employeeIds).stream()
        .map(mapper::toCsv)
        .collect(Collectors.toList());

    writer.write(employees);
  }

}
