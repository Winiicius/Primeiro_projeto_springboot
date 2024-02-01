package com.projeto.akross.akrosssquads.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "TB_EMPRESA")
public class Empresa {
    
    @Id
    private String cnpj;

    @Transient // Singleton
    private static Empresa empresa;

    @Column(nullable = false)
    private String nome;

    @OneToMany(mappedBy = "empresa")
    private List<Squad> squads = new ArrayList<Squad>();

    private Empresa(){
        this.cnpj = "001";
        this.nome = "Akross";
    }

    public static synchronized Empresa getInstancia(){
        if(empresa == null){ return new Empresa(); }
        else{ return empresa; }
    }

    public void adicionarSquad(Squad squad){
        squads.add(squad);
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public static Empresa getEmpresa() {
        return empresa;
    }

    public static void setEmpresa(Empresa empresa) {
        Empresa.empresa = empresa;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Squad> getSquads() {
        return squads;
    }

    public void setSquads(List<Squad> squads) {
        this.squads = squads;
    }

    

}
