package com.example.projeto.modelo;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.projeto.MainActivity;
import com.example.projeto.listeners.ArtigoListener;
import com.example.projeto.listeners.ArtigosListener;
import com.example.projeto.listeners.LoginListener;
import com.example.projeto.utils.ArtigoJsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SingletonGestorApp {
    private ArrayList<Artigo> artigos;
    private static SingletonGestorApp instance = null;
    private ArtigoBDHelper artigoBD=null;
    private static RequestQueue volleyQueue=null;
    private String UrlAPI = null;
    private String TOKEN = null;
    private ArtigosListener artigosListener;
    private ArtigoListener artigoListener;
    private LoginListener loginListener;
    private SharedPreferences sharedPrefUser;
    private SharedPreferences sharedPrefLink;


    public static synchronized SingletonGestorApp getInstance(Context context){
        if(instance == null) {
            instance = new SingletonGestorApp(context);
            volleyQueue = Volley.newRequestQueue(context);
        }
        return instance;
    }

    private SingletonGestorApp(Context context){
        artigos= new ArrayList<>();
        artigoBD=new ArtigoBDHelper(context);
    }

    public void setArtigoListener(ArtigoListener artigoListener) {
        this.artigoListener = artigoListener;
    }

    public void setArtigosListener(ArtigosListener artigosListener) {
        this.artigosListener = artigosListener;
    }

    public void setLoginListener(LoginListener loginListener){
        this.loginListener = loginListener;
    }

    public Artigo getArtigo(int id){
        for (Artigo a: artigos){
            if (a.getId()==id)
                return a;
        }
        return null;
    }

    public static boolean isConnectionInternet(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni!=null && ni.isConnected();
    }
    public void setUrlAPI(String link, final Context context){
        sharedPrefLink = context.getSharedPreferences("DADOS_LINK", Context.MODE_PRIVATE);
        if (sharedPrefLink != null) {
            SharedPreferences.Editor editor = sharedPrefLink.edit();
            editor.putString("LINK", "http://"+link+"/ProjetoPSI_/backend/web/api");
            editor.apply();
        }
    }
    public void getToken(final Context context){
        sharedPrefUser = context.getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
        if (sharedPrefUser != null) {
            TOKEN = sharedPrefUser.getString("TOKEN", "token");
        }
    }
    public void getUrlAPI(final Context context){
        sharedPrefLink = context.getSharedPreferences("DADOS_LINK", Context.MODE_PRIVATE);
        if (sharedPrefLink != null) {
            UrlAPI = sharedPrefLink.getString("LINK", "link");
        }
    }


//region LOGIN
    public void loginAPI(String username, String password, final Context context){
        if (!isConnectionInternet(context))
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        else {
            getUrlAPI(context);
            StringRequest req = new StringRequest(Request.Method.GET, UrlAPI +"/user/login", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);

                        boolean success = jsonResponse.getBoolean("success");
                        String token = jsonResponse.getString("token");
                        String email = jsonResponse.getString("email");
                        String role = jsonResponse.getString("role");
                        String mensagem = jsonResponse.getString("message");

                        if (loginListener != null) {
                            sharedPrefUser = context.getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
                            if (sharedPrefUser != null) {
                                SharedPreferences.Editor editor = sharedPrefUser.edit();
                                editor.putString("TOKEN", token);
                                editor.putString("EMAIL", email);
                                editor.putString("ROLE", role);
                                editor.apply();
                            }
                            loginListener.onRefreshLogin(success, mensagem, token, email, role);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "User Inválido", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    headers.put("username", username);
                    headers.put("password", password);
                    return headers;
                }
            };
            volleyQueue.add(req);
        }
    }

//endregion
//region ACESSOS BD ARTIGOS
    public ArrayList<Artigo> getArtigosBD(){
        artigos = artigoBD.getAllArtigosBD();
        return new ArrayList<>(artigos);
    }
    public void adicionarArtigoBD(Artigo artigo){
        artigoBD.adicionarArtigoBD(artigo);
    }

    public void adicionarAllArtigosBD(ArrayList<Artigo> artigos) {
        artigoBD.removerAllArtigosBD();

        for (Artigo artigo : artigos) {
            adicionarArtigoBD(artigo);
        }
    }

    public void editarArtigoBD(Artigo artigo){
        if (artigo!=null)
            artigoBD.editarArtigoBD(artigo);
    }
    public void removerArtigoBD(int idartigo){
        artigoBD.removerArtigoBD(idartigo);
    }
//endregion
//region PEDIDOS API ARTIGOS
    public void adicionarArtigoAPI(final Artigo artigo, final Context context){
        if(!isConnectionInternet(context))
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        else {
            getUrlAPI(context);
            getToken(context);
            StringRequest req = new StringRequest(Request.Method.POST, UrlAPI + "/artigo/adicionarartigo?access-token="+TOKEN, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // guardar na bdlocal para funcionar offline
                    adicionarArtigoBD(ArtigoJsonParser.parserJsonArtigo(response));
                    // informar a vista
                    if (artigoListener != null)
                        artigoListener.onRefreshDetalhes(MainActivity.ADD);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Erro ao aceder a API", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public byte[] getBody(){
                    String json = ArtigoJsonParser.artigoParaJson(artigo);
                    return json.getBytes();
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };
            volleyQueue.add(req);
        }
    }

    public void getAllArtigosAPI(final Context context){
        if(!isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
            // se nao tem internet vai buscar os livros a base de dados
            artigos=artigoBD.getAllArtigosBD();

            if (artigosListener!=null)
                artigosListener.onRefreshListaArtigos(artigos);
        }
        else {
            getUrlAPI(context);
            getToken(context);
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, UrlAPI +"/artigo?access-token="+TOKEN, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    artigos=ArtigoJsonParser.parserJsonArtigos(response);
                    adicionarAllArtigosBD(artigos);

                    // para informar a vista
                    if (artigosListener!=null)
                        artigosListener.onRefreshListaArtigos(artigos);
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    artigos=artigoBD.getAllArtigosBD();
                    Toast.makeText(context, "Erro ao aceder a API", Toast.LENGTH_SHORT).show();
                }


            });
            volleyQueue.add(req);
        }
    }

    public void removerArtigoAPI(final Artigo artigo, final Context context){
        if(!isConnectionInternet(context))
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        else
        {
            getUrlAPI(context);
            getToken(context);
            StringRequest req = new StringRequest(Request.Method.DELETE, UrlAPI + "/artigo/eliminarartigo/"+ artigo.getId() +"?access-token="+TOKEN, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // atualizar a BDLocal
                    removerArtigoBD(artigo.getId());
                    // informar a vista
                    if (artigoListener!=null)
                        artigoListener.onRefreshDetalhes(MainActivity.DELETE);
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    artigos=artigoBD.getAllArtigosBD();
                    Toast.makeText(context, "Erro ao aceder a API", Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(req);
        }
    }

    public void editarArtigoAPI(final Artigo artigo, final Context context){
        if(!isConnectionInternet(context))
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        else {
            getUrlAPI(context);
            getToken(context);
            StringRequest req = new StringRequest(Request.Method.PUT, UrlAPI + "/artigo/editarartigo/"+ artigo.getId() +"?access-token="+TOKEN, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // guardar na bdlocal para funcionar offline
                    adicionarArtigoBD(ArtigoJsonParser.parserJsonArtigo(response));
                    // informar a vista
                    if (artigoListener != null)
                        artigoListener.onRefreshDetalhes(MainActivity.ADD);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Erro ao aceder a API", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public byte[] getBody(){
                    String json = ArtigoJsonParser.artigoParaJson(artigo);
                    return json.getBytes();
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };
            volleyQueue.add(req);
        }
    }
//endregion

}
