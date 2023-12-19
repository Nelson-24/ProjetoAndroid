package com.example.projeto;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    public static final String EMAIL = "email";
    private EditText etEmail, etPassword;
    private final int MIN_PASS = 6;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
    }
    private boolean isEmailValid(String email){
        if(email==null)
            return false;
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid(String pass){
        if(pass==null)
            return false;
        return pass.length()>=MIN_PASS;
    }

    public void onClickLogin(View view) {
        String email=etEmail.getText().toString();
        String pass=etPassword.getText().toString();

        if (!isEmailValid(email)) {
            etEmail.setError("Email inválido");
            return;
        }
        if (!isPasswordValid(pass)) {
            etPassword.setError("Password inválido");
            return;
        }
        //Toast.makeText(this,"Login efetuado com sucesso", Toast.LENGTH_LONG).show();

        Intent Intent=new Intent(getApplicationContext(),MainActivity.class);
        Intent.putExtra(EMAIL,email);
        startActivity(Intent);
        finish();
    }
}
