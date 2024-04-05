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
public class AlimentoReceita {

    private long id;
    private Pessoa pessoa;
    private String nome;
    private double carboidratos;
    private double proteinas;
    private double gorduras;
    private double calorias;
    private double porcao;
    private LocalDate dataCriacao;
    private LocalDate dataModificacao;
    
    //construtor
    public AlimentoReceita(long id, Pessoa pessoa, String nome, double carboidratos, double proteinas, double gorduras, double porcao, LocalDate dataCriacao, LocalDate dataModificacao) {
        this.id = id;
        this.pessoa = pessoa;
        this.nome = nome;
        this.carboidratos = carboidratos;
        this.proteinas = proteinas;
        this.gorduras = gorduras;
        this.porcao = porcao;
        this.dataCriacao = dataCriacao;
        this.dataModificacao = dataModificacao;
        calcularCalorias();
    }

    private void calcularCalorias() {
        calorias = 4 * carboidratos + 4 * proteinas + 9 * gorduras;
    }

    public long getId() {
        return id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public String getNome() {
        return nome;
    }

    public double getCarboidratos() {
        return carboidratos;
    }

    public double getProteinas() {
        return proteinas;
    }

    public double getGorduras() {
        return gorduras;
    }

    public double getCalorias() {
        return calorias;
    }

    public double getPorcao() {
        return porcao;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public LocalDate getDataModificacao() {
        return dataModificacao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCarboidratos(double carboidratos) {
        this.carboidratos = carboidratos;
        calcularCalorias();
    }

    public void setProteinas(double proteinas) {
        this.proteinas = proteinas;
        calcularCalorias();
    }

    public void setGorduras(double gorduras) {
        this.gorduras = gorduras;
        calcularCalorias();
    }

    public void setCalorias(double calorias) {
        this.calorias = calorias;
    }

    public void setPorcao(double porcao) {
        this.porcao = porcao;
    }

    public void setDataModificacao(LocalDate dataModificacao) {
        this.dataModificacao = dataModificacao;
    }

    @Override
    public String toString() {
        return "\nNome Receita = " + nome + 
                "\nCriada por = " + pessoa.getNome() + 
                "\nId = " + id  + 
                "\nCarboidratos = " + carboidratos +
                "\nProteinas = " + proteinas + 
                "\nGorduras = " + gorduras + 
                "\nCalorias = " + calorias + 
                "\nPorcao = " + porcao + 
                "\nDataCriacao = " + dataCriacao + 
                "\nDataModificacao = " + dataModificacao;
    }
 
}
