package mg.prog4.employeemanagement.repository.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import mg.prog4.employeemanagement.repository.entity.Employee;
import mg.prog4.employeemanagement.repository.entity.Phone;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class EmployeeDao {
  private final EntityManager entityManager;


  public List<Employee> findByCriteria(String firstName, String lastName, char gender,
                                       String position, String code, Instant startedAt,
                                       Instant departedAt,
                                       String sortField, String sortOrder) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Employee> query = builder.createQuery(Employee.class);
    Root<Employee> root = query.from(Employee.class);
    Join<Employee, Phone> phoneJoin = root.join("phones");


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

    if (code.isEmpty()) {
      code = null;
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

    if (code != null) {
      predicates.add(builder.equal(phoneJoin.get("code"), code));
    }

    if (startedAt != null && departedAt != null) {
      predicates.add(
          builder.or(
              builder.between(root.get("startedAt"), startedAt, departedAt),
              builder.between(root.get("departedAt"), startedAt, departedAt)
          )
      );

    }

    query
        .distinct(true)
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

  public List<Employee> findByCriteriaNative(String firstName, String lastName, char gender,
                                             String position, String code, Instant startedAt,
                                             Instant departedAt,
                                             String sortField, String sortOrder) {
    StringBuilder query = new StringBuilder(
        "SELECT DISTINCT e.* FROM Employee e LEFT JOIN Phone p ON e.id = p.employee_id WHERE 1=1 ");

    if (firstName != null && !firstName.isEmpty()) {
      query.append("AND (lower(e.first_name) LIKE :firstName OR e.first_name LIKE :firstName) ");
    }

    if (lastName != null && !lastName.isEmpty()) {
      query.append("AND (lower(e.last_name) LIKE :lastName OR e.last_name LIKE :lastName) ");
    }

    if (gender != ' ') {
      query.append("AND e.gender = :gender ");
    }

    if (position != null && !position.isEmpty()) {
      query.append("AND (lower(e.position) LIKE :position OR e.position LIKE :position) ");
    }

    if (code != null && !code.isEmpty()) {
      query.append("AND p.code = :code ");
    }

    if (startedAt != null && departedAt != null) {
      query.append(
          "AND (e.started_at BETWEEN :startedAt AND :departedAt OR e.departed_at BETWEEN :startedAt AND :departedAt) ");
    }

    if (sortField != null && !sortField.isEmpty() && sortOrder.equalsIgnoreCase("asc")) {
      query.append("ORDER BY ").append("e.").append(sortField)
          .append(" ASC ");
    } else if (sortField != null && !sortField.isEmpty() && sortOrder.equalsIgnoreCase("desc")) {
      query.append("ORDER BY ").append("e.").append(sortField)
          .append(" DESC ");
    } else {
      query.append("ORDER BY e.last_name ASC ");
    }

    Query nativeQuery = entityManager.createNativeQuery(query.toString(), Employee.class);

    if (firstName != null && !firstName.isEmpty()) {
      nativeQuery.setParameter("firstName", "%" + firstName + "%");
    }

    if (lastName != null && !lastName.isEmpty()) {
      nativeQuery.setParameter("lastName", "%" + lastName + "%");
    }

    if (gender != ' ') {
      nativeQuery.setParameter("gender", String.valueOf(gender));
    }

    if (position != null && !position.isEmpty()) {
      nativeQuery.setParameter("position", "%" + position + "%");
    }

    if (code != null && !code.isEmpty()) {
      nativeQuery.setParameter("code", code);
    }

    if (startedAt != null && departedAt != null) {
      nativeQuery.setParameter("startedAt", startedAt);
      nativeQuery.setParameter("departedAt", departedAt);
    }

    return nativeQuery.getResultList();
  }


}
