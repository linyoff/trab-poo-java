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
public class PreferenciaAlimento {
    private long id;
    private Pessoa pessoa;
    private AlimentoReceita alimento;
    private LocalDate dataCriacao, dataModificacao;

    public PreferenciaAlimento (long id, Pessoa pessoa, AlimentoReceita alimento, LocalDate dataCriacao, LocalDate dataModificacao){
        this.id = id;
        this.pessoa = pessoa;
        this.alimento =  alimento;
        this.dataCriacao = dataCriacao;
        this.dataModificacao = dataModificacao;
    }
    
    public long getId() {
        return id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public AlimentoReceita getAlimento() {
        return alimento;
    }

    public void setAlimento(AlimentoReceita alimento) {
        this.alimento = alimento;
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

    @Override
    public String toString() {
        return "\nId da preferência = " + id + 
                "\nAlimento = " + alimento + 
                "\nDataCriacao = " + dataCriacao + 
                "\nData Modificação = " + dataModificacao + "\n";
    }
    
}
