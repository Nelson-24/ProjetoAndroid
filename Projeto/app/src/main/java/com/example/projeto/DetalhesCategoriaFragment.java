package com.example.projeto;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.projeto.modelo.Categoria;
import com.example.projeto.modelo.SingletonGestorApp;

import java.util.ArrayList;

public class DetalhesCategoriaFragment extends Fragment {

    private TextView tvDescricao;

    public DetalhesCategoriaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_lista_categoria, container, false);

        tvDescricao = view.findViewById(R.id.tvDescricao);

        carregarCategoria();
        return view;
    }

    private void carregarCategoria() {
        ArrayList<Categoria> categorias = SingletonGestorApp.getInstance(getContext()).getAllCategoriasAPI(getContext());

        if (categorias.size() > 0) {
            Categoria categoria = categorias.get(0);
            tvDescricao.setText(categoria.getDescricao());
        }
    }
}
