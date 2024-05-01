package com.example.bottomnavapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNAv = findViewById(R.id.bottomNavigationView);

        bottomNAv.setOnItemSelectedListener(item -> {
            Fragment fragemnt = null;

            switch (item.getItemId()){
                case R.id.firstFragment:
                    fragemnt = new FirstFragment();
                    break;
                case R.id.secondFragment:
                    fragemnt = new secondFragment();
                    break;
                case R.id.thirdFragment:
                    fragemnt = new thirdFragment();
                    break;
            }

            if(getSupportFragmentManager().getBackStackEntryCount() > 4) {

                getSupportFragmentManager().popBackStack();
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container,fragemnt).addToBackStack(null).commit();
            return true;
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container,new FirstFragment()).commit();
        bottomNAv.setSelectedItemId(R.id.firstFragment);
    }

}