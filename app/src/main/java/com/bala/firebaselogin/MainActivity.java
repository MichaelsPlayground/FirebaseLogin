package com.bala.firebaselogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button logout, changePassword;
    public FirebaseAuth mAuth;
    public FirebaseUser currentUser;
    Intent  changePasswordIntent;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logout = findViewById(R.id.buttonLogout);
        changePassword = findViewById(R.id.buttonChangePassword);
        changePasswordIntent = new Intent(MainActivity.this, ChangePasswordActivity.class);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            currentUser = mAuth.getCurrentUser();
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentUser != null) {
                    FirebaseAuth.getInstance().signOut();
                    System.out.println("User was loged out");
                }
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(changePasswordIntent);
            }
        });
    }
}
