package dls.examfrontend.service;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import dls.examfrontend.dto.Student;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public class JsonConverters {

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
}
