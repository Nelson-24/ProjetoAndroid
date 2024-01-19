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

import com.example.projeto.adaptadores.ListaIvasAdaptador;
import com.example.projeto.listeners.IvasListener;
import com.example.projeto.modelo.Iva;
import com.example.projeto.modelo.SingletonGestorApp;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListaIvaFragment extends Fragment implements IvasListener {

    private ListView lvLista;
    private FloatingActionButton fabLista;
    private SearchView searchView;

    public ListaIvaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista, container, false);
        setHasOptionsMenu(true);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
        String role = sharedPreferences.getString("ROLE", "");

        lvLista = view.findViewById(R.id.lvLista);
        lvLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getContext(), DetalhesIvaActivity.class);
                intent.putExtra(DetalhesIvaActivity.ID_IVA, (int) id);
                startActivityForResult(intent, MainActivity.EDIT);
            }
        });
        fabLista = view.findViewById(R.id.fabLista);
        if ("cliente".equals(role)) {
            fabLista.setVisibility(View.GONE);
        } else {
            fabLista.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), DetalhesIvaActivity.class);
                    startActivityForResult(intent, MainActivity.ADD);
                }
            });
        }
        SingletonGestorApp.getInstance(getContext()).setIvasListener(this);
        SingletonGestorApp.getInstance(getContext()).getAllIvasAPI(getContext());
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == MainActivity.ADD || requestCode == MainActivity.EDIT) {
                SingletonGestorApp.getInstance(getContext()).getAllIvasAPI(getContext());
                if (requestCode == MainActivity.ADD)
                    Toast.makeText(getContext(), "IVA adicionado com sucesso", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getContext(), "IVA editado com sucesso", Toast.LENGTH_LONG).show();
            }
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
                ArrayList<Iva> tempListaIva = new ArrayList<>();
                for (Iva i : SingletonGestorApp.getInstance(getContext()).getAllIvasAPI(getContext())) {
                    if (i.getDescricao().toLowerCase().contains(newText.toLowerCase()))
                        tempListaIva.add(i);
                }
                lvLista.setAdapter(new ListaIvasAdaptador(getContext(), tempListaIva));
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onRefreshListaIvas(ArrayList<Iva> listaIvas) {
        if (listaIvas != null)
            lvLista.setAdapter(new ListaIvasAdaptador(getContext(), listaIvas));
    }
}
