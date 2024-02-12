package com.example.projeto;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.projeto.modelo.Carrinho;
import com.example.projeto.modelo.SingletonGestorApp;

import java.util.ArrayList;

public class DetalhesCarrinhoFragment extends Fragment {

    private TextView tvData, tvValorTotal, tvEstado;

    public DetalhesCarrinhoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_lista_carrinho, container, false);

        tvData = view.findViewById(R.id.tvData);
        tvValorTotal = view.findViewById(R.id.tvValorTotal);
        tvEstado = view.findViewById(R.id.tvEstado);

        carregarCarrinho();
        return view;
    }

    private void carregarCarrinho() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("ID_USER", 0);

        ArrayList<Carrinho> carrinhos;

        carrinhos = SingletonGestorApp.getInstance(getContext()).getCarrinhosFinalizadosoAPI(userId, getContext());

        if (carrinhos!=null) {
            Carrinho carrinho = carrinhos.get(0);
            tvData.setText(carrinho.getData());
            tvValorTotal.setText(String.valueOf(carrinho.getValorTotal()));
            tvEstado.setText(carrinho.getEstado());
        }
    }
}
