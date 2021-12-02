package dls.examfrontend.service;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import dls.examfrontend.dto.LoginDTO;
import dls.examfrontend.dto.Student;
import dls.examfrontend.dto.Teacher;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public class Converter {

    public List<Student> convertStudentToList(JSONArray students) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Student>>(){}.getType();
        return gson.fromJson(String.valueOf(students), type);
    }

    public Student convertStudentToModel(JSONObject student) {
        Gson gson = new Gson();
        Type type = new TypeToken<Student>(){}.getType();
        return gson.fromJson(String.valueOf(student), type);
    }
    public Teacher convertTeacherToModel(JSONObject teacher) {
        Gson gson = new Gson();
        Type type = new TypeToken<Teacher>(){}.getType();
        return gson.fromJson(String.valueOf(teacher), type);
    }

    public Student convertLoginToStudent(LoginDTO loginDTO) {
        Student student = new Student();
        student.setMail(loginDTO.getMail());
        student.setPassword(loginDTO.getPassword());
        return student;
    }

    public Teacher convertLoginToTeacher(LoginDTO loginDTO) {
        Teacher teacher = new Teacher();
        teacher.setMail(loginDTO.getMail());
        teacher.setPassword(loginDTO.getPassword());
        return teacher;
    }
}
