package com.example.mobile_project;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class PostFragment extends Fragment {
    private EditText etTitle, etDescription;
    private Spinner spinnerOfferType;
    private Button btnSubmit;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        // Initialize Firebase instances
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        etTitle = view.findViewById(R.id.etTitle);
        etDescription = view.findViewById(R.id.etDescription);
        spinnerOfferType = view.findViewById(R.id.spinnerOfferType);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(v -> {
            String title = etTitle.getText().toString();
            String description = etDescription.getText().toString();
            String offerType = spinnerOfferType.getSelectedItem().toString();

            if (auth.getCurrentUser() == null) {
                Toast.makeText(getContext(), "User not authenticated", Toast.LENGTH_SHORT).show();
                return;
            }

            // Fetch the current user's name from the 'users' collection
            String userEmail = auth.getCurrentUser().getEmail();
            db.collection("users")
                    .whereEqualTo("email", userEmail)
                    .get()
                    .addOnSuccessListener(querySnapshot -> {
                        String name = "Unknown User"; // Default value in case name isn't found
                        for (QueryDocumentSnapshot document : querySnapshot) {
                            name = document.getString("name");
                            break; // We only need the first match
                        }

                        // Create the Offer object with the user's name
                        Offer offer = new Offer(title, description, name, offerType);

                        // Save the offer in the 'offers' collection
                        CollectionReference offersCollection = db.collection("offers");
                        offersCollection.add(offer)
                                .addOnSuccessListener(documentReference -> {
                                    // Redirect to HomeFragment after successfully saving the offer
                                    Toast.makeText(getContext(), "Offer posted successfully!", Toast.LENGTH_SHORT).show();
                                    getParentFragmentManager().beginTransaction()
                                            .replace(R.id.homeFragment, new HomeFragment())
                                            .commit();
                                })
                                .addOnFailureListener(e -> {
                                    // Handle failure
                                    Toast.makeText(getContext(), "Error saving offer", Toast.LENGTH_SHORT).show();
                                });
                    })
                    .addOnFailureListener(e -> {
                        // Handle failure in fetching user data
                        Toast.makeText(getContext(), "Error fetching user details: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });

        return view;
    }
}
