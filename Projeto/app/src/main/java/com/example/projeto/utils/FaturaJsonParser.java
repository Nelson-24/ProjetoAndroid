package com.example.projeto.utils;

import com.example.projeto.modelo.Fatura;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FaturaJsonParser {

    public static ArrayList<Fatura> parserJsonFaturas(JSONArray response) {
        ArrayList<Fatura> faturas = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject faturaJson = response.getJSONObject(i);
                int id = faturaJson.getInt("id");
                double valorTotal = faturaJson.getDouble("valorTotal");
                double valor = faturaJson.getDouble("valor");
                double valorIva = faturaJson.getDouble("valorIva");
                String data = faturaJson.getString("data");
                String estado = faturaJson.getString("estado");
                int opcaoEntrega = Integer.parseInt(faturaJson.getString("opcaoEntrega"));
                int users_id = faturaJson.getInt("users_id");

                Fatura fatura = new Fatura(id, data, valorTotal, estado, opcaoEntrega, valor, valorIva, users_id);
                faturas.add(fatura);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return faturas;
    }

}
