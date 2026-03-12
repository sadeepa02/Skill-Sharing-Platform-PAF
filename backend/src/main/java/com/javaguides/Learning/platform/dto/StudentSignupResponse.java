package com.javaguides.Learning.platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentSignupResponse {
    private String message;
    private boolean success;
    private String userId;

}