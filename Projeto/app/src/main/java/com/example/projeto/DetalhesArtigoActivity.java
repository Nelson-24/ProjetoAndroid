package com.example.projeto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.projeto.listeners.ArtigoListener;
import com.example.projeto.modelo.Artigo;
import com.example.projeto.modelo.SingletonGestorArtigos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetalhesArtigoActivity extends AppCompatActivity implements ArtigoListener{

    public static final String ID_ARTIGO="id";
    private final int MIN_CHAR=3, MIN_NUMEROS=1;
    private EditText etReferencia, etPreco, etStock, etDescricao, etCategoria;
    private FloatingActionButton fabGuardar;
    private ImageView imgFoto;
    private Artigo artigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_artigo);

        etReferencia=findViewById(R.id.etReferencia);
        etPreco=findViewById(R.id.etPreco);
        etStock=findViewById(R.id.etStock);
        etDescricao=findViewById(R.id.etDescricao);
        etCategoria=findViewById(R.id.etCategoria);
        imgFoto=findViewById(R.id.imgFoto);
        fabGuardar=findViewById(R.id.fabGuardar);
        SingletonGestorArtigos.getInstance(getApplicationContext()).setArtigoListener(this);

        int id=getIntent().getIntExtra(ID_ARTIGO, 0);
        if (id!=0) {
            artigo = SingletonGestorArtigos.getInstance(getApplicationContext()).getArtigo(id);
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

        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (artigo!=null){ //artigo ja existe
                    if (isArtigoValido()){
                        artigo.setReferencia(Integer.parseInt(etReferencia.getText().toString()));
                        artigo.setPreco(Integer.parseInt(etPreco.getText().toString()));
                        artigo.setStock(Integer.parseInt(etStock.getText().toString()));
                        artigo.setIdCategoria(Integer.parseInt(etCategoria.getText().toString()));
                        artigo.setDescricao(etDescricao.getText().toString());
                        SingletonGestorArtigos.getInstance(getApplicationContext()).editarArtigoAPI(artigo,getApplicationContext());
                    }
                }
                else{ //artigo para criar
                    if (isArtigoValido()){
                        artigo=new Artigo(0, Integer.parseInt(etReferencia.getText().toString()), Integer.parseInt(etPreco.getText().toString()),
                                Integer.parseInt(etStock.getText().toString()), Integer.parseInt(etCategoria.getText().toString()), etDescricao.getText().toString());
                        SingletonGestorArtigos.getInstance(getApplicationContext()).adicionarArtigoAPI(artigo,getApplicationContext());
                    }
                }
            }
        });
    }

    private boolean isArtigoValido() {
        String referencia= etReferencia.getText().toString();
        String preco= etPreco.getText().toString();
        String stock= etStock.getText().toString();
        String descricao= etDescricao.getText().toString();
        String categoria= etCategoria.getText().toString();

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

        return true;
    }

    private void carregarArtigo() {
        setTitle("Detalhes: "+artigo.getDescricao());
        etReferencia.setText(artigo.getReferencia() + "");
        etPreco.setText(artigo.getPreco() + "");
        etStock.setText(artigo.getStock() + "");
        etCategoria.setText(artigo.getIdCategoria() + "");
        etDescricao.setText(artigo.getDescricao());
        //imgFoto.setText(artigo.getFoto());
        Glide.with(getApplicationContext())
                .load(artigo.getFoto())
                .placeholder(R.drawable.logocrm)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgFoto);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (artigo != null) {
            getMenuInflater().inflate(R.menu.menu_pesquisa, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId()==R.id.itemRemover){
//            dialogRemover();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }


    private void dialogRemover() {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Remover Artigo")
                .setMessage("Tem a certeza que pertende remover o artigo?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //se confirmar que quero eliminar o livro
                        SingletonGestorArtigos.getInstance(getApplicationContext()).removerArtigoAPI(artigo, getApplicationContext());
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //se nao quero eliminar o artigo nao faz nada
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