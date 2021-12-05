package dls.examfrontend.dto;

import lombok.Data;

@Data
public class Subject {
    private int subjectId;
    private String subjectName;
    private int numOfStudents;
    private int teacherId;

    @Override
    public String toString() {
        return "Subject{" +
                "subjectId=" + subjectId +
                ", subjectName='" + subjectName + '\'' +
                ", numOfStudents=" + numOfStudents +
                ", teacherId=" + teacherId +
                '}';
    }
}
