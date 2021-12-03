package dls.exambackend.repository;

import dls.exambackend.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE session \n" +
            "SET session_key = ?1\n" +
            "WHERE session_date = ?2 \n" +
            "AND (?3 BETWEEN schedule_start AND schedule_end);", nativeQuery = true)
    void setSessionKey(String sessionKey, Date date, Time currentTime);

    @Transactional
    @Modifying
    @Query(value = "UPDATE session \n" +
            "SET session_key = null WHERE session_date = ?1 " +
            "AND (?2 BETWEEN schedule_start AND schedule_end)", nativeQuery = true)
    void resetSessionKey(Date date, Time currentTime);


}
