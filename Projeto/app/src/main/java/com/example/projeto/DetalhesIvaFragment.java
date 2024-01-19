package com.example.projeto;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.projeto.modelo.Iva;
import com.example.projeto.modelo.SingletonGestorApp;

import java.util.ArrayList;

public class DetalhesIvaFragment extends Fragment {

    private TextView tvDescricao, tvPercentagem;

    public DetalhesIvaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_lista_iva, container, false);

        tvDescricao = view.findViewById(R.id.tvDescricao);
        tvPercentagem = view.findViewById(R.id.tvPercentagem);

        carregarIva();
        return view;
    }

    private void carregarIva() {
        ArrayList<Iva> ivas = SingletonGestorApp.getInstance(getContext()).getAllIvasAPI(getContext());

        if (ivas.size() > 0) {
            Iva iva = ivas.get(0);
            tvDescricao.setText(iva.getDescricao());
            tvPercentagem.setText(String.valueOf(iva.getPercentagem()));
        }
    }
}
