package com.example.projeto.modelo;

import android.content.Context;
import android.widget.Toast;

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
import com.example.projeto.utils.ArtigoJsonParser;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SingletonGestorArtigos {
    private ArrayList<Artigo> artigos;
    private static SingletonGestorArtigos instance = null;
    private ArtigoBDHelper artigoBD=null;
    private static RequestQueue volleyQueue=null;
    private static final String mUrlAPIArtigos="http://10.0.2.2/ProjetoPSI/Projeto/backend/web/api/artigos/json";
    private static final String mUrlAPILogin="http://10.0.2.2/ProjetoPSI/Projeto/backend/web/api/auth";
    private static final String TOKEN="pmtCFWGtJJKcXYtbWXWKI39N0ba7pYM2";
    private ArtigosListener artigosListener;
    private ArtigoListener artigoListener;


    public static synchronized SingletonGestorArtigos getInstance(Context context){
        if(instance == null) {
            instance = new SingletonGestorArtigos(context);
            volleyQueue = Volley.newRequestQueue(context);
        }
        return instance;
    }

    private SingletonGestorArtigos(Context context){
        artigos= new ArrayList<>();
        artigoBD=new ArtigoBDHelper(context);
    }

    public void setArtigoListener(ArtigoListener artigoListener) {
        this.artigoListener = artigoListener;
    }

    public void setArtigosListener(ArtigosListener artigosListener) {
        this.artigosListener = artigosListener;
    }

    public ArrayList<Artigo> getArtigosBD(){
        artigos = artigoBD.getAllArtigosBD();
        return new ArrayList<>(artigos);
    }

    public Artigo getArtigo(int id){
        for (Artigo a: artigos){
            if (a.getId()==id)
                return a;
        }
        return null;
    }

    //region ACESSOS BD
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
//region PEDIDOS API
    public void adicionarArtigoAPI(final Artigo artigo, final Context context){
        if (!ArtigoJsonParser.isConnectionInternet(context))
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        else
        {
            StringRequest req = new StringRequest(Request.Method.POST, mUrlAPIArtigos, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // vou guardar na bdlocal para funcionar offline
                    adicionarArtigoBD(ArtigoJsonParser.parserJsonArtigo(response));
                    // informar a vista
                    if (artigoListener!=null)
                        artigoListener.onRefreshDetalhes(MainActivity.ADD);
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams(){
                    Map<String, String> params = new HashMap<>();
                    params.put("token",TOKEN);
                    params.put("referencia",artigo.getReferencia()+"");
                    params.put("descricao",artigo.getDescricao());
                    params.put("preco",artigo.getPreco()+"");
                    params.put("stock",artigo.getStock()+"");
                    params.put("categoria_id",artigo.getIdCategoria()+"");
//                    params.put("foto",artigo.getFoto());
                    return params;
                }
            };
            volleyQueue.add(req);
        }
    }

    public void getAllArtigosAPI(final Context context){
        if (!ArtigoJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
            // se nao tem internet vai buscar os livros a base de dados
            artigos=artigoBD.getAllArtigosBD();

            if (artigosListener!=null)
                artigosListener.onRefreshListaArtigos(artigos);
        }
        else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPIArtigos, null, new Response.Listener<JSONArray>() {
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
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(req);
        }
    }

    public void removerArtigoAPI(final Artigo artigo, final Context context){
        if (!ArtigoJsonParser.isConnectionInternet(context))
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        else
        {
            StringRequest req = new StringRequest(Request.Method.DELETE, mUrlAPIArtigos+'/'+artigo.getId(), new Response.Listener<String>() {
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
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(req);
        }
    }

    public void editarArtigoAPI(final Artigo artigo, final Context context){
        if (!ArtigoJsonParser.isConnectionInternet(context))
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        else
        {
            StringRequest req = new StringRequest(Request.Method.PUT, mUrlAPIArtigos+'/'+artigo.getId(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // vou guardar na bdlocal para funcionar offline
                    editarArtigoBD(artigo);
                    // informar a vista
                    if (artigoListener!=null)
                        artigoListener.onRefreshDetalhes(MainActivity.EDIT);
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams(){
                    Map<String, String> params = new HashMap<>();
                    params.put("token",TOKEN);
                    params.put("referencia",artigo.getReferencia()+"");
                    params.put("descricao",artigo.getDescricao());
                    params.put("preco",artigo.getPreco()+"");
                    params.put("stock",artigo.getStock()+"");
                    params.put("categoria_id",artigo.getIdCategoria()+"");
//                    params.put("foto",artigo.getFoto());
                    return params;
                }
            };
            volleyQueue.add(req);
        }
    }
//endregion
}
