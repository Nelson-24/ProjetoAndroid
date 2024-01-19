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
import android.widget.EditText;

import com.example.projeto.listeners.CategoriaListener;
import com.example.projeto.modelo.Categoria;
import com.example.projeto.modelo.SingletonGestorApp;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetalhesCategoriaActivity extends AppCompatActivity implements CategoriaListener {

    public static final String ID_CATEGORIA = "id";
    private final int MIN_CHAR = 3;
    private EditText etDescricao;
    private FloatingActionButton fabGuardar;
    private Categoria categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_categoria);
        SharedPreferences sharedPreferences = getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
        String role = sharedPreferences.getString("ROLE", "");

        etDescricao = findViewById(R.id.etDescricao);
        fabGuardar = findViewById(R.id.fabGuardar);
        SingletonGestorApp.getInstance(getApplicationContext()).setCategoriaListener(this);

        int id = getIntent().getIntExtra(ID_CATEGORIA, 0);
        if (id != 0) {
            categoria = SingletonGestorApp.getInstance(getApplicationContext()).getCategoria(id);
            if (categoria != null) {
                carregarCategoria();
                fabGuardar.setImageResource(R.drawable.ic_action_guardar);
            } else {
                // Algo de errado aconteceu
                finish();
            }
        } else {
            setTitle("Adicionar Categoria");
            fabGuardar.setImageResource(R.drawable.ic_action_adicionar);
        }

        if ("cliente".equals(role)) {
            etDescricao.setKeyListener(null);
            fabGuardar.setVisibility(View.GONE);
        } else {
            fabGuardar.setVisibility(View.VISIBLE);
            fabGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (categoria != null) { // Categoria já existe
                        if (isCategoriaValida()) {
                            categoria.setDescricao(etDescricao.getText().toString());
                            SingletonGestorApp.getInstance(getApplicationContext()).editarCategoriaAPI(categoria, getApplicationContext());
                        }
                    } else { // Categoria para criar
                        if (isCategoriaValida()) {
                            categoria = new Categoria(0, etDescricao.getText().toString());
                            SingletonGestorApp.getInstance(getApplicationContext()).adicionarCategoriaAPI(categoria, getApplicationContext());
                        }
                    }
                }
            });
        }
    }

    private boolean isCategoriaValida() {
        String descricao = etDescricao.getText().toString();

        if (descricao.length() < MIN_CHAR) {
            etDescricao.setError("Descrição inválida");
            return false;
        }

        return true;
    }

    private void carregarCategoria() {
        setTitle("Detalhes: " + categoria.getDescricao());
        etDescricao.setText(categoria.getDescricao());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SharedPreferences sharedPreferences = getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
        String role = sharedPreferences.getString("ROLE", "");

        if (!"cliente".equals(role)) {
            if (categoria != null) {
                getMenuInflater().inflate(R.menu.menu_remover, menu);
                return true;
            }
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
        builder.setTitle("Remover Categoria")
                .setMessage("Tem a certeza que pertende remover a categoria?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SingletonGestorApp.getInstance(getApplicationContext()).removerCategoriaAPI(categoria, getApplicationContext());
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
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

}
