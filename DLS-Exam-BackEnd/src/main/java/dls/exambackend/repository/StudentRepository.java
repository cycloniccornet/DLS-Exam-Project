package dls.exambackend.repository;

import dls.exambackend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Query(value = "SELECT student.first_name, student.last_name, student.mail, student.student_id, student.birthdate, student.password FROM student, attendance, session " +
            "WHERE session_key = ?1 AND session.session_id = attendance.session_id AND attendance.student_id = student.student_id", nativeQuery = true)
    List<Student> getSessionStudents(String sessionKey);
}
