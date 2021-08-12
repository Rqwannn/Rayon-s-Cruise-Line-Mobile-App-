package com.example.rayons;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    TextInputLayout Username, Password;
    TextInputEditText UsernameField, PasswordField;
    ImageView Logo;
    TextView SignUp;
    MaterialButton Login;
    CheckBox Checked;
    SharedPreferences ShredRef;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((MainActivity) this).getSupportActionBar().setTitle("Login");
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

//        int orientation = getResources().getConfiguration().orientation;
//        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            // In landscape
//        } else {
//            // In Potrait
//        }

        UsernameField = findViewById(R.id.username_field);
        PasswordField = findViewById(R.id.password_field);
        Login = findViewById(R.id.btn_login);
        Checked = findViewById(R.id.check);

        ShredRef = getSharedPreferences("SESSION", MODE_PRIVATE);

        if (ShredRef.getBoolean("RememberMe", false)){
            startActivity(new Intent(MainActivity.this, Beranda.class));
        }

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UsernameField.getText().length() == 0 || PasswordField.getText().length() == 0){
                    Toast.makeText(MainActivity.this, "Isi Field Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                } else {
                    String Username = UsernameField.getText().toString();
                    boolean Check = Checked.isChecked();

                    editor = ShredRef.edit();
                    editor.putInt("Submit", 1);
                    editor.putString("Username", Username);
                    editor.putBoolean("RememberMe", Check);
                    editor.apply();

                    Toast.makeText(MainActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, Beranda.class));
                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.getDecorView().setSystemUiVisibility(0);
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        SignUp = findViewById(R.id.sign_up);

        SignUp.setOnClickListener(data -> {
            Intent MoveActivity = new Intent(getApplicationContext(), Register.class);
            startActivity(MoveActivity);
        });
    }

    @Override
    public boolean onKeyDown(int key_code, KeyEvent key_event) {
        ShredRef = getSharedPreferences("SESSION", MODE_PRIVATE);

        if (key_code== KeyEvent.KEYCODE_BACK) {
            super.onKeyDown(key_code, key_event);
            if (ShredRef.getInt("Submit", 0) != 1){
                return false;
            } else {
                return true;
            }
        }
        return true;
    }
}