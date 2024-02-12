package com.example.projeto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.projeto.adaptadores.ListaArtigosAdaptador;
import com.example.projeto.listeners.ArtigosListener;
import com.example.projeto.modelo.Artigo;
import com.example.projeto.modelo.SingletonGestorApp;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListaArtigoFragment extends Fragment implements ArtigosListener{

    private ListView lvLista;
    private FloatingActionButton fabLista;
    private SearchView searchView;

    public ListaArtigoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista, container, false);
        setHasOptionsMenu(true);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
        String role = sharedPreferences.getString("ROLE", "");

        lvLista=view.findViewById(R.id.lvLista);
        lvLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent=new Intent(getContext(), DetalhesArtigoActivity.class);
                intent.putExtra(DetalhesArtigoActivity.ID_ARTIGO, (int)id);
                startActivityForResult(intent, MainActivity.EDIT);
            }
        });
        fabLista=view.findViewById(R.id.fabLista);
        if ("cliente".equals(role)) {
            fabLista.setVisibility(View.GONE);
        } else {
            fabLista.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), DetalhesArtigoActivity.class);
                    startActivityForResult(intent, MainActivity.ADD);
                }
            });
        }
        SingletonGestorApp.getInstance(getContext()).setArtigosListener(this);
        SingletonGestorApp.getInstance(getContext()).getAllArtigosAPI(getContext());
        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode== Activity.RESULT_OK){
            if (requestCode==MainActivity.ADD || requestCode==MainActivity.EDIT){
                SingletonGestorApp.getInstance(getContext()).getAllArtigosAPI(getContext());
                if (requestCode==MainActivity.ADD)
                    Toast.makeText(getContext(),"Artigo adicionado com sucesso", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getContext(),"Artigo editado com sucesso", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_pesquisa,menu);
        MenuItem itemPesquisa= menu.findItem(R.id.itemPesquisa);
        searchView= (SearchView) itemPesquisa.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Artigo> tempListaArtigo=new ArrayList<>();
                for (Artigo a: SingletonGestorApp.getInstance(getContext()).getArtigosBD()){
                    if (a.getDescricao().toLowerCase().contains(newText.toLowerCase()) || a.getReferencia().toLowerCase().contains(newText.toLowerCase()))
                        tempListaArtigo.add(a);
                }
                lvLista.setAdapter(new ListaArtigosAdaptador(getContext(),tempListaArtigo));
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onRefreshListaArtigos(ArrayList<Artigo> listaArtigos) {
        if (listaArtigos!=null)
            lvLista.setAdapter(new ListaArtigosAdaptador(getContext(),listaArtigos));
    }
}