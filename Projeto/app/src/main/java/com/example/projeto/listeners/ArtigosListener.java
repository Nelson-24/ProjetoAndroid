package com.example.projeto.listeners;

import com.example.projeto.modelo.Artigo;

import java.util.ArrayList;

public interface ArtigosListener {
    void onRefreshListaArtigos(ArrayList<Artigo> listaArtigos);
}
