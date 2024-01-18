package com.example.projeto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.example.projeto.modelo.SingletonGestorApp;

public class LinkActivity extends AppCompatActivity {

    private EditText etLink;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);
        SingletonGestorApp.getInstance(getApplicationContext());
        sharedPreferences = getSharedPreferences("DADOS_LINK", Context.MODE_PRIVATE);
        etLink=findViewById(R.id.etLink);
    }

    public void onClickEnviar(View view) {
        String link=etLink.getText().toString();
        SingletonGestorApp.getInstance(getApplicationContext()).setUrlAPI(link, LinkActivity.this);
        Toast.makeText(this, "Endereço Configurado: "+link, Toast.LENGTH_SHORT).show();
        Intent Intent=new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(Intent);
        finish();
    }
}