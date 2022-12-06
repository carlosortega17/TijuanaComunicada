package com.itt.tijuanacomunicada;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.itt.tijuanacomunicada.services.AuthService;
import com.itt.tijuanacomunicada.tabs.HoleFragment;
import com.itt.tijuanacomunicada.tabs.HomeFragment;
import com.itt.tijuanacomunicada.tabs.ProfileFragment;

public class PanelActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment()).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment temp = null;
                switch (item.getItemId())
                {
                    case R.id.homeFragment : temp = new HomeFragment();
                        break;
                    case R.id.holeFragment : temp = new HoleFragment();
                        break;
                    case R.id.profileFragment : temp = new ProfileFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, temp).addToBackStack(null).commit();
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        if (!AuthService.IsAuth()) {
            Intent intent = new Intent(PanelActivity.this, MainActivity.class);
            startActivity(intent);
        }
        super.onStart();
    }
}