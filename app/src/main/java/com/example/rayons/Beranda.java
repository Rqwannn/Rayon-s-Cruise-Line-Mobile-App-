package com.example.rayons;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);

        ShredRef = getSharedPreferences("SESSION", MODE_PRIVATE);
        //  UsernameSession = findViewById(R.id.username_session);
        //  UsernameSession.setText(ShredRef.getString("Username", null));

        getSupportActionBar().hide();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setStatusBarColor(getColor(R.color.white));
        }

        BtnView = findViewById(R.id.bottom_navigation_view);
        BtnView.setBackground(null);

        BtnView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                if (item.getItemId() == R.id.btn_profile){
//                    editor = ShredRef.edit();
//                    editor.clear().commit();
//                    startActivity(new Intent(Beranda.this, MainActivity.class));
//                }
                return false;
            }
        });
    }

    @Override
    public boolean onKeyDown(int key_code, KeyEvent key_event) {
        ShredRef = getSharedPreferences("SESSION", MODE_PRIVATE);

        if (key_code== KeyEvent.KEYCODE_BACK) {
            super.onKeyDown(key_code, key_event);
            if (ShredRef.getBoolean("Submit", false)){
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

}