package com.example.rayons;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rayons.API.APIRequest;
import com.example.rayons.API.RetroServer;
import com.example.rayons.Model.ResponseModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    TextInputLayout Username, Password;
    TextInputEditText UsernameField, PasswordField;
    TextView SignUp;
    MaterialButton Login;
    CheckBox Checked;
    SharedPreferences ShredRef;
    SharedPreferences.Editor editor;
    String username, password;
    boolean Pass = true;

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
        String getStatusFragment = ShredRef.getString("FragmentS", "");
        if (!getStatusFragment.equals("")){
            editor = ShredRef.edit();
            editor.clear().apply();
        }

        if (ShredRef.getBoolean("RememberMe", false)){
            startActivity(new Intent(MainActivity.this, Beranda.class));
        }

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Username = findViewById(R.id.username);
                Password = findViewById(R.id.password);
                Pass = true;

                username = UsernameField.getText().toString();
                password = PasswordField.getText().toString();

                for (int i = 1; i <= 3; i++){
                    if (UsernameField.getText().toString().trim().equals("") && i == 1) {
                        Username.setEndIconMode(TextInputLayout.END_ICON_NONE);
                        UsernameField.setError("Username Harus Di Isi");
                        Pass = false;
                    } else if (PasswordField.getText().toString().trim().equals("") && i == 2){
                        Password.setEndIconMode(TextInputLayout.END_ICON_NONE);
                        PasswordField.setError("Password Harus Di Isi");
                        Pass = false;
                    } else if(Pass && i == 3){
                        AuthUser();
                    }
                }
            }
        });

        TextWatcher fieldValidatorTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (PasswordField.getText().toString().trim().length() > 0 && !Pass){
                    Password.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
                }

                if (UsernameField.getText().toString().trim().length() > 0 && !Pass){
                    Username.setEndIconMode(TextInputLayout.END_ICON_CLEAR_TEXT);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        PasswordField.addTextChangedListener(fieldValidatorTextWatcher);
        UsernameField.addTextChangedListener(fieldValidatorTextWatcher);

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

    public void AuthUser(){
        APIRequest API = RetroServer.KonekRetrofit().create(APIRequest.class);
        Call<ResponseModel> AuthData = API.authUser(
                username,
                password
        );

        AuthData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                int Kode = response.body().getKode();

                String ErrorPesan = response.body().getPesan() == null ? "" : response.body().getPesan();
                String ErrorUser = response.body().getPesanUsername() == null ? "" : response.body().getPesanUsername();
                String ErrorPassword = response.body().getPesanPassword() == null ? "" : response.body().getPesanPassword();

                if (Kode == 1){
                    Toast.makeText(MainActivity.this, ErrorPesan, Toast.LENGTH_SHORT).show();
                    String Username = UsernameField.getText().toString();
                    boolean Check = Checked.isChecked();

                    editor = ShredRef.edit();
                    editor.putInt("Submit", 1);
                    editor.putString("Username", Username);
                    editor.putBoolean("RememberMe", Check);
                    editor.apply();

                    startActivity(new Intent(MainActivity.this, Beranda.class));
                } else {
                    for (int i = 1; i <= 3; i++){
                        if (ErrorPesan.length() > 0 && i == 1) {
                            Toast.makeText(MainActivity.this, response.body().getPesan(), Toast.LENGTH_SHORT).show();
                        } else if (ErrorUser.length() > 0 && i == 2){
                            Username.setEndIconMode(TextInputLayout.END_ICON_NONE);
                            UsernameField.setError(response.body().getPesanUsername());
                            Pass = false;
                        } else if (ErrorPassword.length() > 0 && i == 3){
                            Password.setEndIconMode(TextInputLayout.END_ICON_NONE);
                            PasswordField.setError(response.body().getPesanPassword());
                            Pass = false;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Gagal Menghubungi Server " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
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