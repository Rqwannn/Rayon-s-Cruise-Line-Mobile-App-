package com.example.rayons;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class Register extends AppCompatActivity {
    TextView SignIn;
    SharedPreferences ShredRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ((Register) this).getSupportActionBar().setTitle("Register");
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window w = getWindow();
            w.getDecorView().setSystemUiVisibility(0);
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        ShredRef = getSharedPreferences("SESSION", MODE_PRIVATE);

        if (ShredRef.getBoolean("RememberMe", false)){
            startActivity(new Intent(Register.this, Beranda.class));
        }

        SignIn = findViewById(R.id.sign_in);

        SignIn.setOnClickListener(data -> {
            Intent MoveActivity = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(MoveActivity);
        });
    }
}