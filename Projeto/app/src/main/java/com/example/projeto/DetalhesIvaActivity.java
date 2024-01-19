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

import com.example.projeto.listeners.IvaListener;
import com.example.projeto.modelo.Iva;
import com.example.projeto.modelo.SingletonGestorApp;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetalhesIvaActivity extends AppCompatActivity implements IvaListener {

    public static final String ID_IVA = "id";
    private final int MIN_CHAR = 3;
    private EditText etDescricao, etPercentagem;
    private FloatingActionButton fabGuardar;
    private Iva iva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_iva);
        SharedPreferences sharedPreferences = getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
        String role = sharedPreferences.getString("ROLE", "");

        etDescricao = findViewById(R.id.etDescricao);
        etPercentagem = findViewById(R.id.etPercentagem);
        fabGuardar = findViewById(R.id.fabGuardar);
        SingletonGestorApp.getInstance(getApplicationContext()).setIvaListener(this);

        int id = getIntent().getIntExtra(ID_IVA, 0);
        if (id != 0) {
            iva = SingletonGestorApp.getInstance(getApplicationContext()).getIva(id);
            if (iva != null) {
                carregarIva();
                fabGuardar.setImageResource(R.drawable.ic_action_guardar);
            } else {
                // Algo de errado aconteceu
                finish();
            }
        } else {
            setTitle("Adicionar IVA");
            fabGuardar.setImageResource(R.drawable.ic_action_adicionar);
        }

        if ("cliente".equals(role)) {
            etDescricao.setKeyListener(null);
            etPercentagem.setKeyListener(null);
            fabGuardar.setVisibility(View.GONE);
        } else {
            fabGuardar.setVisibility(View.VISIBLE);
            fabGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (iva != null) { // IVA já existe
                        if (isIvaValido()) {
                            iva.setPercentagem(Float.parseFloat(etPercentagem.getText().toString()));
                            iva.setDescricao(etDescricao.getText().toString());
                            SingletonGestorApp.getInstance(getApplicationContext()).editarIvaAPI(iva, getApplicationContext());
                        }
                    } else { // IVA para criar
                        if (isIvaValido()) {
                            iva = new Iva(0, Float.parseFloat(etPercentagem.getText().toString()), etDescricao.getText().toString());
                            SingletonGestorApp.getInstance(getApplicationContext()).adicionarIvaAPI(iva, getApplicationContext());
                        }
                    }
                }
            });
        }
    }

    private boolean isIvaValido() {
        String percentagem = etPercentagem.getText().toString();
        String descricao = etDescricao.getText().toString();

        if (percentagem.isEmpty()) {
            etPercentagem.setError("Percentagem inválida");
            return false;
        }

        if (descricao.length() < MIN_CHAR) {
            etDescricao.setError("Descrição inválida");
            return false;
        }

        return true;
    }

    private void carregarIva() {
        setTitle("Detalhes: " + iva.getDescricao());
        etPercentagem.setText(iva.getPercentagem()+"");
        etDescricao.setText(iva.getDescricao());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SharedPreferences sharedPreferences = getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
        String role = sharedPreferences.getString("ROLE", "");

        if (!"cliente".equals(role)) {
            if (iva != null) {
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
        builder.setTitle("Remover IVA")
                .setMessage("Tem a certeza que pertende remover o IVA?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SingletonGestorApp.getInstance(getApplicationContext()).removerIvaAPI(iva, getApplicationContext());
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
