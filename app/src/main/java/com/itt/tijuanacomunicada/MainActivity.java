package com.itt.tijuanacomunicada;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.itt.tijuanacomunicada.services.AuthService;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    // Inflaters
    private EditText tbxEmail;
    private EditText tbxPassword;
    private AppCompatButton btnLogin;
    private TextView btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        tbxEmail = findViewById(R.id.tbxEmail);
        tbxPassword = findViewById(R.id.tbxPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = tbxEmail.getText().toString();
                final String password = tbxPassword.getText().toString();
                AuthService.Login(MainActivity.this, email, password);
                GoToPanel();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void GoToPanel() {
        if (AuthService.IsAuth()) {
            Intent intent = new Intent(MainActivity.this, PanelActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        GoToPanel();
        super.onStart();
    }
}