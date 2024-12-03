package com.example.mobile_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUp_company extends AppCompatActivity {

    private EditText nameCompany, email, password, phoneNumber;
    private CheckBox termsCheckBox;
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_company);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        // Bind UI elements
        nameCompany = findViewById(R.id.nameCompany);
        email = findViewById(R.id.editTextTextEmailAddress2);
        password = findViewById(R.id.editTextTextPassword3);
        phoneNumber = findViewById(R.id.editTextPhone);
        termsCheckBox = findViewById(R.id.checkBox2);
        Button signUpButton = findViewById(R.id.button3);
        TextView loginNavigate = findViewById(R.id.logInNavigate2);

        // Navigate to login page
        loginNavigate.setOnClickListener(v -> {
            Intent intent = new Intent(SignUp_company.this, Login_client.class);
            startActivity(intent);
        });

        // Handle Sign-Up button click
        signUpButton.setOnClickListener(v -> {
            String name = nameCompany.getText().toString();
            String emailValue = email.getText().toString();
            String passwordValue = password.getText().toString();
            String phone = phoneNumber.getText().toString();

            // Validation
            if (name.isEmpty() || emailValue.isEmpty() || passwordValue.isEmpty() || phone.isEmpty()) {
                Toast.makeText(SignUp_company.this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!termsCheckBox.isChecked()) {
                Toast.makeText(SignUp_company.this, "Please accept the terms and conditions", Toast.LENGTH_SHORT).show();
                return;
            }

            // Sign up with Firebase Authentication
            auth.createUserWithEmailAndPassword(emailValue, passwordValue)
                    .addOnCompleteListener(SignUp_company.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Save data to Firestore
                                saveCompanyData(name, emailValue, phone);
                                Intent intent = new Intent(SignUp_company.this, WelcomePage.class); // Adjust WelcomePage if needed
                                startActivity(intent);
                                finish();
                            } else {
                                String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                                Toast.makeText(SignUp_company.this, "Sign-up failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });
    }

    private void saveCompanyData(String name, String email, String phone) {
        // Get the current user's unique ID
        String userId = auth.getCurrentUser().getUid();

        // Map company details to Firestore with role as "company"
        Map<String, Object> userData = new HashMap<>();
        userData.put("name", name);
        userData.put("email", email);
        userData.put("phone", phone);
        userData.put("role", "company"); // Add role field
        userData.put("userId", userId); // Store the userId for reference

        // Save to Firestore in "users" collection
        firestore.collection("users").document(userId).set(userData)
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(SignUp_company.this, "Company registered successfully!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(SignUp_company.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
