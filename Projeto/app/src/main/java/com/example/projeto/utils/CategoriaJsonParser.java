package com.example.projeto.utils;

import com.example.projeto.modelo.Categoria;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoriaJsonParser {

    public static ArrayList<Categoria> parserJsonCategorias(JSONArray response) {
        ArrayList<Categoria> categorias = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject categoriaJson = response.getJSONObject(i);
                int id = categoriaJson.getInt("id");
                String descricao = categoriaJson.getString("descricao");

                Categoria categoria = new Categoria(id, descricao);
                categorias.add(categoria);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return categorias;
    }

    public static String categoriaParaJson(Categoria categoria) {
        JSONObject jsonCategoria = new JSONObject();
        try {
            jsonCategoria.put("descricao", categoria.getDescricao());

            // Adicione outros campos conforme necessário

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonCategoria.toString();
    }
}
