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
import mg.prog4.employeemanagement.repository.model.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class EmployeeDao {
  private final EntityManager entityManager;


  public List<Employee> findByCriteria(String firstName, String lastName, char gender,
                                       String position, Instant startedAt, Instant departedAt,
                                       String sortField, String sortOrder, Pageable pageable) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Employee> query = builder.createQuery(Employee.class);
    Root<Employee> root = query.from(Employee.class);

    List<Predicate> predicates = new ArrayList<>();

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

    if (sortOrder.equalsIgnoreCase("asc")) {
      query.orderBy(builder.asc(root.get(sortField)));
    } else if (sortOrder.equalsIgnoreCase("desc")) {
      query.orderBy(builder.desc(root.get(sortField)));
    } else {
      query.orderBy(QueryUtils.toOrders(pageable.getSort(), root, builder));
    }

    return entityManager.createQuery(query)
        .setFirstResult((pageable.getPageNumber()) * pageable.getPageSize())
        .setMaxResults(pageable.getPageSize())
        .getResultList();
  }
}
