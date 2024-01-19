package com.example.projeto;

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

    private TextView tvData, tvValorTotal, tvIdUser;

    public DetalhesCarrinhoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_lista_carrinho, container, false);

        tvData = view.findViewById(R.id.tvData);
        tvValorTotal = view.findViewById(R.id.tvValorTotal);
        tvIdUser = view.findViewById(R.id.tvIdUser);

        carregarCarrinho();
        return view;
    }

    private void carregarCarrinho() {
//        ArrayList<Carrinho> carrinhos = SingletonGestorApp.getInstance(getContext()).getCarrinhosBD();
//
//        if (carrinhos.size() > 0) {
//            Carrinho carrinho = carrinhos.get(0);
//            tvData.setText(carrinho.getData());
//            tvValorTotal.setText(carrinho.getValorTotal() + "");
//            tvIdUser.setText(carrinho.getUsers_id() + "");
//        }
    }
}
