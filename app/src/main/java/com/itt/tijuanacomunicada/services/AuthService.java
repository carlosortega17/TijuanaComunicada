package com.itt.tijuanacomunicada.services;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.itt.tijuanacomunicada.MainActivity;

public class AuthService {
    private static FirebaseUser user = null;
    private static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public static boolean IsAuth() {
        if ((user = firebaseAuth.getCurrentUser()) != null) {
            return true;
        }
        return false;
    }

    public static void Login(AppCompatActivity activity, String email, String password) {
        if (!email.isEmpty() && !password.isEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                    activity,
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                user = firebaseAuth.getCurrentUser();
                            }
                        }
                    }
            ).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(activity, "Debe llenar todos los campos", Toast.LENGTH_LONG).show();
        }
    }

    public static void Logout() {
        if (IsAuth()) {
            firebaseAuth.signOut();
            user = null;
        }
    }

    public static String CurrentUser() {
        if (IsAuth()) {
            return user.getEmail();
        }
        return null;
    }
}
