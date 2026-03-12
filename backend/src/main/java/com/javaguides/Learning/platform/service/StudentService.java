package com.javaguides.Learning.platform.service;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import com.javaguides.Learning.platform.security.JwtTokenUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.javaguides.Learning.platform.dto.StudentLoginRequest;
import com.javaguides.Learning.platform.dto.StudentLoginResponse;

import com.javaguides.Learning.platform.dto.StudentSignupRequest;
import com.javaguides.Learning.platform.dto.StudentSignupResponse;

import com.javaguides.Learning.platform.model.Student;
import com.javaguides.Learning.platform.repository.StudentRepository;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final FirebaseService firebaseService;

    @Autowired
    public StudentService(StudentRepository studentRepository,
                          PasswordEncoder passwordEncoder,
                          JwtTokenUtil jwtTokenUtil,
                          FirebaseService firebaseService) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
        this.firebaseService = firebaseService;
    }

    public StudentSignupResponse registerStudent(StudentSignupRequest signupRequest) {
        try {
            if (studentRepository.existsByEmail(signupRequest.getEmail())) {
                return new StudentSignupResponse("Email already in use", false, null);
            }

            Student student = new Student();
            student.setFullName(signupRequest.getFullName());
            student.setEmail(signupRequest.getEmail());
            student.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
            student.setInterests(signupRequest.getInterests());
            student.setAgreeToTerms(signupRequest.isAgreeToTerms());

            String firebaseId = firebaseService.saveStudentToFirebase(student);
            student.setFirebaseId(firebaseId);

            Student savedStudent = studentRepository.save(student);
            String token = generateToken(savedStudent);

            return new StudentSignupResponse("Student registered successfully", true, token);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return new StudentSignupResponse("Error registering student: " + e.getMessage(), false, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new StudentSignupResponse("Unexpected error: " + e.getMessage(), false, null);
        }
    }

    public StudentLoginResponse loginStudent(StudentLoginRequest loginRequest) {
        Student student = studentRepository.findByEmail(loginRequest.getEmail());

        if (student == null || !passwordEncoder.matches(loginRequest.getPassword(), student.getPassword())) {
            return new StudentLoginResponse(false, "Invalid email or password");
        }

        String token = generateToken(student);

        StudentLoginResponse response = new StudentLoginResponse(true, "Login successful");
        response.setToken(token);
        response.setId(student.getId());
        response.setFullName(student.getFullName());
        response.setEmail(student.getEmail());
        response.setInterests(student.getInterests());

        return response;
    }

    private String generateToken(Student student) {
        return jwtTokenUtil.generateToken(student.getEmail());
    }
}