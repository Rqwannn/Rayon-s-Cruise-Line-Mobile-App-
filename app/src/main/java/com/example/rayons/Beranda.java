package com.example.rayons;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Beranda extends AppCompatActivity {
    SharedPreferences ShredRef;
    SharedPreferences.Editor editor;
    TextView UsernameSession;
    BottomNavigationView BtnView;
    NestedScrollView Parent;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);

        ShredRef = getSharedPreferences("SESSION", MODE_PRIVATE);
        getSupportActionBar().hide();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setStatusBarColor(getColor(R.color.white));
        }

        fragment = new Home();
        loadFragment(fragment);

        BtnView = findViewById(R.id.bottom_navigation_view);
        BtnView.setBackground(null);
        BtnView.setSelectedItemId(R.id.btn_home);

        if (ShredRef.getString("Fragment", null) != null){
            String Name = ShredRef.getString("Fragment", null);
            if (Name.equals("Kapal")){
                fragment = new Ship();
                loadFragment(fragment);
                BtnView.setSelectedItemId(R.id.btn_ship);
            }
        }

        BtnView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.btn_ship){
                    if (!ShredRef.getString("Fragment", null).equals("Kapal")){
                        fragment = new Ship();
                        animFragment(fragment, "Kapal");
                        return true;
                    }
                } else if (item.getItemId() == R.id.btn_home){
                    if (!ShredRef.getString("Fragment", null).equals("Home")){
                        fragment = new Home();
                        animFragment(fragment, "Home");
                        return true;
                    }
                }
                return false;
            }
        });
    }

    public void animFragment(Fragment fragment, String Text){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_left_to_right, R.anim.exit_left_to_right, R.anim.enter_right_to_left, R.anim.exit_right_to_left);
        transaction.replace(R.id.parent_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        editor = ShredRef.edit();
        editor.putString("Fragment", Text);
        editor.apply();
    }

    public void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.parent_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onKeyDown(int key_code, KeyEvent key_event) {
        ShredRef = getSharedPreferences("SESSION", MODE_PRIVATE);

        if (key_code== KeyEvent.KEYCODE_BACK) {
            super.onKeyDown(key_code, key_event);
            if (ShredRef.getInt("Submit", 0) == 1){
                return false;
            } else {
                return true;
            }
        }
        return true;
    }
}