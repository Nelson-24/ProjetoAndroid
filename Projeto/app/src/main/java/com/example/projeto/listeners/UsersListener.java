package com.example.projeto.listeners;

import com.example.projeto.modelo.User;

import java.util.ArrayList;

public interface UsersListener {
    void onRefreshListaUsers(ArrayList<User> listaUsers);
}
