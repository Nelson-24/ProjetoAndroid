package com.example.projeto;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.projeto.modelo.Fatura;
import com.example.projeto.modelo.SingletonGestorApp;

import java.util.ArrayList;

public class DetalhesFaturaFragment extends Fragment {
    private TextView tvData, tvValorTotal, tvEstado, tvIdUser;

    public DetalhesFaturaFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_lista_fatura, container, false);

        tvData = view.findViewById(R.id.tvData);
        tvValorTotal = view.findViewById(R.id.tvValorTotal);
        tvEstado = view.findViewById(R.id.tvEstado);
        tvIdUser = view.findViewById(R.id.tvIdUser);
        carregarFatura();
        return view;
    }

    private void carregarFatura() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
        String role = sharedPreferences.getString("ROLE", "");
        int userId = sharedPreferences.getInt("ID_USER", 0);

        ArrayList<Fatura> faturas;

        if ("cliente".equals(role)) {
            faturas = SingletonGestorApp.getInstance(getContext()).getFaturasClienteAPI(userId,getContext());
        } else {
            faturas = SingletonGestorApp.getInstance(getContext()).getAllFaturasAPI(getContext());
        }
        if (faturas!=null) {
            Fatura fatura = faturas.get(0);
            tvData.setText(fatura.getData());
            tvValorTotal.setText(String.valueOf(fatura.getValorTotal()));
            tvEstado.setText(fatura.getEstado());
            tvIdUser.setText(String.valueOf(fatura.getUsers_id()));
        }
    }
}
