package com.example.projeto.utils;

import com.example.projeto.modelo.Iva;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class IvaJsonParser {

    public static ArrayList<Iva> parserJsonIvas(JSONArray response) {
        ArrayList<Iva> ivas = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject ivaJson = response.getJSONObject(i);
                int id = ivaJson.getInt("id");
                float percentagem = (float) ivaJson.getDouble("percentagem");
                String descricao = ivaJson.getString("descricao");

                Iva iva = new Iva(id, percentagem, descricao);

                ivas.add(iva);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return ivas;
    }

    public static String ivaParaJson(Iva iva) {
        JSONObject jsonIva = new JSONObject();
        try {
            jsonIva.put("percentagem", String.valueOf(iva.getPercentagem()));
            jsonIva.put("descricao", iva.getDescricao());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonIva.toString();
    }
}
