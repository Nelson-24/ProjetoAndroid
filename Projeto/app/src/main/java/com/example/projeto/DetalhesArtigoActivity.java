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
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.projeto.listeners.ArtigoListener;
import com.example.projeto.modelo.Artigo;
import com.example.projeto.modelo.SingletonGestorApp;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetalhesArtigoActivity extends AppCompatActivity implements ArtigoListener{

    public static final String ID_ARTIGO="id";
    public static final String DEFAULT_IMG = "http://10.0.2.2/ProjetoPSI_/frontend/web/images/logotipo-loja.png";
    private final int MIN_CHAR=3, MIN_NUMEROS=1;
    private EditText etReferencia, etPreco, etStock, etDescricao, etCategoria, etIva;
    private FloatingActionButton fabGuardar;
    private ImageView imgImagem;
    private Artigo artigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_artigo);
        SharedPreferences sharedPreferences = getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
        String role = sharedPreferences.getString("ROLE", "");

        etReferencia=findViewById(R.id.etReferencia);
        etPreco=findViewById(R.id.etPreco);
        etStock=findViewById(R.id.etStock);
        etDescricao=findViewById(R.id.etDescricao);
        etCategoria=findViewById(R.id.etCategoria);
        etIva=findViewById(R.id.etIva);
        imgImagem=findViewById(R.id.imgImagem);
        fabGuardar=findViewById(R.id.fabGuardar);
        SingletonGestorApp.getInstance(getApplicationContext()).setArtigoListener(this);

        int id=getIntent().getIntExtra(ID_ARTIGO, 0);
        if (id!=0) {
            artigo = SingletonGestorApp.getInstance(getApplicationContext()).getArtigo(id);
            if (artigo!=null){
                carregarArtigo();
                fabGuardar.setImageResource(R.drawable.ic_action_guardar);
            }
            else //significa que algo de errado aconteceu
                finish();
        }
        else {
            setTitle("Adicionar Artigo");
            fabGuardar.setImageResource(R.drawable.ic_action_adicionar);
        }
        if ("cliente".equals(role)) {
            etReferencia.setKeyListener(null);
            etPreco.setKeyListener(null);
            etStock.setKeyListener(null);
            etDescricao.setKeyListener(null);
            etCategoria.setKeyListener(null);
            etIva.setKeyListener(null);

            fabGuardar.setVisibility(View.GONE);
        } else {
            fabGuardar.setVisibility(View.VISIBLE);
            fabGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (artigo!=null){ //artigo ja existe
                        if (isArtigoValido()){
                            artigo.setReferencia(etReferencia.getText().toString());
                            artigo.setPreco(Float.parseFloat(etPreco.getText().toString()));
                            artigo.setStock(Integer.parseInt(etStock.getText().toString()));
                            artigo.setIdCategoria(Integer.parseInt(etCategoria.getText().toString()));
                            artigo.setIdIva(Integer.parseInt(etIva.getText().toString()));
                            artigo.setDescricao(etDescricao.getText().toString());
                            SingletonGestorApp.getInstance(getApplicationContext()).editarArtigoAPI(artigo,getApplicationContext());
                        }
                    }
                    else{ //artigo para criar
                        if (isArtigoValido()){
                            artigo=new Artigo(0, etReferencia.getText().toString(), Float.parseFloat(etPreco.getText().toString()),
                                    Integer.parseInt(etStock.getText().toString()), Integer.parseInt(etCategoria.getText().toString()),
                                    Integer.parseInt(etIva.getText().toString()), etDescricao.getText().toString(),imgImagem.toString());
                            SingletonGestorApp.getInstance(getApplicationContext()).adicionarArtigoAPI(artigo,getApplicationContext());
                        }
                    }
                }
            });
        }
    }

    private boolean isArtigoValido() {
        String referencia= etReferencia.getText().toString();
        String preco= etPreco.getText().toString();
        String stock= etStock.getText().toString();
        String categoria= etCategoria.getText().toString();
        String iva= etIva.getText().toString();
        String descricao= etDescricao.getText().toString();

        if (referencia.length()<MIN_NUMEROS) {
            etReferencia.setError("Referência inválida");
            return false;
        }
        if (preco.length()<MIN_NUMEROS) {
            etPreco.setError("Preço inválido");
            return false;
        }
        if (stock.length()<MIN_NUMEROS) {
            etStock.setError("Stock inválido");
            return false;
        }
        if (descricao.length()<MIN_CHAR) {
            etDescricao.setError("Descrição inválida");
            return false;
        }
        if (categoria.length()<MIN_NUMEROS) {
            etCategoria.setError("Id Categoria inválido");
            return false;
        }
        if (iva.length()<MIN_NUMEROS) {
            etIva.setError("Id Categoria inválido");
            return false;
        }

        return true;
    }

    private void carregarArtigo() {
        setTitle("Detalhes: "+artigo.getDescricao());
        etReferencia.setText(artigo.getReferencia() + "");
        etPreco.setText(artigo.getPreco() + "");
        etStock.setText(artigo.getStock() + "");
        etCategoria.setText(artigo.getIdCategoria() + "");
        etIva.setText(artigo.getIdIva() + "");
        etDescricao.setText(artigo.getDescricao());
        Glide.with(getApplicationContext())
                .load(artigo.getImagem())
                .placeholder(R.drawable.logocrm)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgImagem);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SharedPreferences sharedPreferences = getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
        String role = sharedPreferences.getString("ROLE", "");

        if ("cliente".equals(role)) {
            //todo: iniciar o menu de adicioanr artigo ao carrinho
        }
        else{
            if (artigo != null) {
                getMenuInflater().inflate(R.menu.menu_remover, menu);
                return true;
            }
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.itemRemover){
            dialogRemover();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void dialogRemover() {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Remover Artigo")
                .setMessage("Tem a certeza que pertende remover o artigo?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SingletonGestorApp.getInstance(getApplicationContext()).removerArtigoAPI(artigo, getApplicationContext());
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