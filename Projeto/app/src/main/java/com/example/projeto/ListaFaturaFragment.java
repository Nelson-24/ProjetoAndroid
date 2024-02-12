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

import com.example.projeto.DetalhesFaturaActivity;
import com.example.projeto.MainActivity;
import com.example.projeto.R;
import com.example.projeto.adaptadores.ListaFaturasAdaptador;
import com.example.projeto.listeners.FaturasListener;
import com.example.projeto.modelo.Fatura;
import com.example.projeto.modelo.SingletonGestorApp;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListaFaturaFragment extends Fragment implements FaturasListener {

    private ListView lvLista;
    private SearchView searchView;
    private FloatingActionButton fabLista;

    public ListaFaturaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista, container, false);
        setHasOptionsMenu(true);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
        String role = sharedPreferences.getString("ROLE", "");
        int userId = sharedPreferences.getInt("USER_ID", 0);
        fabLista = view.findViewById(R.id.fabLista);
        fabLista.setVisibility(View.GONE);

        lvLista = view.findViewById(R.id.lvLista);
        lvLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getContext(), DetalhesFaturaActivity.class);
                intent.putExtra(DetalhesFaturaActivity.ID_FATURA, (int) id);
                startActivityForResult(intent, MainActivity.EDIT);
            }
        });

        SingletonGestorApp.getInstance(getContext()).setFaturasListener(this);
        if ("cliente".equals(role)) {
            SingletonGestorApp.getInstance(getContext()).getFaturasClienteAPI(userId,getContext());
        } else {
            SingletonGestorApp.getInstance(getContext()).getAllFaturasAPI(getContext());
        }
        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
        String role = sharedPreferences.getString("ROLE", "");
        int userId = sharedPreferences.getInt("USER_ID", 0);

        if (resultCode== Activity.RESULT_OK) {
            if ("cliente".equals(role)) {
                SingletonGestorApp.getInstance(getContext()).getFaturasClienteAPI(userId,getContext());
            } else {
                SingletonGestorApp.getInstance(getContext()).getAllFaturasAPI(getContext());
            }
        }
    }


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
                ArrayList<Fatura> tempListaFatura = new ArrayList<>();
                for (Fatura f: SingletonGestorApp.getInstance(getContext()).getAllFaturasAPI(getContext())) {
                    if (String.valueOf(f.getUsers_id()).toLowerCase().contains(newText.toLowerCase()) || f.getData().toLowerCase().contains(newText.toLowerCase()))
                        tempListaFatura.add(f);
                }
                lvLista.setAdapter(new ListaFaturasAdaptador(getContext(), tempListaFatura));
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onRefreshListaFaturas(ArrayList<Fatura> listaFaturas) {
        if (listaFaturas != null) {
            lvLista.setAdapter(new ListaFaturasAdaptador(getContext(), listaFaturas));
        }
    }
}
