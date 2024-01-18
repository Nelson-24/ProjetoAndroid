package com.example.projeto.modelo;

public class LinhaCarrinho {
    private int id, quantidade, artigos_id, carrinhocompras_id;
    private float valor;
    private String referencia;

    public LinhaCarrinho(int id, int quantidade, float valor, String referencia, int artigos_id, int carrinhocompras_id) {
        this.id = id;
        this.quantidade = quantidade;
        this.valor = valor;
        this.referencia = referencia;
        this.artigos_id = artigos_id;
        this.carrinhocompras_id = carrinhocompras_id;
    }

    public int getId(){
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public float getValor() {
        return valor;
    }
    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getReferencia(){
        return referencia;
    }
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public int getArtigos_id() {
        return artigos_id;
    }
    public void setArtigos_id(int artigos_id) {
        this.artigos_id = artigos_id;
    }

    public int getCarrinhocompras_id() {
        return carrinhocompras_id;
    }
    public void setCarrinhocompras_id(int carrinhocompras_id) {
        this.carrinhocompras_id = carrinhocompras_id;
    }
}
