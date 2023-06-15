package com.example.demo.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository studentRepository;

    @Autowired
    public UserService(UserRepository studentRepository) {
        this.studentRepository = studentRepository;
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

    public String login(LoginForm loginForm) {
        User user = studentRepository.findStudentByEmail(loginForm.getEmail())
                .orElseThrow(() -> new IllegalStateException("Email not found"));
        if (user.checkPassword(loginForm.getPassword())) {
            return user.getId();
        } else {
            throw new IllegalStateException("Wrong password");
        }
    }

}
