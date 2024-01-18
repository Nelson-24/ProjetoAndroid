package com.example.projeto.modelo;

public class User {
    private int id;
    private String username, email, nome, nif, morada, contacto;

    public User(int id, String username, String email, String nome, String nif, String morada, String contacto) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.nome = nome;
        this.nif = nif;
        this.morada = morada;
        this.contacto = contacto;
    }

    public int getId(){
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUsername(){
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome(){
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNif(){
        return nif;
    }
    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getMorada(){
        return morada;
    }
    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getContacto(){
        return contacto;
    }
    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

}
