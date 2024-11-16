package com.example.mobile_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUp_client extends AppCompatActivity {

    private EditText name, email, password, localisation, college, degree, speciality;
    private CheckBox termsCheckBox;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up_client);

        firestore = FirebaseFirestore.getInstance();
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.editTextTextPassword);
        localisation = findViewById(R.id.localisation);
        college = findViewById(R.id.college);
        degree = findViewById(R.id.degree);
        speciality = findViewById(R.id.speciality);
        termsCheckBox = findViewById(R.id.checkBox);
        Button signInButton = findViewById(R.id.SignInButtonClient);
        TextView loginTextView = findViewById(R.id.LogInNavigate);

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp_client.this, Login_client.class);
                startActivity(intent);
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameValue = name.getText().toString();
                String emailValue = email.getText().toString();
                String passwordValue = password.getText().toString();

                if (nameValue.isEmpty() || emailValue.isEmpty() || passwordValue.isEmpty()) {
                    Toast.makeText(SignUp_client.this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!termsCheckBox.isChecked()) {
                    Toast.makeText(SignUp_client.this, "Please accept the terms and conditions", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Sign up with Firebase Authentication
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailValue, passwordValue)
                        .addOnCompleteListener(SignUp_client.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    post();
                                    Intent intent = new Intent(SignUp_client.this, WelcomePage.class);
                                    startActivity(intent);
                                } else {

                                    String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                                    if (errorMessage.contains("email address is already in use")) {
                                        Toast.makeText(SignUp_client.this, "This email is already registered. Please log in.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(SignUp_client.this, "Sign up failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            }
        });

    }

    private void post() {
        String nameValue = name.getText().toString();
        String emailValue = email.getText().toString() ;
        String localisationValue = localisation.getText().toString();
        String collegeValue = college.getText().toString();
        String degreeValue = degree.getText().toString();
        String specialityValue = speciality.getText().toString();

        Map<String, Object> internship_seeker = new HashMap<>();
        internship_seeker.put("name", nameValue);
        internship_seeker.put("email", emailValue);
        internship_seeker.put("localisation", localisationValue);
        internship_seeker.put("college", collegeValue);
        internship_seeker.put("degree", degreeValue);
        internship_seeker.put("speciality", specialityValue);

        firestore.collection("internship_seeker").add(internship_seeker)
                .addOnSuccessListener(documentReference -> Toast.makeText(SignUp_client.this, "Sign Up Successful!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(SignUp_client.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
