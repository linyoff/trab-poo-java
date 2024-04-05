/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mvc.model;

import java.time.LocalDate;
import java.time.Period;

/**
 *
 * @author josea
 */

public class Pessoa {
    private long id;
    private int idade;
    private String nome;
    private String sexo;
    private LocalDate nascimento;
    private String login;
    private String senha;
    private String tipoUsuario;
    private LocalDate dataCriacao;
    private LocalDate dataModificacao;

    public Pessoa(long id, String nome, String sexo, LocalDate nascimento, String login, String senha, LocalDate dataCriacao, LocalDate dataModificacao) {
        this.id = id;
        this.idade = calcularIdade(nascimento);
        this.nome = nome;
        this.sexo = sexo;
        this.nascimento = nascimento;
        this.login = login;
        this.senha = senha;
        this.dataCriacao = dataCriacao;
        this.dataModificacao = dataModificacao;
    }
       
    private int calcularIdade(LocalDate dataNascimento) {
        LocalDate dataAtual = LocalDate.now();
        Period periodo = Period.between(dataNascimento, dataAtual);
        return periodo.getYears();
    }
    
    public long getId() {
        return id;
    }
    
    public int getIdade() {
        return idade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
        this.idade = calcularIdade(nascimento);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }
    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDate getDataModificacao() {
        return dataModificacao;
    }

    public void setDataModificacao(LocalDate dataModificacao) {
        this.dataModificacao = dataModificacao;
    }
    
    @Override
    public String toString() {
        return "\nId = " + id + 
                "\nNome = " + nome + 
                "\nSexo = " + sexo + 
                "\nId = " + idade +
                "\nData de Nascimento = " + nascimento + 
                "\nLogin = " + login + 
                "\nData Criacão = " + dataCriacao + 
                "\nData Modificação = " + dataModificacao + "\n";
    }
    
}