/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mvc.model;
import java.time.LocalDate;

public class TipoDieta {
    private long id;
    private String nome;
    private double carboidrato;
    private double proteina;
    private double gordura;
    private LocalDate dataCriacao, dataModificacao;

    public TipoDieta(long id, String nome, double carboidrato, double proteina, double gordura, LocalDate dataCriacao, LocalDate dataModificacao) {
        this.id = id;
        this.nome = nome;
        this.carboidrato = carboidrato;
        this.proteina = proteina;
        this.gordura = gordura;
        this.dataCriacao = dataCriacao;
        this.dataModificacao = dataModificacao;
    }

    public long getId() {
        return id;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getCarboidrato() {
        return carboidrato;
    }

    public void setCarboidrato(double carboidrato) {
        this.carboidrato = carboidrato;
    }

    public double getProteina() {
        return proteina;
    }

    public void setProteina(double proteina) {
        this.proteina = proteina;
    }

    public double getGordura() {
        return gordura;
    }

    public void setGordura(double gordura) {
        this.gordura = gordura;
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
        return "TipoDieta -> " + 
                "\nId = " + id + 
                "\nNome = " + nome + 
                "\nCarboidrato = " + carboidrato + 
                "\nProteína = " + proteina + 
                "\nGordura = " + gordura + 
                "\nData Criação=" + dataCriacao + 
                "\nData Modificação=" + dataModificacao + "\n";
    }
    
}

