package com.javaguides.Learning.platform.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.javaguides.Learning.platform.model.Student;

@Service
public class FirebaseService {

    private static final String COLLECTION_NAME = "students";

    public String saveStudentToFirebase(Student student) throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        Map<String, Object> studentData = new HashMap<>();
        studentData.put("fullName", student.getFullName());
        studentData.put("email", student.getEmail());
        // Note: In a real application, you should never store plain passwords
        // This is for demonstration only
        studentData.put("interests", student.getInterests());
        studentData.put("agreeToTerms", student.isAgreeToTerms());
        studentData.put("createdAt", java.time.LocalDateTime.now().toString());

        // Create a new document with auto-generated ID
        DocumentReference documentReference = dbFirestore.collection(COLLECTION_NAME).document();
        ApiFuture<WriteResult> collectionsApiFuture = documentReference.set(studentData);

        // Return the document ID
        return documentReference.getId();
    }
}