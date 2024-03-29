package com.example.projeto;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.projeto.modelo.Artigo;
import com.example.projeto.modelo.SingletonGestorApp;

import java.util.ArrayList;
public class DetalhesArtigoFragment extends Fragment {

    private TextView tvReferencia, tvDescricao, tvPreco;
    private ImageView imgImagem;

    public DetalhesArtigoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.item_lista_artigo, container, false);

        tvReferencia=view.findViewById(R.id.tvReferencia);
        tvDescricao=view.findViewById(R.id.tvDescricao);
        tvPreco=view.findViewById(R.id.tvPreco);
        imgImagem=view.findViewById(R.id.imgImagem);

        carregarArtigo();
        return view;
    }

    private void carregarArtigo() {
        ArrayList<Artigo> artigos= SingletonGestorApp.getInstance(getContext()).getArtigosBD();

        if(artigos.size()>0){ //se o tiver algum artigo
            Artigo artigo=artigos.get(0); //buscar o 1ºartigo da lista
            tvReferencia.setText(artigo.getReferencia() + "");
            tvDescricao.setText(artigo.getDescricao());
            tvPreco.setText(artigo.getPreco() + "");
        }
    }
}
