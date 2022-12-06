package com.itt.tijuanacomunicada;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.itt.tijuanacomunicada.services.AuthService;
import com.itt.tijuanacomunicada.tabs.HoleFragment;
import com.itt.tijuanacomunicada.tabs.HomeFragment;
import com.itt.tijuanacomunicada.tabs.ProfileFragment;

public class PanelActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    public static FragmentManager fragmentManager;
    public static int currentTab = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        fragmentManager = getSupportFragmentManager();
        if (currentTab == 0) {
            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment()).commit();
            bottomNavigationView.setSelectedItemId(R.id.homeFragment);
        }
        if (currentTab == 1) {
            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, new HoleFragment()).commit();
            bottomNavigationView.setSelectedItemId(R.id.holeFragment);
        }
        if (currentTab == 2) {
            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, new ProfileFragment()).commit();
            bottomNavigationView.setSelectedItemId(R.id.profileFragment);
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.homeFragment : {
                        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment()).addToBackStack(null).commit();
                        break;
                    }
                    case R.id.holeFragment : {
                        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, new HoleFragment()).addToBackStack(null).commit();
                        break;
                    }
                    case R.id.profileFragment : {
                        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, new ProfileFragment()).addToBackStack(null).commit();
                        break;
                    }
                }
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

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
    }
}