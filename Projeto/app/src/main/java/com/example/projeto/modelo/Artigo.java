package com.example.projeto.modelo;

import java.util.ArrayList;
import java.util.List;

public class Artigo {
    private int id, stock, categoria_id, ivas_id;
    private float preco;
    private String referencia,descricao, imagem;

    public Artigo(int id, String referencia, float preco, int stock, int categoria_id, int ivas_id, String descricao, String imagem) {
        this.id = id;
        this.referencia = referencia;
        this.preco = preco;
        this.stock = stock;
        this.descricao = descricao;
        this.categoria_id = categoria_id;
        this.ivas_id = ivas_id;
        this.imagem = imagem;
    }

    public int getId(){
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getReferencia(){
        return referencia;
    }
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public float getPreco(){
        return preco;
    }
    public void setPreco(float preco) {
        this.preco = preco;
    }

    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getIdCategoria() {
        return categoria_id;
    }
    public void setIdCategoria(int idCategoria) {
        this.categoria_id = idCategoria;
    }

    public int getIdIva() {
        return ivas_id;
    }
    public void setIdIva(int idIva) {
        this.ivas_id = idIva;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

}
