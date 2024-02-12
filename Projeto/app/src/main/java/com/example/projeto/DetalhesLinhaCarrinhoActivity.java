package com.example.projeto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projeto.listeners.LinhaCarrinhoListener;
import com.example.projeto.modelo.Carrinho;
import com.example.projeto.modelo.Categoria;
import com.example.projeto.modelo.LinhaCarrinho;
import com.example.projeto.modelo.SingletonGestorApp;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetalhesLinhaCarrinhoActivity extends AppCompatActivity implements LinhaCarrinhoListener {

    public static final String ID_LINHA = "id";
    private TextView etQuantidade, tvValor,tvReferencia;
    private LinhaCarrinho linhaCarrinho;
    private FloatingActionButton fabGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_linha_carrinho);
        etQuantidade = findViewById(R.id.etQuantidade);
        tvValor = findViewById(R.id.tvValor);
        tvReferencia = findViewById(R.id.tvReferencia);
        fabGuardar = findViewById(R.id.fabGuardar);
        SingletonGestorApp.getInstance(getApplicationContext()).setLinhaCarrinhoListener(this);

        int id = getIntent().getIntExtra(ID_LINHA, 0);
        if (id != 0) {
            linhaCarrinho = SingletonGestorApp.getInstance(getApplicationContext()).getLinhaCarrinho(id);
            if (linhaCarrinho != null) {
                carregarLinhaCarrinho();
            } else {
                Toast.makeText(this, "Carrinho Inválido", Toast.LENGTH_SHORT).show();
            }
        } else {
            finish();
        }
        fabGuardar.setVisibility(View.VISIBLE);
        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linhaCarrinho != null) {
                    if (isQuantidadeValida()) {
                        linhaCarrinho.setQuantidade(Integer.parseInt(etQuantidade.getText().toString()));
                        SingletonGestorApp.getInstance(getApplicationContext()).editarLinhaCarrinhoAPI(linhaCarrinho, getApplicationContext());
                    }
                }
            }
        });

    }

    private boolean isQuantidadeValida() {
        String quantidade = etQuantidade.getText().toString();

        if (quantidade.length() < 1) {
            etQuantidade.setError("Quantidade inválida");
            return false;
        }
        return true;
    }

    private void carregarLinhaCarrinho() {
        setTitle("Detalhes da Linha do Carrinho");

        if (linhaCarrinho!= null) {
            etQuantidade.setText(String.valueOf(linhaCarrinho.getQuantidade()));
            tvValor.setText(String.valueOf(linhaCarrinho.getValor()));
            tvReferencia.setText(linhaCarrinho.getReferencia());
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (linhaCarrinho != null) {
            getMenuInflater().inflate(R.menu.menu_remover, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itemRemover) {
            dialogRemover();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void dialogRemover() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remover Artigo")
                .setMessage("Tem a certeza que pertende remover o artigo do carrinho?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SingletonGestorApp.getInstance(getApplicationContext()).removerLinhaCarrinhoAPI(linhaCarrinho, getApplicationContext());
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

    @Override
    public void onRefreshDetalhes(int op) {
        Intent intent= new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}