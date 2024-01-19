package com.example.projeto.modelo;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
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
import com.example.projeto.listeners.CarrinhoListener;
import com.example.projeto.listeners.CarrinhosListener;
import com.example.projeto.listeners.CategoriaListener;
import com.example.projeto.listeners.CategoriasListener;
import com.example.projeto.listeners.FaturaListener;
import com.example.projeto.listeners.FaturasListener;
import com.example.projeto.listeners.IvaListener;
import com.example.projeto.listeners.IvasListener;
import com.example.projeto.listeners.LinhaCarrinhoListener;
import com.example.projeto.listeners.LinhasCarrinhoListener;
import com.example.projeto.listeners.LoginListener;
import com.example.projeto.listeners.UserListener;
import com.example.projeto.listeners.UsersListener;
import com.example.projeto.utils.ArtigoJsonParser;
import com.example.projeto.utils.CategoriaJsonParser;
import com.example.projeto.utils.FaturaJsonParser;
import com.example.projeto.utils.IvaJsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SingletonGestorApp {
    private ArrayList<Artigo> artigos;
    private ArrayList<Carrinho> carrinhos;
    private ArrayList<Categoria> categorias;
    private ArrayList<Fatura> faturas;
    private ArrayList<Iva> ivas;
    private ArrayList<LinhaCarrinho> linhaCarrinhos;
    private ArrayList<User> users;
    private static SingletonGestorApp instance = null;
    private ArtigoBDHelper artigoBD=null;
    private static RequestQueue volleyQueue=null;
    private String UrlAPI = null;
    private String TOKEN = null;
    private ArtigosListener artigosListener;
    private ArtigoListener artigoListener;
    private CategoriasListener categoriasListener;
    private CategoriaListener categoriaListener;
    private CarrinhosListener carrinhosListener;
    private CarrinhoListener carrinhoListener;

    private FaturasListener faturasListener;
    private FaturaListener faturaListener;

    private IvasListener ivasListener;
    private IvaListener ivaListener;

    private LinhasCarrinhoListener linhasCarrinhoListener;
    private LinhaCarrinhoListener linhaCarrinhoListener;

    private UsersListener usersListener;
    private UserListener userListener;
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

//region SETTERS DOS LISTENERS
    public void setArtigoListener(ArtigoListener artigoListener) {
        this.artigoListener = artigoListener;
    }

    public void setArtigosListener(ArtigosListener artigosListener) {
        this.artigosListener = artigosListener;
    }
    public void setCategoriaListener(CategoriaListener categoriaListener) {
        this.categoriaListener = categoriaListener;
    }

    public void setCategoriasListener(CategoriasListener categoriasListener) {
        this.categoriasListener = categoriasListener;
    }

    public void setCarrinhoListener(CarrinhoListener carrinhoListener) {
        this.carrinhoListener = carrinhoListener;
    }

    public void setCarrinhosListener(CarrinhosListener carrinhosListener) {
        this.carrinhosListener = carrinhosListener;
    }

    public void setFaturaListener(FaturaListener faturaListener) {
        this.faturaListener = faturaListener;
    }

    public void setFaturasListener(FaturasListener faturasListener) {
        this.faturasListener = faturasListener;
    }

    public void setIvaListener(IvaListener ivaListener) {
        this.ivaListener = ivaListener;
    }

    public void setIvasListener(IvasListener ivasListener) {
        this.ivasListener = ivasListener;
    }

    public void setLinhaCarrinhos(LinhaCarrinhoListener linhaCarrinhoListener){
        this.linhaCarrinhoListener = linhaCarrinhoListener;
    }

    public void setLinhasCarrinhos(LinhasCarrinhoListener linhasCarrinhoListener){
        this.linhasCarrinhoListener = linhasCarrinhoListener;
    }

    public void setUserListener(UserListener userListener) {
        this.userListener = userListener;
    }

    public void setUsersListener(UsersListener usersListener) {
        this.usersListener = usersListener;
    }

    public void setLoginListener(LoginListener loginListener){
        this.loginListener = loginListener;
    }
//endregion

//region getId(modelos)
    public Artigo getArtigo(int id){
        for (Artigo artigo: artigos){
            if (artigo.getId()==id)
                return artigo;
        }
        return null;
    }
    public Carrinho getCarrinho(int id){
        for (Carrinho carrinho: carrinhos){
            if (carrinho.getId()==id)
                return carrinho;
        }
        return null;
    }
    public Categoria getCategoria(int id){
        for (Categoria categoria: categorias){
            if (categoria.getId()==id)
                return categoria;
        }
        return null;
    }
    public Fatura getFatura(int id){
        for (Fatura fatura: faturas){
            if (fatura.getId()==id)
                return fatura;
        }
        return null;
    }
    public Iva getIva(int id){
        for (Iva iva: ivas){
            if (iva.getId()==id)
                return iva;
        }
        return null;
    }
    public LinhaCarrinho getLinhaCarrinho(int id){
        for (LinhaCarrinho linhaCarrinho: linhaCarrinhos){
            if (linhaCarrinho.getId()==id)
                return linhaCarrinho;
        }
        return null;
    }
    public User getUser(int id){
        for (User user: users){
            if (user.getId()==id)
                return user;
        }
        return null;
    }
//endregion

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
                        int user_id = jsonResponse.getInt("id");
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
                                editor.putInt("USER_ID", user_id);
                                editor.apply();
                            }
                            loginListener.onRefreshLogin(success, mensagem, token, email, role, user_id);
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
//region PEDIDOS API CARRINHOS
//endregion
//region PEDIDOS API CATEGORIAS
    public void adicionarCategoriaAPI(final Categoria categoria, final Context context){
        if(!isConnectionInternet(context))
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        else {
            getUrlAPI(context);
            getToken(context);
            StringRequest req = new StringRequest(Request.Method.POST, UrlAPI + "/categoria/adicionarcategoria?access-token="+TOKEN, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (categoriaListener != null)
                        categoriaListener.onRefreshDetalhes(MainActivity.ADD);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Erro ao aceder a API", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public byte[] getBody(){
                    String json = CategoriaJsonParser.categoriaParaJson(categoria);
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

    public ArrayList<Categoria> getAllCategoriasAPI(final Context context){
        if(!isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();

        }
        else {
            getUrlAPI(context);
            getToken(context);
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, UrlAPI +"/categoria?access-token="+TOKEN, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    categorias = CategoriaJsonParser.parserJsonCategorias(response);

                    // para informar a vista
                    if (categoriasListener!=null)
                        categoriasListener.onRefreshListaCategorias(categorias);
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Erro ao aceder a API", Toast.LENGTH_SHORT).show();
                }

            });
            volleyQueue.add(req);
        }
        return categorias;
    }

    public void removerCategoriaAPI(final Categoria categoria, final Context context){
        if(!isConnectionInternet(context))
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        else
        {
            getUrlAPI(context);
            getToken(context);
            StringRequest req = new StringRequest(Request.Method.DELETE, UrlAPI + "/categoria/eliminarcategoria/"+ categoria.getId() +"?access-token="+TOKEN, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // informar a vista
                    if (categoriaListener!=null)
                        categoriaListener.onRefreshDetalhes(MainActivity.DELETE);
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Erro ao aceder a API", Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(req);
        }
    }

    public void editarCategoriaAPI(final Categoria categoria, final Context context){
        if(!isConnectionInternet(context))
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        else {
            getUrlAPI(context);
            getToken(context);
            StringRequest req = new StringRequest(Request.Method.PUT, UrlAPI + "/categoria/editarcategoria/"+ categoria.getId() +"?access-token="+TOKEN, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (categoriaListener != null)
                        categoriaListener.onRefreshDetalhes(MainActivity.ADD);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Erro ao aceder a API", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public byte[] getBody(){
                    String json = CategoriaJsonParser.categoriaParaJson(categoria);
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
//region PEDIDOS API FATURAS
    public void adicionarFaturaAPI(final Fatura fatura, final Context context){
        if(!isConnectionInternet(context))
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        else {
            getUrlAPI(context);
            getToken(context);
            StringRequest req = new StringRequest(Request.Method.POST, UrlAPI + "/fatura/adicionarfatura/"+fatura.getId()+"?access-token="+TOKEN, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (faturaListener != null)
                        faturaListener.onRefreshDetalhes(MainActivity.ADD);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Erro ao aceder a API", Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(req);
        }
    }

    public ArrayList<Fatura> getAllFaturasAPI(final Context context){
        if(!isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        }
        else {
            getUrlAPI(context);
            getToken(context);
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, UrlAPI +"/fatura?access-token="+TOKEN, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    faturas = FaturaJsonParser.parserJsonFaturas(response);

                    // para informar a vista
                    if (faturasListener!=null)
                        faturasListener.onRefreshListaFaturas(faturas);
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Erro ao aceder a API", Toast.LENGTH_SHORT).show();
                }

            });
            volleyQueue.add(req);
        }
        return faturas;
    }
    public ArrayList<Fatura> getFaturasClienteAPI(int userId, final Context context){
        if(!isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        }
        else {
            getUrlAPI(context);
            getToken(context);
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, UrlAPI +"/fatura/faturas/"+userId+"?access-token="+TOKEN, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    faturas = FaturaJsonParser.parserJsonFaturas(response);

                    if (faturasListener != null) {
                        faturasListener.onRefreshListaFaturas(faturas);
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Erro na chamada da API", Toast.LENGTH_SHORT).show();
                }

            });
            volleyQueue.add(req);
        }
        return faturas;
    }

    public void anularFaturaAPI(final Fatura fatura, final Context context){
        if(!isConnectionInternet(context))
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        else {
            getUrlAPI(context);
            getToken(context);
            StringRequest req = new StringRequest(Request.Method.PUT, UrlAPI + "/fatura/anularfatura/"+ fatura.getId() +"?access-token="+TOKEN, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (faturaListener != null)
                        faturaListener.onRefreshDetalhes(MainActivity.ADD);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Erro ao aceder a API", Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(req);
        }
    }
//endregion
//region PEDIDOS API IVAS
    public void adicionarIvaAPI(final Iva iva, final Context context){
        if(!isConnectionInternet(context))
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        else {
            getUrlAPI(context);
            getToken(context);
            StringRequest req = new StringRequest(Request.Method.POST, UrlAPI + "/iva/adicionariva?access-token="+TOKEN, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (ivaListener != null)
                        ivaListener.onRefreshDetalhes(MainActivity.ADD);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Erro ao aceder a API", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public byte[] getBody(){
                    String json = IvaJsonParser.ivaParaJson(iva);
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

    public ArrayList<Iva> getAllIvasAPI(final Context context){
        if(!isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        }
        else {
            getUrlAPI(context);
            getToken(context);
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, UrlAPI +"/iva?access-token="+TOKEN, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    ivas = IvaJsonParser.parserJsonIvas(response);

                    // para informar a vista
                    if (ivasListener!=null)
                        ivasListener.onRefreshListaIvas(ivas);
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Erro ao aceder a API", Toast.LENGTH_SHORT).show();
                }

            });
            volleyQueue.add(req);
        }
        return ivas;
    }

    public void removerIvaAPI(final Iva iva, final Context context){
        if(!isConnectionInternet(context))
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        else
        {
            getUrlAPI(context);
            getToken(context);
            StringRequest req = new StringRequest(Request.Method.DELETE, UrlAPI + "/iva/eliminariva/"+ iva.getId() +"?access-token="+TOKEN, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // informar a vista
                    if (ivaListener!=null)
                        ivaListener.onRefreshDetalhes(MainActivity.DELETE);
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Erro ao aceder a API", Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(req);
        }
    }

    public void editarIvaAPI(final Iva iva, final Context context){
        if(!isConnectionInternet(context))
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        else {
            getUrlAPI(context);
            getToken(context);
            StringRequest req = new StringRequest(Request.Method.PUT, UrlAPI + "/iva/editariva/"+ iva.getId() +"?access-token="+TOKEN, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (ivaListener != null)
                        ivaListener.onRefreshDetalhes(MainActivity.ADD);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Erro ao aceder a API", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public byte[] getBody(){
                    String json = IvaJsonParser.ivaParaJson(iva);
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
//region PEDIDOS API LINHAS CARRINHOS
//endregion
//region PEDIDOS API USERS
//endregion
}
