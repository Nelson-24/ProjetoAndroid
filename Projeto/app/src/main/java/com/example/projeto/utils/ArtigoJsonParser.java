package com.example.projeto.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

import com.example.projeto.modelo.Artigo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.NetworkInterface;
import java.util.ArrayList;

public class ArtigoJsonParser {
    public static ArrayList<Artigo> parserJsonArtigos(JSONArray response){
        ArrayList<Artigo> artigos=new ArrayList<>();
        try {
            for (int i=0; i<response.length(); i++){
                JSONObject artigoJson= (JSONObject) response.get(i);
                int id=artigoJson.getInt("id");
                int referencia=artigoJson.getInt("referencia");
                int preco=artigoJson.getInt("preco");
                int stock=artigoJson.getInt("stock");
                int idCategoria=artigoJson.getInt("categoria_id");
                String descricao= artigoJson.getString("descricao");
                //String foto= artigoJson.getString("foto");
                Artigo artigo = new Artigo(id,referencia,preco,stock,idCategoria,descricao);
                artigos.add(artigo);
            }
        }
        catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return artigos;
    }

    public static Artigo parserJsonArtigo(String response){
        Artigo artigo = null;
        try {
            JSONObject artigoJson = new JSONObject(response);
            int id=artigoJson.getInt("id");
            int referencia=artigoJson.getInt("referencia");
            int preco=artigoJson.getInt("preco");
            int stock=artigoJson.getInt("stock");
            int idCategoria=artigoJson.getInt("categoria_id");
            String descricao= artigoJson.getString("descricao");
            //String foto= artigoJson.getString("foto");
            artigo = new Artigo(id,referencia,preco,stock,idCategoria,descricao);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return artigo;
    }

    public static String parserJsonLogin(String response){
        String token = null;
        try {
            JSONObject loginJson = new JSONObject(response);
            token = loginJson.getString("token");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return token;
    }

    public static boolean isConnectionInternet(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni!=null && ni.isConnected();
    }

}
