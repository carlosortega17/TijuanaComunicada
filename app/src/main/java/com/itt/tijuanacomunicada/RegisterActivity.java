package com.itt.tijuanacomunicada;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private EditText tbxEmail;
    private EditText tbxPassword;
    private EditText tbxPasswordR;
    private AppCompatButton btnRegister;
    private TextView btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();
        tbxEmail = findViewById(R.id.tbxEmail);
        tbxPassword = findViewById(R.id.tbxPassword);
        tbxPasswordR = findViewById(R.id.tbxPasswordR);
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        if (firebaseAuth.getCurrentUser() != null) {
            Intent intent = new Intent(RegisterActivity.this, PanelActivity.class);
            startActivity(intent);
        }
        super.onStart();
    }

    private void register() {
        final String email = tbxEmail.getText().toString();
        final String password = tbxPassword.getText().toString();
        final String passwordR = tbxPasswordR.getText().toString();
        if (!email.isEmpty() && !password.isEmpty() && !passwordR.isEmpty()) {
            if (password.equals(passwordR)) {
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Success", Toast.LENGTH_LONG).show();
                            tbxEmail.setText("");
                            tbxPassword.setText("");
                            tbxPasswordR.setText("");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            } else  {
                Toast.makeText(RegisterActivity.this, "Las contraseñas no coiciden", Toast.LENGTH_LONG).show();
            }
        } else  {
            Toast.makeText(RegisterActivity.this, "Se deben llenar todos los campos", Toast.LENGTH_LONG).show();
        }
    }
}