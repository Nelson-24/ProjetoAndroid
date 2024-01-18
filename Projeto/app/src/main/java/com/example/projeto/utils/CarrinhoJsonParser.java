package com.example.projeto.utils;

import com.example.projeto.modelo.Carrinho;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CarrinhoJsonParser {

    public static ArrayList<Carrinho> parserJsonCarrinhos(JSONArray response) {
        ArrayList<Carrinho> carrinhos = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject carrinhoJson = response.getJSONObject(i);
                int id = carrinhoJson.getInt("id");
                String data = carrinhoJson.getString("data");
                float valorTotal = (float) carrinhoJson.getDouble("valorTotal");
                String estado = carrinhoJson.getString("estado");
                String opcaoEntrega = carrinhoJson.getString("opcaoEntrega");
                float valor = (float) carrinhoJson.getDouble("valor");
                float valorIva = (float) carrinhoJson.getDouble("valorIva");
                int users_id = carrinhoJson.getInt("users_id");

                Carrinho carrinho = new Carrinho(id, data, valorTotal, estado, opcaoEntrega, valor, valorIva, users_id);
                carrinhos.add(carrinho);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return carrinhos;
    }

    public static Carrinho parserJsonCarrinho(String response) {
        try {
            JSONObject jsonCarrinho = new JSONObject(response);

            int id = jsonCarrinho.optInt("id", -1);
            String data = jsonCarrinho.optString("data", "");
            float valorTotal = (float) jsonCarrinho.optDouble("valorTotal", 0.0);
            String estado = jsonCarrinho.optString("estado", "");
            String opcaoEntrega = jsonCarrinho.optString("opcaoEntrega", "");
            float valor = (float) jsonCarrinho.optDouble("valor", 0.0);
            float valorIva = (float) jsonCarrinho.optDouble("valorIva", 0.0);
            int users_id = jsonCarrinho.optInt("users_id", -1);

            Carrinho carrinho = new Carrinho(id, data, valorTotal, estado, opcaoEntrega, valor, valorIva, users_id);
            return carrinho;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String carrinhoParaJson(Carrinho carrinho) {
        JSONObject jsonCarrinho = new JSONObject();
        try {
            jsonCarrinho.put("data", carrinho.getData());
            jsonCarrinho.put("valorTotal", carrinho.getValorTotal());
            jsonCarrinho.put("estado", carrinho.getEstado());
            jsonCarrinho.put("opcaoEntrega", carrinho.getOpcaoEntrega());
            jsonCarrinho.put("valor", carrinho.getValor());
            jsonCarrinho.put("valorIva", carrinho.getValorIva());
            jsonCarrinho.put("users_id", carrinho.getUsers_id());

            // Adicione outros campos conforme necessário

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonCarrinho.toString();
    }
}
