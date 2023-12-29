package com.example.projeto.modelo;

public class Artigo {
    private int id, referencia, preco, stock, categoria_id;
    private String descricao, foto;

    public Artigo(int id, int referencia, int preco, int stock, int categoria_id, String descricao) {
        this.id = id;
        this.referencia = referencia;
        this.preco = preco;
        this.stock = stock;
        this.descricao = descricao;
        this.categoria_id = categoria_id;
        //this.foto = foto;
    }

    public int getId(){
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getReferencia(){
        return referencia;
    }
    public void setReferencia(int referencia) {
        this.referencia = referencia;
    }

    public int getPreco(){
        return preco;
    }
    public void setPreco(int preco) {
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
