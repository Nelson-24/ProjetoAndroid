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
                float valorTotal = (float) faturaJson.getDouble("valorTotal");
                float valor = (float) faturaJson.getDouble("valor");
                float valorIva = (float) faturaJson.getDouble("valorIva");
                String data = faturaJson.getString("data");
                String estado = faturaJson.getString("estado");
                String opcaoEntrega = faturaJson.getString("opcaoEntrega");
                int users_id = faturaJson.getInt("users_id");

                Fatura fatura = new Fatura(id, data, valorTotal, estado, opcaoEntrega, valor, valorIva, users_id);
                faturas.add(fatura);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return faturas;
    }

    public static Fatura parserJsonFatura(String response) {
        try {
            JSONObject jsonFatura = new JSONObject(response);

            int id = jsonFatura.optInt("id", -1);
            float valorTotal = (float) jsonFatura.optDouble("valorTotal", 0.0);
            float valor = (float) jsonFatura.optDouble("valor", 0.0);
            float valorIva = (float) jsonFatura.optDouble("valorIva", 0.0);
            String data = jsonFatura.optString("data", "");
            String estado = jsonFatura.optString("estado", "");
            String opcaoEntrega = jsonFatura.optString("opcaoEntrega", "");
            int users_id = jsonFatura.optInt("users_id", -1);

            Fatura fatura = new Fatura(id, data, valorTotal, estado, opcaoEntrega, valor, valorIva, users_id);
            return fatura;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String faturaParaJson(Fatura fatura) {
        JSONObject jsonFatura = new JSONObject();
        try {
            jsonFatura.put("valorTotal", fatura.getValorTotal());
            jsonFatura.put("valor", fatura.getValor());
            jsonFatura.put("valorIva", fatura.getValorIva());
            jsonFatura.put("data", fatura.getData());
            jsonFatura.put("estado", fatura.getEstado());
            jsonFatura.put("opcaoEntrega", fatura.getOpcaoEntrega());
            jsonFatura.put("users_id", fatura.getUsers_id());

            // Adicione outros campos conforme necessário

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonFatura.toString();
    }
}
