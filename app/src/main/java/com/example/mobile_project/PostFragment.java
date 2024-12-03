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

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class PostFragment extends Fragment {
    private EditText etTitle, etDescription;
    private Spinner spinnerOfferType;
    private Button btnSubmit;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);


        db = FirebaseFirestore.getInstance();

        etTitle = view.findViewById(R.id.etTitle);
        etDescription = view.findViewById(R.id.etDescription);
        spinnerOfferType = view.findViewById(R.id.spinnerOfferType);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(v -> {
            String title = etTitle.getText().toString();
            String description = etDescription.getText().toString();
            String offerType = spinnerOfferType.getSelectedItem().toString();
            String name = "offer";


            Offer offer = new Offer(title, description, name, offerType);

            CollectionReference offersCollection = db.collection("offers");


            offersCollection.add(offer)
                    .addOnSuccessListener(documentReference -> {
                        // Redirect to HomeFragment after successfully saving the offer
                        getParentFragmentManager().beginTransaction()
                                .replace(R.id.homeFragment, new HomeFragment())
                                .commit();
                    })
                    .addOnFailureListener(e -> {
                        // Handle failure
                        Toast.makeText(getContext(), "Error saving offer", Toast.LENGTH_SHORT).show();
                    });
        });

        return view;
    }
}