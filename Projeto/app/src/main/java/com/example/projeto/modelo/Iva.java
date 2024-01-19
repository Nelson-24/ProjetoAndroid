package com.example.projeto.modelo;

public class Iva {
    private int id;

    private float percentagem;
    private String descricao;

    public Iva(int id, Float percentagem, String descricao) {
        this.id = id;
        this.percentagem = percentagem;
        this.descricao = descricao;
    }

    public int getId(){
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public float getPercentagem(){
        return percentagem;
    }
    public void setPercentagem(float percentagem) {
        this.percentagem = percentagem;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
