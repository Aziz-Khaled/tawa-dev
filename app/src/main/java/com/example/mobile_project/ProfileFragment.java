package com.example.mobile_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileFragment extends Fragment {

    private TextView profileUsername, EmailUsername;
    private FirebaseFirestore firestore;
    private FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firestore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        profileUsername = view.findViewById(R.id.profileUsername);
        EmailUsername = view.findViewById(R.id.userEmail);

        Button logout = view.findViewById(R.id.logout);
        logout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(getContext(), Login_client.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            requireActivity().finish();
        });

        fetchAndDisplayUserData();
    }

    private void fetchAndDisplayUserData() {
        if (user != null) {
            String userEmail = user.getEmail();
            EmailUsername.setText(userEmail);

            // Fetch user data from the 'users' collection
            firestore.collection("users")
                    .whereEqualTo("email", userEmail)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                            String userName = document.getString("name");

                            // Set the user's name in the profileUsername TextView
                            profileUsername.setText(userName);
                        } else {
                            profileUsername.setText("User");
                        }
                    })
                    .addOnFailureListener(e -> {
                        profileUsername.setText("Error fetching user data");
                        EmailUsername.setText("Error fetching email");
                    });
        } else {
            profileUsername.setText("Guest");
            EmailUsername.setText("No email available");
        }
    }
}
