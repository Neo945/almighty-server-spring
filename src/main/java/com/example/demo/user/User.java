package com.example.demo.user;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class User {
    @Id
    private String id;
    private String name;
    private LocalDate dob;
    @Transient
    private Integer age;
    private String email;
    private String password;

    public User() {
        this.id = null;
        this.name = null;
        this.dob = null;
        this.email = null;
        this.password = null;
    }

    public User(String name, LocalDate dob, String email, String password) {
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());

    }

    public User(String id, String name, LocalDate dob, String email, String password) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.password = password;

    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public LocalDate getDob() {
        return this.dob;
    }

    public Integer getAge() {
        return Period.between(this.dob, LocalDate.now()).getYears();
    }

    public String getEmail() {
        return this.email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public String toString() {
        String str = "Student{" + "id=" + this.id + ", name='" + this.name + '\'' + ", dob=" + this.dob + ", age="
                + this.age + ", email='" + this.email + '\'' + '}';
        return str;
    }

    public boolean checkPassword(String password) {
        return BCrypt.checkpw(password, this.password);
    }

}
