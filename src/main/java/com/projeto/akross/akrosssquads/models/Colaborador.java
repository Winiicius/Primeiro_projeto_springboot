package com.projeto.akross.akrosssquads.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

@Entity
@Table(name = "TB_COLABORADOR")
public class Colaborador {
    
    @Id
    private String email;

    @Column(nullable = false)
    private String nome;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dataNascimento;
    
    @Transient
    private int idade;

    @ManyToOne
    @JoinColumn(name = "SQUAD_FK")
    private Squad squad;

    public Colaborador(){}

    public Colaborador(String email, Date dataNascimento, String nome){
        this.email = email;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    public Colaborador(String email, Date dataNascimento, String nome, Squad squad){
        this.email = email;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.squad = squad;
    }

    public void setSquad(Squad squad){
        this.squad = squad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.dataNascimento = dateFormat.parse(dataNascimento);
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public Squad getSquad() {
        return squad;
    }

}
