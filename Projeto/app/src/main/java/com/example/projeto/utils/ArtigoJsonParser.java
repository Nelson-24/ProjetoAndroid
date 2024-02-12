package com.example.projeto.utils;

import com.example.projeto.modelo.Artigo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ArtigoJsonParser {
    public static ArrayList<Artigo> parserJsonArtigos(JSONArray response){
        ArrayList<Artigo> artigos=new ArrayList<>();
        try {
            for (int i=0; i<response.length(); i++){
                JSONObject artigoJson= (JSONObject) response.get(i);
                int id=artigoJson.getInt("id");
                String referencia=artigoJson.getString("referencia");
                String descricao= artigoJson.getString("descricao");
                int preco=artigoJson.getInt("preco");
                int stock=artigoJson.getInt("stock");
                int categoriaId=artigoJson.getInt("categoria_id");
                int ivasId=artigoJson.getInt("ivas_id");
                String imagem= artigoJson.getString("imagem");
                Artigo artigo = new Artigo(id,referencia,preco,stock,categoriaId, ivasId, descricao, imagem);
                artigos.add(artigo);
            }
        }
        catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return artigos;
    }

    public static Artigo parserJsonArtigo(String response){
        try {
            JSONObject jsonArtigo = new JSONObject(response);

            int id = jsonArtigo.optInt("id", -1);
            String referencia = jsonArtigo.optString("referencia", "");
            String descricao = jsonArtigo.optString("descricao", "");
            float preco = (float) jsonArtigo.optDouble("preco", 0.0);
            int stock = jsonArtigo.optInt("stock", 0);
            int categoriaId = jsonArtigo.optInt("categoria_id", -1);
            int ivasId = jsonArtigo.optInt("ivas_id", -1);
            String imagem = jsonArtigo.optString("imagem", "");


            Artigo artigo = new Artigo(id,referencia,preco,stock,categoriaId, ivasId, descricao, imagem);

            return artigo;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String artigoParaJson(Artigo artigo) {
        JSONObject jsonArtigo = new JSONObject();
        try {
            jsonArtigo.put("referencia", artigo.getReferencia());
            jsonArtigo.put("descricao", artigo.getDescricao());
            jsonArtigo.put("preco", artigo.getPreco());
            jsonArtigo.put("stock", artigo.getStock());
            jsonArtigo.put("categoria_id", artigo.getIdCategoria());
            jsonArtigo.put("ivas_id", artigo.getIdIva());
            jsonArtigo.put("imagem", artigo.getImagem());

            // Adicione outros campos conforme necessário

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonArtigo.toString();
    }
}
