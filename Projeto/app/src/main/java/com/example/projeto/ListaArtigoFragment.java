package com.example.projeto;

import android.app.Activity;
import android.content.Intent;
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
import com.example.projeto.modelo.SingletonGestorArtigos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListaArtigoFragment extends Fragment implements ArtigosListener{

    private ListView lvArtigos;
    private ArrayList<Artigo> artigos;
    private FloatingActionButton fabLista;
    private SearchView searchView;

    public ListaArtigoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_artigo, container, false);
        setHasOptionsMenu(true);

        lvArtigos=view.findViewById(R.id.lvArtigos);
//        artigos= SingletonGestorArtigos.getInstance(getContext()).getArtigosBD();
//        lvArtigos.setAdapter(new ListaArtigosAdaptador(getContext(),artigos));
        lvArtigos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent=new Intent(getContext(), DetalhesArtigoActivity.class);
                intent.putExtra(DetalhesArtigoActivity.ID_ARTIGO, (int)id);
                startActivityForResult(intent, MainActivity.EDIT);
            }
        });
        fabLista=view.findViewById(R.id.fabLista);
        fabLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DetalhesArtigoActivity.class);
                startActivityForResult(intent, MainActivity.ADD);
            }
        });

        SingletonGestorArtigos.getInstance(getContext()).setArtigosListener(this);
        SingletonGestorArtigos.getInstance(getContext()).getAllArtigosAPI(getContext());
        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode== Activity.RESULT_OK){
            if (requestCode==MainActivity.ADD || requestCode==MainActivity.EDIT){
//                artigos=SingletonGestorArtigos.getInstance(getContext()).getArtigosBD();
//                lvArtigos.setAdapter(new ListaArtigosAdaptador(getContext(),artigos));
                SingletonGestorArtigos.getInstance(getContext()).getAllArtigosAPI(getContext());
                if (resultCode==MainActivity.ADD)
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
                for (Artigo a:SingletonGestorArtigos.getInstance(getContext()).getArtigosBD())
                    if (a.getDescricao().toLowerCase().contains(newText.toLowerCase()))
                        tempListaArtigo.add(a);
                lvArtigos.setAdapter(new ListaArtigosAdaptador(getContext(),tempListaArtigo));
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onRefreshListaArtigos(ArrayList<Artigo> listaArtigos) {
        if (listaArtigos!=null)
            lvArtigos.setAdapter(new ListaArtigosAdaptador(getContext(),listaArtigos));
    }
}