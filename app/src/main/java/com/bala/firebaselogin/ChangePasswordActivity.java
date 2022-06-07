package com.bala.firebaselogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText email, oldPassword, newPassword;
    Button  changePassword;
    TextView changePasswordErrorView;
    private FirebaseUser user;
    Intent mainActivityIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        email = findViewById(R.id.changePasswordEmailTextInput);
        oldPassword = findViewById(R.id.changePasswordOldTextInput);
        newPassword = findViewById(R.id.changePasswordNewTextInput);
        changePassword = findViewById(R.id.changePasswordButton);
        changePasswordErrorView = findViewById(R.id.changePasswordErrorView);
        mainActivityIntent = new Intent(ChangePasswordActivity.this, MainActivity.class);

        // get email from loged in user
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            final String emailFromUser = user.getEmail();
            email.setText(emailFromUser);
        }

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (user != null) {
                final String emailFromUser = user.getEmail();
                String oldPass = oldPassword.getText().toString();
                final String newPass = newPassword.getText().toString();
                AuthCredential credential = EmailAuthProvider.getCredential(emailFromUser,oldPass);

                user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(!task.isSuccessful()){
                                        changePasswordErrorView.setText("Something went wrong. Please try again later");
                                        Toast.makeText(ChangePasswordActivity.this, "Something went wrong. Please try again later",
                                                Toast.LENGTH_SHORT).show();
                                    }else {
                                        changePasswordErrorView.setText("Password Successfully Modified");
                                        Toast.makeText(ChangePasswordActivity.this, "Password Successfully Modified",
                                                Toast.LENGTH_SHORT).show();
                                        setResult(RESULT_OK, null);
                                        startActivity(mainActivityIntent);
                                        ChangePasswordActivity.this.finish();
                                    }
                                }
                            });
                        }else {
                            changePasswordErrorView.setText("Authentication Failed");
                            Toast.makeText(ChangePasswordActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }

        });
    }
}