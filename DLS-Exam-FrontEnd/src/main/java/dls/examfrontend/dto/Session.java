package dls.examfrontend.dto;

import java.sql.Date;
import java.sql.Time;

public class Session {
    private int sessionId;
    private int subjectId;
    private Date sessionDate;
    private Time scheduleStart;
    private Time scheduleEnd;
    private String sessionKey;
}
