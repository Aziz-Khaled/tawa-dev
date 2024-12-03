package com.example.mobile_project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView rvOffers;
    private OfferAdapter adapter;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        rvOffers = view.findViewById(R.id.rvOffers);
        rvOffers.setLayoutManager(new LinearLayoutManager(getContext()));

        // Fetch offers from Firestore
        db.collection("offers")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Offer> offers = queryDocumentSnapshots.toObjects(Offer.class);
                    // Set the adapter with the fetched offers
                    adapter = new OfferAdapter(offers);
                    rvOffers.setAdapter(adapter);
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                    Toast.makeText(getContext(), "Error loading offers", Toast.LENGTH_SHORT).show();
                });

        return view;
    }
}
