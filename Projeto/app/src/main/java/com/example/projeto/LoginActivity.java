package com.example.projeto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;



public class LoginActivity extends AppCompatActivity {

    public static final String USER = "Username";
    private EditText etUser, etPassword;
    private final int MIN_PASS = 6;
    private final int MIN_CHAR = 3;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");
        etUser=findViewById(R.id.etUser);
        etPassword=findViewById(R.id.etPassword);
    }
    private boolean IsUserValid(String user){
        if(user==null)
            return false;
        return user.length()>=MIN_CHAR;
    }

    private boolean isPasswordValid(String pass){
        if(pass==null)
            return false;
        return pass.length()>=MIN_PASS;
    }

    public void onClickLogin(View view) {
        String user=etUser.getText().toString();
        String pass=etPassword.getText().toString();

        if (!IsUserValid(user)) {
            etUser.setError("User inválido");
            return;
        }
        if (!isPasswordValid(pass)) {
            etPassword.setError("Password inválido");
            return;
        }
        //Toast.makeText(this,"Login efetuado com sucesso", Toast.LENGTH_LONG).show();

        Intent Intent=new Intent(getApplicationContext(),MainActivity.class);
        Intent.putExtra(USER,user);
        startActivity(Intent);
        finish();

//        String url = "http://10.0.2.2/ProjetoPSI/Projeto/backend/web/api/user/login";
//        JSONObject jsonBody = new JSONObject();
//        try {
//            jsonBody.put("username", user);
//            jsonBody.put("password", pass);
//            Log.d("API-REQUEST", jsonBody.toString());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        JsonObjectRequest request = new JsonObjectRequest(
//                Request.Method.POST,
//                url,
//                jsonBody,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            boolean success = response.getBoolean("success");
//                            String message = response.getString("message");
//                            Log.d("API-REQUEST", "antes do if");
//                            if (success) {
//                                Log.d("API-REQUEST", "dentro do if");
//                                // Login bem-sucedido
//                                // Recupere os dados adicionais (token, email) da resposta
//                                String token = response.getString("token");
//                                String userEmail = response.getString("email");
//
//                                // Faça o que for necessário com esses dados (por exemplo, armazene-os localmente)
//                                // ...
//
//                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                                intent.putExtra(EMAIL, userEmail);
//                                startActivity(intent);
//                                finish();
//                            } else {
//                                Log.d("API-REQUEST", "dentro do else");
//                                // Exiba uma mensagem de erro
//                                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (JSONException e) {
//                            Log.d("API-REQUEST", "depois do catch");
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // Lidar com erros na chamada à API
//                        Log.d("API-REQUEST", "deu merda");
//                        Toast.makeText(LoginActivity.this, "Erro na chamada à API", Toast.LENGTH_SHORT).show();
//                    }
//                }
//        );
//
//        // Adicione a requisição à fila do Volley
//        Volley.newRequestQueue(this).add(request);
    }
}
