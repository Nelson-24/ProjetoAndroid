package com.example.projeto.listeners;

import com.example.projeto.modelo.Fatura;

import java.util.ArrayList;

public interface FaturasListener {
    void onRefreshListaFaturas(ArrayList<Fatura> listaFaturas);
}
