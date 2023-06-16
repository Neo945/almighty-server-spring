package com.example.demo.user;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Resources for the api
@RestController
@RequestMapping(path = "api/v1/student")
public class UserController {

    private final UserService studentService;

    @Autowired
    public UserController(UserService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/")
    public List<User> getStudents(HttpServletRequest req) {
        return this.studentService.getStudents();
    }

    @GetMapping(path = "/{studentId}")
    public User getStudents(@PathVariable("studentId") String studentId) {
        User user = this.studentService.getStudents(studentId);
        return user;
    }

    @PostMapping("/")
    public User registerNewStudent(@RequestBody User student) {
        System.out.println(student.getPassword());
        this.studentService.addNewStudent(student);
        return student;

    }

    @DeleteMapping(path = "/{studentId}")
    public void deleteStudent(@PathVariable("studentId") String studentId) {
        this.studentService.deleteStudent(studentId);
    }

    @PostMapping(path = "/login")
    public String login(@RequestBody LoginForm loginForm,
            HttpServletResponse response) throws UnsupportedEncodingException {
        String token = this.studentService.login(loginForm);
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 365 * 10);
        response.addCookie(cookie);
        return "login successful";
    }

    @GetMapping(path = "/user")
    public User getUser(@CookieValue("token") String token, @RequestAttribute("user") User user) {
        return user;
    }

    @PostMapping(path = "/logout")
    public void logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", "");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
