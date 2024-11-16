package com.example.mobile_project;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class WelcomePage extends AppCompatActivity {

    private TextView textView6;
    private FirebaseFirestore firestore;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome_page);
        textView6 = findViewById(R.id.textView6);
        firestore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    private void fetchAndDisplayUserName() {
        String userEmail = user.getEmail();
        firestore.collection("internship_seeker")
                .whereEqualTo("email", userEmail)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        // Get the first document (should only be one)
                        DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);

                        // Get the user's name and display it
                        String userName = document.getString("name");
                        textView6.setText("Welcome, " + userName + "!");
                    } else {
                        textView6.setText("Welcome, User!");
                    }
                })
                .addOnFailureListener(e -> textView6.setText("Error fetching user data!"));
    }
}
