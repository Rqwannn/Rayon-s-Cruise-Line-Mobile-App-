package com.example.rayons;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

public class Beranda extends AppCompatActivity {
    SharedPreferences ShredRef;
    SharedPreferences.Editor editor;
    BottomNavigationView BtnView;
    Fragment fragment;
    int ForceClose = 0;

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

        if (ShredRef.getString("FragmentS", null) != null){
            String Name = ShredRef.getString("FragmentS", null);
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
                    if (!ShredRef.getString("FragmentS", "").equals("Kapal")){
                        fragment = new Ship();
                        animFragment(fragment, "Kapal");
                        return true;
                    }
                } else if (item.getItemId() == R.id.btn_home){
                    if (!ShredRef.getString("FragmentS", "").equals("Home")){
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
        editor.putString("FragmentS", Text);
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

            if (ForceClose == 0){
                Toast.makeText(Beranda.this, "Tekan lagi untuk keluar", Toast.LENGTH_LONG).show();
                ForceClose++;

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ForceClose = 0;
                    }
                }, 3600);

            } else {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }

            if (ShredRef.getInt("Submit", 0) == 1){
                return false;
            } else {
                return true;
            }
        }
        return true;
    }
}