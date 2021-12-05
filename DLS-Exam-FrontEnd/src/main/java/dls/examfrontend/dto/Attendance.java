package dls.examfrontend.dto;

import lombok.Data;

@Data
public class Attendance {
    private int attendanceId;
    private int sessionId;
    private int studentId;
    private boolean isPresent;
}
