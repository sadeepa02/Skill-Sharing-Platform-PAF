package com.javaguides.Learning.platform.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.javaguides.Learning.platform.dto.StudentSignupRequest;
import com.javaguides.Learning.platform.dto.StudentSignupResponse;
import com.javaguides.Learning.platform.service.StudentService;

import com.javaguides.Learning.platform.dto.StudentLoginRequest;
import com.javaguides.Learning.platform.dto.StudentLoginResponse;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*") // In production, restrict this to your client domain
public class StudentController {

    @Autowired
    private StudentService studentService;

    // POST endpoint for student signup
    @PostMapping("/signup")
    public ResponseEntity<StudentSignupResponse> registerStudent(@Valid @RequestBody StudentSignupRequest signupRequest) {
        StudentSignupResponse response = studentService.registerStudent(signupRequest);

        if (response.isSuccess()) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // Simple GET endpoint to verify backend is running
    @GetMapping("/health")
    public String healthCheck() {
        return " Backend is working!";
    }

    @PostMapping("/login")
    public ResponseEntity<StudentLoginResponse> loginStudent(@Valid @RequestBody StudentLoginRequest loginRequest) {
        StudentLoginResponse response = studentService.loginStudent(loginRequest);

        if (response.isSuccess()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }
}
