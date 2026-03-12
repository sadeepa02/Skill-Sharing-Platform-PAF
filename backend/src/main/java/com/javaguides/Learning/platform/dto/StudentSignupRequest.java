package com.javaguides.Learning.platform.dto;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentSignupRequest {
    private String fullName;
    private String email;
    private String password;
    private String interests;
    private boolean agreeToTerms;
}
/*http://localhost:8080/api/students/signup-POST*/
/*{
  "agreeToTerms": true,
  "createdAt": "2025-04-16T14:32:17.481956300",
  "email": "sara@example.com",
  "password": "secure123",
  "fullName": "Sara Johnson",
  "interests": "photography travel cooking"
}*/