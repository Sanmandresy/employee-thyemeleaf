package mg.prog4.employeemanagement.repository.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
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
      predicates.add(
          builder.or(
              builder.between(root.get("startedAt"), startedAt, departedAt),
              builder.between(root.get("departedAt"), startedAt, departedAt)
          )
      );

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

  public List<Employee> findByCriteriaNative(String firstName, String lastName, char gender,
                                             String position, Instant startedAt, Instant departedAt,
                                             String sortField, String sortOrder) {
    StringBuilder query = new StringBuilder("select * from Employee where 1=1 ");

    if (firstName != null && !firstName.isEmpty()) {
      query.append("and (lower(first_name) like :firstName or first_name like :firstName) ");
    }

    if (lastName != null && !lastName.isEmpty()) {
      query.append("and (lower(last_name) like :lastName or last_name like :lastName) ");
    }

    if (gender != ' ') {
      query.append("and gender=:gender ");
    }

    if (position != null && !position.isEmpty()) {
      query.append("and (lower(position) like :position or position like :position) ");
    }

    if (startedAt != null && departedAt != null) {
      query.append(
          "and (started_at between :startedAt and :departedAt OR departed_at between :startedAt and :departedAt) ");
    }

    if (sortField != null && !sortField.isEmpty() && sortOrder.equalsIgnoreCase("asc")) {
      query.append("order by ")
          .append(sortField)
          .append(" asc ");
    } else if (sortField != null && !sortField.isEmpty() && sortOrder.equalsIgnoreCase("desc")) {
      query.append("order by ")
          .append(sortField)
          .append(" desc ");
    } else {
      query.append("order by last_name asc ");
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

    if (startedAt != null && departedAt != null) {
      nativeQuery.setParameter("startedAt", startedAt);
      nativeQuery.setParameter("departedAt", departedAt);
    }

    return nativeQuery.getResultList();
  }


}
