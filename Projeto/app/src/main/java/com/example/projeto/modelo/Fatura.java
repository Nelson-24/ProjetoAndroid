package com.example.projeto.modelo;

public class Fatura {
    private int id, opcaoEntrega, users_id;
    private double valorTotal, valor, valorIva;
    private String data, estado;

    public Fatura(int id, String data, double valorTotal, String estado, int opcaoEntrega, double valor, double valorIva, int users_id) {
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

    public double getValorTotal(){
        return valorTotal;
    }
    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getOpcaoEntrega() {
        return opcaoEntrega;
    }
    public void setOpcaoEntrega(int opcaoEntrega) {
        this.opcaoEntrega = opcaoEntrega;
    }

    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getValorIva() {
        return valorIva;
    }
    public void setValorIva(double valorIva) {
        this.valorIva = valorIva;
    }

    public int getUsers_id() {
        return users_id;
    }
    public void setUsers_id(int users_id) {
        this.users_id = users_id;
    }
}
