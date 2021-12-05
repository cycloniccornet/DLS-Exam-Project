package dls.examfrontend.dto;

import lombok.Data;

import java.sql.Date;
import java.sql.Time;

@Data
public class Session {
    private int sessionId;
    private int subjectId;
    private String sessionDate;
    private String scheduleStart;
    private String scheduleEnd;
    private String sessionKey;


}
