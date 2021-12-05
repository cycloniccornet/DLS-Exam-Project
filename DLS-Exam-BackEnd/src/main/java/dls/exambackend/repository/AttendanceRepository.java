package dls.exambackend.repository;

import dls.exambackend.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO attendance (session_id, student_id, is_present) VALUES " +
            "((SELECT session_id FROM session WHERE session_key = ?1) , ?2, 1)", nativeQuery = true)
    String enterSessionKey(String sessionKey, int student_id);
}
