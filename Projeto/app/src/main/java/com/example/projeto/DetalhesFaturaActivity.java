package com.example.projeto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.TextView;

import com.example.projeto.listeners.FaturaListener;
import com.example.projeto.modelo.Fatura;
import com.example.projeto.modelo.SingletonGestorApp;

public class DetalhesFaturaActivity extends AppCompatActivity implements FaturaListener {

    public static final String ID_FATURA = "id";
    private TextView tvdata, tvValotTotal, tvEstado, tvValor, tvValorIva, tvIdUser;
    private Fatura fatura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_fatura);

        tvdata = findViewById(R.id.tvData);
        tvValotTotal = findViewById(R.id.tvValorTotal);
        tvEstado = findViewById(R.id.tvEstado);
        tvValor = findViewById(R.id.tvValor);
        tvValorIva = findViewById(R.id.tvValorIva);
        tvIdUser = findViewById(R.id.tvIdUser);
        SingletonGestorApp.getInstance(getApplicationContext()).setFaturaListener(this);

        int id = getIntent().getIntExtra(ID_FATURA, 0);
        if (id != 0) {
            fatura = SingletonGestorApp.getInstance(getApplicationContext()).getFatura(id);
            if (fatura != null) {
                carregarFatura();
            } else {
                finish();
            }
        }
    }
    private void carregarFatura() {
        setTitle("Detalhes da Fatura: " + fatura.getId());

        tvdata.setText(fatura.getData());
        tvValotTotal.setText(String.valueOf(fatura.getValorTotal()));
        tvEstado.setText(fatura.getEstado());
        tvValor.setText(String.valueOf(fatura.getValor()));
        tvValorIva.setText(String.valueOf(fatura.getValorIva()));
        tvIdUser.setText(String.valueOf(fatura.getUsers_id()));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (fatura != null) {
            getMenuInflater().inflate(R.menu.menu_anular, menu);
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
        builder.setTitle("Anular Fatura")
                .setMessage("Tem a certeza que pretende remover a fatura?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SingletonGestorApp.getInstance(getApplicationContext()).anularFaturaAPI(fatura, getApplicationContext());
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

