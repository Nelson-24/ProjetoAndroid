package com.example.projeto.listeners;

import com.example.projeto.modelo.Carrinho;

import java.util.ArrayList;

public interface CarrinhosListener {
    void onRefreshListaCarrinhos(ArrayList<Carrinho> listaCarrinhos);
}
