package com.example.projeto.modelo;


public class Carrinho {
    private int id, users_id;
    private float valorTotal, valor, valorIva;
    private String data, estado, opcaoEntrega;

    public Carrinho(int id, String data, float valorTotal, String estado, String opcaoEntrega, float valor, float valorIva, int users_id) {
        this.id = id;
        this.data = data;
        this.valorTotal = valorTotal;
        this.estado = estado;
        this.opcaoEntrega = opcaoEntrega;
        this.valor = valor;
        this.valorIva = valorIva;
        this.users_id = users_id;
    }

    public int getId(){
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getData(){
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }

    public float getValorTotal(){
        return valorTotal;
    }
    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getOpcaoEntrega() {
        return opcaoEntrega;
    }
    public void setOpcaoEntrega(String opcaoEntrega) {
        this.opcaoEntrega = opcaoEntrega;
    }

    public float getValor() {
        return valor;
    }
    public void setValor(int valor) {
        this.valor = valor;
    }

    public float getValorIva() {
        return valorIva;
    }
    public void setValorIva(int valorIva) {
        this.valorIva = valorIva;
    }

    public int getUsers_id() {
        return users_id;
    }
    public void setUsers_id(int users_id) {
        this.users_id = users_id;
    }
}
