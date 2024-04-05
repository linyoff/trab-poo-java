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
public class Mensagem {
    private long id;
    private Pessoa origem; // Pessoa que envia
    private Pessoa destino; // Pessoa que recebe
    private String mensagemTexto;
    private LocalDate dataCriacao;
    private LocalDate dataModificacao;

    public Mensagem(long id, Pessoa origem, Pessoa destino, String mensagemTexto, LocalDate dataCriacao, LocalDate dataModificacao) {
        this.id = id;
        this.origem = origem;
        this.destino = destino;
        this.mensagemTexto = mensagemTexto;
        this.dataCriacao = dataCriacao;
        this.dataModificacao = dataModificacao;
    }

    public long getId() {
        return id;
    }

    public Pessoa getOrigem() {
        return origem;
    }

    public Pessoa getDestino() {
        return destino;
    }

    public String getMensagem() {
        return mensagemTexto;
    }

    public void setMensagem(String mensagem) {
        this.mensagemTexto = mensagem;
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
        return "\nMensagem de " + origem.getNome() + ":\n" + mensagemTexto + "\nenviada em: " + dataCriacao+ "\n";
    }
    
}
