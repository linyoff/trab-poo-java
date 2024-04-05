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
public class Post {
    private long id;
    private Pessoa autor;
    private String conteudoDaMensagem;
    private LocalDate dataCriacao, dataModificacao;


    public Post(long id, Pessoa pessoa, String conteudoDaMensagem, LocalDate dataCriacao, LocalDate dataModificacao){
        this.id = id;
        this.autor = pessoa;
        this.conteudoDaMensagem =  conteudoDaMensagem;
        this.dataCriacao = dataCriacao;
        this.dataModificacao = dataModificacao;
    }
    
    public long getId() {
        return id;
    }

    public Pessoa getAutor() {
        return autor;
    }

    public String getConteudoDaMensagem() {
        return conteudoDaMensagem;
    }

    public void setConteudoDaMensagem(String conteudoDaMensagem) {
        this.conteudoDaMensagem = conteudoDaMensagem;
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
        return "\nPost de " + autor.getNome() + ":\n" + conteudoDaMensagem + "\ndata do post:" + dataCriacao + "\ndata de modificação: " + dataModificacao + "\n";
    }
   
}
