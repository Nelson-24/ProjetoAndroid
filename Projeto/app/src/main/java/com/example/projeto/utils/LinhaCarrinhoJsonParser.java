package com.example.projeto.utils;

import com.example.projeto.modelo.LinhaCarrinho;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LinhaCarrinhoJsonParser {

    public static ArrayList<LinhaCarrinho> parserJsonLinhasCarrinho(JSONArray response) {
        ArrayList<LinhaCarrinho> linhasCarrinho = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject linhaCarrinhoJson = response.getJSONObject(i);
                int id = linhaCarrinhoJson.getInt("id");
                int quantidade = linhaCarrinhoJson.getInt("quantidade");
                float valor = (float) linhaCarrinhoJson.getDouble("valor");
                String referencia = linhaCarrinhoJson.getString("referencia");
                int artigos_id = linhaCarrinhoJson.getInt("artigos_id");
                int carrinhocompras_id = linhaCarrinhoJson.getInt("carrinhocompras_id");

                LinhaCarrinho linhaCarrinho = new LinhaCarrinho(id, quantidade, valor, referencia, artigos_id, carrinhocompras_id);
                linhasCarrinho.add(linhaCarrinho);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return linhasCarrinho;
    }

    public static LinhaCarrinho parserJsonLinhaCarrinho(String response) {
        try {
            JSONObject jsonLinhaCarrinho = new JSONObject(response);

            int id = jsonLinhaCarrinho.optInt("id", -1);
            int quantidade = jsonLinhaCarrinho.optInt("quantidade", 0);
            float valor = (float) jsonLinhaCarrinho.optDouble("valor", 0.0);
            String referencia = jsonLinhaCarrinho.optString("referencia", "");
            int artigos_id = jsonLinhaCarrinho.optInt("artigos_id", -1);
            int carrinhocompras_id = jsonLinhaCarrinho.optInt("carrinhocompras_id", -1);

            LinhaCarrinho linhaCarrinho = new LinhaCarrinho(id, quantidade, valor, referencia, artigos_id, carrinhocompras_id);
            return linhaCarrinho;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String linhaCarrinhoParaJson(LinhaCarrinho linhaCarrinho) {
        JSONObject jsonLinhaCarrinho = new JSONObject();
        try {
            jsonLinhaCarrinho.put("quantidade", linhaCarrinho.getQuantidade());
            jsonLinhaCarrinho.put("artigos_id", linhaCarrinho.getArtigos_id());
            jsonLinhaCarrinho.put("carrinhocompras_id", linhaCarrinho.getCarrinhocompras_id());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonLinhaCarrinho.toString();
    }
}
