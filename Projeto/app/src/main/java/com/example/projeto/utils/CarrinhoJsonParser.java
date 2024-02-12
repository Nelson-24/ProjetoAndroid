package com.example.projeto.utils;

import com.example.projeto.modelo.Carrinho;
import com.example.projeto.modelo.Fatura;

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
                int id = carrinhoJson.optInt("id", -1);
                double valorTotal = carrinhoJson.optDouble("valorTotal", 0.0);
                double valor = carrinhoJson.optDouble("valor", 0.0);
                double valorIva = carrinhoJson.optDouble("valorIva", 0.0);
                String data = carrinhoJson.optString("data", "");
                String estado = carrinhoJson.optString("estado", "");
                int opcaoEntrega = carrinhoJson.optInt("opcaoEntrega", -1);
                int users_id = carrinhoJson.optInt("users_id", -1);

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
            JSONArray jsonArray = new JSONArray(response);

            if (jsonArray.length() > 0) {
                JSONObject carrinhoJson = jsonArray.getJSONObject(0);

                int id = carrinhoJson.optInt("id", -1);
                double valorTotal = carrinhoJson.optDouble("valorTotal", 0.0);
                double valor = carrinhoJson.optDouble("valor", 0.0);
                double valorIva = carrinhoJson.optDouble("valorIva", 0.0);
                String data = carrinhoJson.optString("data", "");
                String estado = carrinhoJson.optString("estado", "");
                int opcaoEntrega = carrinhoJson.optInt("opcaoEntrega", -1);
                int users_id = carrinhoJson.optInt("users_id", -1);

                Carrinho carrinho = new Carrinho(id, data, valorTotal, estado, opcaoEntrega, valor, valorIva, users_id);

                return carrinho;
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}