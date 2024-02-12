package com.example.projeto;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.projeto.modelo.LinhaCarrinho;
import com.example.projeto.modelo.SingletonGestorApp;

import java.util.ArrayList;

public class DetalhesLinhasCarrinhoFragment extends Fragment {
    private TextView tvIdArtigo, tvQuantidade, tvPreco;

    public DetalhesLinhasCarrinhoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_lista_linha_carrinho, container, false);
        tvIdArtigo = view.findViewById(R.id.tvIdArtigo);
        tvQuantidade = view.findViewById(R.id.tvQuantidade);
        tvPreco = view.findViewById(R.id.tvPreco);

        carrecarLinhasCarrinho();
        return view;
    }

    private void carrecarLinhasCarrinho() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
        int carrinhoId = sharedPreferences.getInt("CARRINHO_ID", 0);
        ArrayList<LinhaCarrinho> linhasCarrinho = SingletonGestorApp.getInstance(getContext()).getLinhasCarrinhoApi(carrinhoId,getContext());
        if (linhasCarrinho.size() > 0) {
            LinhaCarrinho linhaCarrinho = linhasCarrinho.get(0);
            tvIdArtigo.setText(String.valueOf(linhaCarrinho.getArtigos_id()));
            tvQuantidade.setText(String.valueOf(linhaCarrinho.getQuantidade()));
            tvPreco.setText(String.valueOf(linhaCarrinho.getValor()));
        }
    }
}
