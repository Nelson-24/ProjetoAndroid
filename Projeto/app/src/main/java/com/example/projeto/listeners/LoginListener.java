package com.example.projeto.listeners;

public interface LoginListener {
    void onRefreshLogin(boolean success, String mensagem, String token, String email, String role, int user_id, int carrinho_id);
}

