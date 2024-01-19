package com.example.projeto;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projeto.listeners.LoginListener;
import com.example.projeto.modelo.SingletonGestorApp;


public class LoginActivity extends AppCompatActivity implements LoginListener {

    private EditText etUser, etPassword;
    private SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");
        if (isUserAuthenticated()) {
            redirectToMainActivity();
        }
        SingletonGestorApp.getInstance(getApplicationContext()).setLoginListener(this);
        sharedPreferences = getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
        etUser=findViewById(R.id.etUser);
        etPassword=findViewById(R.id.etPassword);
    }
    private boolean isUserAuthenticated() {
        SharedPreferences sharedPreferences = getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("TOKEN", "");
        return !token.isEmpty();
    }

    private void redirectToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void onClickLogin(View view) {
        String username=etUser.getText().toString();
        String password=etPassword.getText().toString();
        SingletonGestorApp.getInstance(getApplicationContext()).loginAPI(username, password, LoginActivity.this);
    }

    @Override
    public void onRefreshLogin(boolean success, String mensagem, String token, String email, String role, int user_id) {
        if (success) {
            Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
            Intent Intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(Intent);
            finish();
        } else {
            Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
        }
    }
}
