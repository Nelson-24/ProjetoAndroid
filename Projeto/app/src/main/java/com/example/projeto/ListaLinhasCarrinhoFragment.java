package com.example.projeto;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.projeto.adaptadores.ListaCarrinhosAdaptador;
import com.example.projeto.adaptadores.ListaIvasAdaptador;
import com.example.projeto.adaptadores.ListaLinhasCarrinhoAdaptador;
import com.example.projeto.listeners.CarrinhosListener;
import com.example.projeto.listeners.LinhasCarrinhoListener;
import com.example.projeto.modelo.Carrinho;
import com.example.projeto.modelo.LinhaCarrinho;
import com.example.projeto.modelo.SingletonGestorApp;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListaLinhasCarrinhoFragment extends Fragment implements LinhasCarrinhoListener, CarrinhosListener {

    private ListView lvLista;
    private TextView tvValorTotal, tvValorSubTotal, tvValorIva;
    private ListaLinhasCarrinhoAdaptador linhascarrinhoAdaptador;
    private int carrinhoId;
    private FloatingActionButton fabLista;
    private ArrayList<LinhaCarrinho> linhasCarrinho = new ArrayList<>();

    public ListaLinhasCarrinhoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dados_carrinho, container, false);
        setHasOptionsMenu(true);
        SingletonGestorApp.getInstance(getContext());
        tvValorTotal = view.findViewById(R.id.tvValorTotal);
        tvValorSubTotal = view.findViewById(R.id.tvValorSubTotal);
        tvValorIva = view.findViewById(R.id.tvValorIva);
        lvLista = view.findViewById(R.id.lvLista);

        linhascarrinhoAdaptador = new ListaLinhasCarrinhoAdaptador(getContext(), new ArrayList<>());
        lvLista.setAdapter(linhascarrinhoAdaptador);
        fabLista = view.findViewById(R.id.fabLista);

        lvLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getContext(), DetalhesLinhaCarrinhoActivity.class);
                intent.putExtra(DetalhesLinhaCarrinhoActivity.ID_LINHA, (int) id);
                startActivityForResult(intent, MainActivity.ADD);
            }
        });
        fabLista = view.findViewById(R.id.fabLista);
        fabLista.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmarFinalizarCompra();
            }
        });
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
        carrinhoId = sharedPreferences.getInt("CARRINHO_ID", 0);
        SingletonGestorApp.getInstance(getContext()).setLinhasCarrinhoListener(this);
        SingletonGestorApp.getInstance(getContext()).setCarrinhosListener(this);
        SingletonGestorApp.getInstance(getContext()).getLinhasCarrinhoApi(carrinhoId, getContext());
        SingletonGestorApp.getInstance(getContext()).getCarrinhosAPI(carrinhoId, getContext());
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == Activity.RESULT_OK) {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
            carrinhoId = sharedPreferences.getInt("CARRINHO_ID", 0);
            SingletonGestorApp.getInstance(getContext()).getLinhasCarrinhoApi(carrinhoId, getContext());
            SingletonGestorApp.getInstance(getContext()).getCarrinhosAPI(carrinhoId, getContext());
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_remover, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itemRemover) {
            removerCarrinho();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void removerCarrinho() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Eliminar Carinho")
                .setMessage("Tem a certeza que pretende eliminar o carrinho?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences sharedPreferences = getContext().getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
                        carrinhoId = sharedPreferences.getInt("CARRINHO_ID", 0);
                        SingletonGestorApp.getInstance(getContext()).removerCarrinhoAPI(carrinhoId, getContext());
                        Toast.makeText(getContext(), "Carrinho eliminado com sucesso!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setIcon(android.R.drawable.ic_delete)
                .show();
    }
    private void confirmarFinalizarCompra() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Finalizar Compra")
                .setMessage("Tem a certeza que pretende finalizar a compra?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences sharedPreferences = getContext().getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
                        carrinhoId = sharedPreferences.getInt("CARRINHO_ID", 0);
                        SingletonGestorApp.getInstance(getContext()).finalizarCarrinhoAPI(carrinhoId, getContext());
                        SingletonGestorApp.getInstance(getContext()).adicionarFaturaAPI(carrinhoId, getContext());
                        Toast.makeText(getContext(), "Compra finalizada com sucesso!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }
    @Override
    public void onRefreshListaLinhasCarrinho(ArrayList<LinhaCarrinho> listaLinhasCarrinho) {
        if (listaLinhasCarrinho != null) {
            linhascarrinhoAdaptador = new ListaLinhasCarrinhoAdaptador(getContext(), listaLinhasCarrinho);
            lvLista.setAdapter(linhascarrinhoAdaptador);
        }
    }

    @Override
    public void onRefreshListaCarrinhos(ArrayList<Carrinho> listaCarrinhos) {
        if (listaCarrinhos != null && !listaCarrinhos.isEmpty()) {
            Carrinho carrinhoAtivo = listaCarrinhos.get(0);

            String valorTotal = String.valueOf(carrinhoAtivo.getValorTotal());
            String valorSubTotal = String.valueOf(carrinhoAtivo.getValor());
            String valorIva = String.valueOf(carrinhoAtivo.getValorIva());

            tvValorTotal.setText(valorTotal);
            tvValorSubTotal.setText(valorSubTotal);
            tvValorIva.setText(valorIva);
        }
    }
}
