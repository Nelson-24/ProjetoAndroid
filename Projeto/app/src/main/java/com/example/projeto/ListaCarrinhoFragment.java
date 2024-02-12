package com.example.projeto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.projeto.adaptadores.ListaCarrinhosAdaptador;
import com.example.projeto.listeners.CarrinhosListener;
import com.example.projeto.modelo.Carrinho;
import com.example.projeto.modelo.SingletonGestorApp;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListaCarrinhoFragment extends Fragment implements CarrinhosListener {

    private ListView lvLista;
    private SearchView searchView;
    private ListaCarrinhosAdaptador carrinhosAdaptador;
    private int userId;
    private FloatingActionButton fabLista;

    public ListaCarrinhoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista, container, false);
        setHasOptionsMenu(true);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("USER_ID", 0);

        lvLista = view.findViewById(R.id.lvLista);
        carrinhosAdaptador = new ListaCarrinhosAdaptador(getContext(), new ArrayList<>());
        lvLista.setAdapter(carrinhosAdaptador);
        fabLista = view.findViewById(R.id.fabLista);
        fabLista.setVisibility(View.GONE);

        lvLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getContext(), DetalhesCarrinhoActivity.class);
                intent.putExtra(DetalhesCarrinhoActivity.ID_CARRINHO, (int) id);
                startActivityForResult(intent, MainActivity.ADD);
            }
        });


        SingletonGestorApp.getInstance(getContext()).setCarrinhosListener(this);
        SingletonGestorApp.getInstance(getContext()).getCarrinhosFinalizadosoAPI(userId, getContext());

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == Activity.RESULT_OK) {
            SingletonGestorApp.getInstance(getContext()).getCarrinhosFinalizadosoAPI(userId, getContext());
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_pesquisa, menu);
        MenuItem itemPesquisa = menu.findItem(R.id.itemPesquisa);
        searchView = (SearchView) itemPesquisa.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Carrinho> tempListaCarrinho = new ArrayList<>();
                for (Carrinho c : SingletonGestorApp.getInstance(getContext()).getCarrinhosFinalizadosoAPI(userId, getContext()))
                    if (c.getData().toLowerCase().contains(newText.toLowerCase()))
                        tempListaCarrinho.add(c);
                lvLista.setAdapter(new ListaCarrinhosAdaptador(getContext(), tempListaCarrinho));
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onRefreshListaCarrinhos(ArrayList<Carrinho> listaCarrinhos) {
        if (listaCarrinhos != null && getContext() != null) {
            carrinhosAdaptador = new ListaCarrinhosAdaptador(getContext(), listaCarrinhos);
            lvLista.setAdapter(carrinhosAdaptador);
        }
    }
}
