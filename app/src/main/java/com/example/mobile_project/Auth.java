package com.example.mobile_project;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class Auth extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singup);
        Button  companyB = findViewById(R.id.companyButton);
        Button  clientB = findViewById(R.id.clientButton);

        clientB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Auth.this, SignUp_client.class);
                startActivity(intent);
            }
        });

        companyB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Auth.this, SignUp_company.class);
                startActivity(intent);
            }
        });
    }

}
