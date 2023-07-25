package mg.prog4.employeemanagement.repository.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import mg.prog4.employeemanagement.repository.entity.Employee;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class EmployeeDao {
  private final EntityManager entityManager;


  public List<Employee> findByCriteria(String firstName, String lastName, char gender,
                                       String position, Instant startedAt, Instant departedAt,
                                       String sortField, String sortOrder) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Employee> query = builder.createQuery(Employee.class);
    Root<Employee> root = query.from(Employee.class);

    List<Predicate> predicates = new ArrayList<>();

    if (firstName.isEmpty()) {
      firstName = null;
    }
    if (lastName.isEmpty()) {
      lastName = null;
    }
    if (position.isEmpty()) {
      position = null;
    }
    if (sortField.isEmpty()) {
      sortField = null;
    }


    if (firstName != null) {
      predicates.add(
          builder.or(
              builder.like(builder.lower(root.get("firstName")), "%" + firstName + "%"),
              builder.like(root.get("firstName"), "%" + firstName + "%")
          )
      );
    }

    if (lastName != null) {
      predicates.add(
          builder.or(
              builder.like(builder.lower(root.get("lastName")), "%" + lastName + "%"),
              builder.like(root.get("lastName"), "%" + lastName + "%")
          )
      );
    }

    if ((gender == 'M') || (gender == 'F')) {
      predicates.add(builder.equal(root.get("gender"), gender));
    }

    if (position != null) {
      predicates.add(
          builder.or(
              builder.like(builder.lower(root.get("position")), "%" + position + "%"),
              builder.like(root.get("position"), "%" + position + "%")
          )
      );
    }

    if (startedAt != null && departedAt != null) {
      predicates.add(builder.between(root.get("startedAt"), startedAt, departedAt));
      predicates.add(builder.between(root.get("departedAt"), startedAt, departedAt));
    }

    query
        .where(builder.and(predicates.toArray(new Predicate[0])));

    if (sortField != null && sortOrder.equalsIgnoreCase("asc")) {
      query.orderBy(builder.asc(root.get(sortField)));
    } else if (sortField != null && sortOrder.equalsIgnoreCase("desc")) {
      query.orderBy(builder.desc(root.get(sortField)));
    } else {
      query.orderBy(builder.asc(root.get("lastName")));
    }


    return entityManager.createQuery(query).getResultList();
  }
}
