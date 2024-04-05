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
public class Seguir {
    private long id;
    private Pessoa origem; // Pessoa que está seguindo
    private Pessoa seguindo; // Pessoa que está sendo seguida
    private LocalDate dataCriacao;
    private LocalDate dataModificacao;

    public Seguir(long id, Pessoa origem, Pessoa seguindo, LocalDate dataCriacao, LocalDate dataModificacao) {
        this.id = id;
        this.origem = origem;
        this.seguindo = seguindo;
        this.dataCriacao = dataCriacao;
        this.dataModificacao = dataModificacao;
    }

    public Seguir(Pessoa origem, Pessoa seguindo, LocalDate now, LocalDate now0) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    public long getId() {
        return id;
    }

    public Pessoa getOrigem() {
        return origem;
    }

    public Pessoa getSeguindo() {
        return seguindo;
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
    
}
