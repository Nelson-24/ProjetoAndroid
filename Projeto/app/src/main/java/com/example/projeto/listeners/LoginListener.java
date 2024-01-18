package com.example.projeto.listeners;

public interface LoginListener {
    void onRefreshLogin(boolean success, String mensagem, String token, String email, String role);
}

