package dls.examfrontend.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
public class SessionChecker {

    public void loginCheck(HttpSession session, HttpServletResponse response) {
        try {
            if (session.getAttribute("student") != null) {
                response.sendRedirect("/student");
            } else if (session.getAttribute("teacher") != null) {
                response.sendRedirect("/teacher");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sessionCheck(HttpSession session, HttpServletResponse response) {
        try {
            if (session.getAttribute("student") == null && session.getAttribute("teacher") == null) {
                response.sendRedirect("/login");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
