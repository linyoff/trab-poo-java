/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mvc.model;
import java.time.LocalDate;

public class RegistroDieta {
    private long id;
    private Pessoa pessoa;
    private AvaliacaoFisica avaliacaoFisica;
    private TipoDieta tipoDieta;
    private String objetivo;
    private double calorias;
    private int numeroRefeicoes;
    private LocalDate dataCriacao, dataModificacao;

    public RegistroDieta(long id, Pessoa pessoa, AvaliacaoFisica avaliacaoFisica, TipoDieta tipoDieta, String objetivo, int numeroRefeicoes, LocalDate dataCriacao, LocalDate dataModificacao) {
        this.id = id;
        this.pessoa = pessoa;
        this.avaliacaoFisica = avaliacaoFisica;
        this.tipoDieta = tipoDieta;
        this.objetivo = objetivo;
        this.numeroRefeicoes = numeroRefeicoes;
        this.dataCriacao = dataCriacao;
        this.dataModificacao = dataModificacao;
        calcularCaloriasDaDieta();
    }

    public long getId() {
        return id;
    }

    public Pessoa getP() {
        return pessoa;
    }

    public void setP(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public AvaliacaoFisica getAvaliacaoFisica() {
        return avaliacaoFisica;
    }

    public void setAvaliacaoFisica(AvaliacaoFisica avaliacaoFisica) {
        this.avaliacaoFisica = avaliacaoFisica;
    }

    public TipoDieta getTipoDieta() {
        return tipoDieta;
    }

    public void setTipoDieta(TipoDieta tipoDieta) {
        this.tipoDieta = tipoDieta;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public double getCalorias() {
        return calorias;
    }

    public void setCalorias(double calorias) {
        this.calorias = calorias;
    }

    public int getNumeroRefeicoes() {
        return numeroRefeicoes;
    }

    public void setNumeroRefeicoes(int numeroRefeicoes) {
        this.numeroRefeicoes = numeroRefeicoes;
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

    
    public double calcularCaloriasDaDieta() {
    double tmb = avaliacaoFisica.TMB();

        if (objetivo.equals("DIMINUIR O PESO")) {
            calorias = tmb - 200; 
        } else if (objetivo.equals("MANTER O PESO")) {
            calorias = tmb;
        } else if (objetivo.equals("MELHORAR COMPOSICAO CORPORAL")) {
            calorias = tmb; 
        } else if (objetivo.equals("AUMENTAR O PESO")) {
            calorias = tmb + 200; 
        }
        return calorias;
    }
    
    @Override
    public String toString() {
        return "\nId = " + id + 
                "\nPessoa = " + pessoa + 
                "\nAvaliacao Fisica = " + avaliacaoFisica.getId() +
                "\nTipo Dieta = " + tipoDieta + 
                "\nObjetivo = " + objetivo + 
                "\nCalorias = " + calorias + 
                "\nNumero Refeicoes = " + numeroRefeicoes + 
                "\nData Criacao = " + dataCriacao + 
                "\nData Modificacao = " + dataModificacao;
    }
}

