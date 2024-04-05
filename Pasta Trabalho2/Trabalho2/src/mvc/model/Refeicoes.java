/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mvc.model;

import java.time.LocalDate;



/**
 *
 * @author Aliny
 */
public class Refeicoes {
    private long id;
    private RegistroDieta dieta;
    private double carboidrato;
    private double proteina;
    private double gordura;
    private double calorias;
    private String nomeRefeicao;
    private LocalDate dataCriacao;
    private LocalDate dataModificacao;

    
    public Refeicoes(long id, RegistroDieta dieta, String nomeRefeicao, double carboidratos, double proteinas, double gorduras, LocalDate dataCriacao, LocalDate dataModificacao) {
        this.id = id;
        this.dieta = dieta;
        this.nomeRefeicao =  nomeRefeicao;
        this.carboidrato = carboidratos;
        this.proteina = proteinas;
        this.gordura = gorduras;
        this.dataCriacao = dataCriacao;
        this.dataModificacao = dataModificacao;
        calcularCalorias();
    }

    public long getId() {
        return id;
    }

    public RegistroDieta getDieta() {
        return dieta;
    }

    public void setDieta(RegistroDieta dieta) {
        this.dieta = dieta;
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
        calcularCalorias();
    }

    public double getGordura() {
        return gordura;
    }

    public void setGordura(double gordura) {
        this.gordura = gordura;
        calcularCalorias();
    }

    public double getCalorias() {
        return calorias;
    }

    public String getNomeRefeicao() {
        return nomeRefeicao;
    }

    public void setNomeRefeicao(String nomeRefeicao) {
        this.nomeRefeicao = nomeRefeicao;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }
    
    public LocalDate getDataModificacao() {
        return dataModificacao;
    }

    public void setDataModificacao(LocalDate dataModificacao) {
        this.dataModificacao = dataModificacao;
    }
    
    private void calcularCalorias() {
        calorias = 4 * carboidrato + 4 * proteina + 9 * gordura;
    }

    @Override
    public String toString() {
        return "\nNome da Refeicao = " + nomeRefeicao + 
                "\nId = " + id +
                "\nDieta = " + dieta.getTipoDieta().getNome() + 
                "\nCarboidrato = " + carboidrato + 
                "\nProteina = " + proteina + 
                "\nGordura = " + gordura + 
                "\nCalorias = "  + calorias +
                "\nDataCriacao = " + dataCriacao + 
                "\nDataModificacao = " + dataModificacao + "\n";
    }
    

}
