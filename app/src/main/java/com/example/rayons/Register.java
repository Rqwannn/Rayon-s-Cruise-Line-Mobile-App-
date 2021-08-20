package com.example.rayons;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

public class Register extends AppCompatActivity {
    TextView SignIn;
    SharedPreferences ShredRef;
    MaterialButton Register;
    TextInputEditText Email, Username, Password;
    TextInputLayout PassLay, UserLay, EmailLay;
    String username, email, password;
    boolean Pass = true;

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

        Register = findViewById(R.id.btn_register);
        Email = findViewById(R.id.email_field);
        Username = findViewById(R.id.username_field);
        Password = findViewById(R.id.password_field);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PassLay = findViewById(R.id.password);
                UserLay = findViewById(R.id.username);
                EmailLay = findViewById(R.id.email);
                Pass = true;

                username = Username.getText().toString();
                email = Email.getText().toString();
                password = Password.getText().toString();

                for (int i = 1; i <= 4; i++){
                    if (Email.getText().toString().trim().equals("") && i == 1){
                        EmailLay.setEndIconMode(TextInputLayout.END_ICON_NONE);
                        Email.setError("Email Harus Di Isi");
                        Pass = false;
                    } else if (Username.getText().toString().trim().equals("") && i == 2) {
                        UserLay.setEndIconMode(TextInputLayout.END_ICON_NONE);
                        Username.setError("Username Harus Di Isi");
                        Pass = false;
                    } else if (Password.getText().toString().trim().equals("") && i == 3){
                        PassLay.setEndIconMode(TextInputLayout.END_ICON_NONE);
                        Password.setError("Password Harus Di Isi");
                        Pass = false;
                    } else if(Pass && i == 4){
                        createData();
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
                if (Password.getText().toString().length() > 0 && !Pass){
                    PassLay.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
                }

                if (Username.getText().toString().length() > 0 && !Pass){
                    UserLay.setEndIconMode(TextInputLayout.END_ICON_CLEAR_TEXT);
                }

                if (Email.getText().toString().length() > 0 && !Pass){
                    EmailLay.setEndIconMode(TextInputLayout.END_ICON_CLEAR_TEXT);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        Password.addTextChangedListener(fieldValidatorTextWatcher);
        Username.addTextChangedListener(fieldValidatorTextWatcher);
        Email.addTextChangedListener(fieldValidatorTextWatcher);

        SignIn = findViewById(R.id.sign_in);

        SignIn.setOnClickListener(data -> {
            Intent MoveActivity = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(MoveActivity);
        });
    }

    private void createData(){
        APIRequest API = RetroServer.KonekRetrofit().create(APIRequest.class);
        Call<ResponseModel> InsertData = API.createUser(
                username,
                email,
                password
        );

        InsertData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                int Kode = response.body().getKode();

                String ErrorPesan = response.body().getPesan() == null ? "" : response.body().getPesan();
                String ErrorUser = response.body().getPesanUsername() == null ? "" : response.body().getPesanUsername();
                String ErrorEmail = response.body().getPesanEmail() == null ? "" : response.body().getPesanEmail();

                if (Kode == 1){
                    Toast.makeText(Register.this, response.body().getPesan(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Register.this, MainActivity.class));
                } else {
                    for (int i = 1; i <= 3; i++){
                        if (ErrorPesan.length() > 0 && i == 1) {
                            Toast.makeText(Register.this, response.body().getPesan(), Toast.LENGTH_SHORT).show();
                        } else if (ErrorUser.length() > 0 && i == 2){
                            UserLay.setEndIconMode(TextInputLayout.END_ICON_NONE);
                            Username.setError(response.body().getPesanUsername());
                            Pass = false;
                        } else if (ErrorEmail.length() > 0 && i == 3){
                            EmailLay.setEndIconMode(TextInputLayout.END_ICON_NONE);
                            Email.setError(response.body().getPesanEmail());
                            Pass = false;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(Register.this,"Gagal Menghubungi Serve" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}