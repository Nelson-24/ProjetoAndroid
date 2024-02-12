package com.example.projeto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projeto.listeners.CarrinhoListener;
import com.example.projeto.modelo.Carrinho;
import com.example.projeto.modelo.SingletonGestorApp;

public class DetalhesCarrinhoActivity extends AppCompatActivity implements CarrinhoListener {

    public static final String ID_CARRINHO = "id";
    private TextView tvdata, tvValotTotal, tvEstado, tvValor, tvValorIva;
    private Carrinho carrinho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_carrinho);
        tvdata = findViewById(R.id.tvData);
        tvValotTotal = findViewById(R.id.tvValorTotal);
        tvEstado = findViewById(R.id.tvEstado);
        tvValor = findViewById(R.id.tvValor);
        tvValorIva = findViewById(R.id.tvValorIva);
        SingletonGestorApp.getInstance(getApplicationContext()).setCarrinhoListener(this);

        int id = getIntent().getIntExtra(ID_CARRINHO, 0);
        if (id != 0) {
            carrinho = SingletonGestorApp.getInstance(getApplicationContext()).getCarrinho(id);
            if (carrinho != null) {
                carregarCarrinho();
            } else {
                Toast.makeText(this, "Carrinho Inválido", Toast.LENGTH_SHORT).show();
            }
        } else {
            finish();
        }
    }
    private void carregarCarrinho() {
        setTitle("Detalhes do Carrinho");

        if (carrinho!= null) {
            tvdata.setText(carrinho.getData());
            tvValotTotal.setText(String.valueOf(carrinho.getValorTotal()));
            tvEstado.setText(carrinho.getEstado());
            tvValor.setText(String.valueOf(carrinho.getValor()));
            tvValorIva.setText(String.valueOf(carrinho.getValorIva()));
        }
    }


    @Override
    public void onRefreshDetalhes(int op) {
        Intent intent= new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}

