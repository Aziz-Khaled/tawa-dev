package com.example.mobile_project;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.mobile_project.databinding.ActivityWelcomePageBinding;



public class WelcomePage extends AppCompatActivity {

    private ActivityWelcomePageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set up View Binding
        binding = ActivityWelcomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Load the default fragment (HomeFragment)
        if (savedInstanceState == null) {
            replaceFragment(new HomeFragment());
        }
        // Set up Bottom Navigation
        setupBottomNavigation();
    }

    private void setupBottomNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.home) {
                selectedFragment = new HomeFragment();
                Log.d("Navigation", "Home Fragment Selected");
            } else if (item.getItemId() == R.id.profile) {
                selectedFragment = new ProfileFragment();
                Log.d("Navigation", "Profile Fragment Selected");
            } else if (item.getItemId() == R.id.Add) {
                selectedFragment = new PostFragment();
                Log.d("Navigation", "Notifications Fragment Selected");
            }

            if (selectedFragment != null) {
                replaceFragment(selectedFragment);
                return true;
            }

            return false;
        });
    }
    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }
}
