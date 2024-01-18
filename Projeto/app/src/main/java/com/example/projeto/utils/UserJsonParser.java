package com.example.projeto.utils;

import com.example.projeto.modelo.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserJsonParser {

    public static ArrayList<User> parserJsonUsers(JSONArray response) {
        ArrayList<User> users = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject userJson = response.getJSONObject(i);
                int id = userJson.getInt("id");
                String username = userJson.getString("username");
                String email = userJson.getString("email");
                String nome = userJson.getString("nome");
                String nif = userJson.getString("nif");
                String morada = userJson.getString("morada");
                String contacto = userJson.getString("contacto");

                User user = new User(id, username, email, nome, nif, morada, contacto);
                users.add(user);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public static User parserJsonUser(String response) {
        try {
            JSONObject jsonUser = new JSONObject(response);

            int id = jsonUser.optInt("id", -1);
            String username = jsonUser.optString("username", "");
            String email = jsonUser.optString("email", "");
            String nome = jsonUser.optString("nome", "");
            String nif = jsonUser.optString("nif", "");
            String morada = jsonUser.optString("morada", "");
            String contacto = jsonUser.optString("contacto", "");

            User user = new User(id, username, email, nome, nif, morada, contacto);
            return user;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String userParaJson(User user) {
        JSONObject jsonUser = new JSONObject();
        try {
            jsonUser.put("username", user.getUsername());
            jsonUser.put("email", user.getEmail());
            jsonUser.put("nome", user.getNome());
            jsonUser.put("nif", user.getNif());
            jsonUser.put("morada", user.getMorada());
            jsonUser.put("contacto", user.getContacto());

            // Adicione outros campos conforme necessário

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonUser.toString();
    }
}
