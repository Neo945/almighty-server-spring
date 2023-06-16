package com.example.demo.user;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.config.JwtGenerator;

@Service
public class UserService {
    private final UserRepository studentRepository;

    private final JwtGenerator jwtGenerator;

    @Autowired

    public UserService(UserRepository studentRepository, JwtGenerator jwtGenerator) {
        this.studentRepository = studentRepository;
        this.jwtGenerator = jwtGenerator;
    }

    public List<User> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(User student) {
        // studentRepository.save(student);
        studentRepository.findStudentByEmail(student.getEmail()).ifPresentOrElse(s -> {
            throw new IllegalStateException("Email taken");
        }, () -> {
            studentRepository.save(student);
        });
    }

    public void deleteStudent(String studentId) {
        studentRepository.deleteById(studentId);
    }

    public User getStudents(String studentId) {
        return studentRepository.findById(studentId).orElseThrow(() -> new IllegalStateException("Student not found"));
    }

    public String login(LoginForm loginForm) throws UnsupportedEncodingException {
        User user = studentRepository.findStudentByEmail(loginForm.getEmail())
                .orElseThrow(() -> new IllegalStateException("Email not found"));
        if (user.checkPassword(loginForm.getPassword())) {
            String token = jwtGenerator.generateToken(user);
            return token;
        } else {
            throw new IllegalStateException("Wrong password");
        }
    }

}
