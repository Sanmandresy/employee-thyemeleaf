package mg.prog4.employeemanagement.repository;

import mg.prog4.employeemanagement.repository.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, String> {
  @Query("select s from Session s where s.user.id = :userId")
  Session findByUserId(@Param("userId") String userId);

  @Query(value = "select * from select_and_update_isExpired(:sessionId)", nativeQuery = true)
  Session checkValidity(@Param("sessionId") String sessionId);
}
