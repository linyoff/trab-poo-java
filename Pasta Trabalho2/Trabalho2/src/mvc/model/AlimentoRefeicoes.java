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
public class AlimentoRefeicoes {
    
    private long id;
    private Refeicoes refeicao;
    private AlimentoReceita alimento;
    private double porcao;
    private double proteina;
    private double gordura;
    private double carboidrato;
    private double calorias;
    private LocalDate dataCriacao;
    private LocalDate dataModificacao;

    public AlimentoRefeicoes(long id, AlimentoReceita alimento, Refeicoes refeicao, double porcao,  double proteina, double gordura, double carboidrato, LocalDate dataCriacao, LocalDate dataModificacao) {
        this.id = id;
        this.alimento = alimento;
        this.refeicao = refeicao;
        this.porcao = porcao;
        this.proteina = proteina;
        this.gordura = gordura;
        this.carboidrato = gordura;
        calcularNutrientes();
        this.dataCriacao = dataCriacao;
        this.dataModificacao = dataModificacao;
    }

    private double calcularCalorias() {
        return calorias = 4 * carboidrato + 4 * proteina + 9 * gordura;
    }
    
    private void calcularNutrientes() {
        this.proteina = (proteina * porcao) / 100.0;
        this.gordura = (gordura * porcao) / 100.0;
        this.calorias = (calcularCalorias() * porcao) / 100.0;
    }

    public long getId() {
        return id;
    }

    public void setProteina(double proteina) {
        this.proteina = proteina;
        calcularNutrientes();
    }

    public void setGordura(double gordura) {
        this.gordura = gordura;
        calcularNutrientes();
    }

    public void setCalorias(double calorias) {
        this.calorias = calorias;
        calcularNutrientes();
    }

    public Refeicoes getRefeicao() {
        return refeicao;
    }

    public void setRefeicao(Refeicoes refeicao) {
        this.refeicao = refeicao;
    }

    public AlimentoReceita getAlimento() {
        return alimento;
    }

    public double getPorcao() {
        return porcao;
    }

    public void setPorcao(double porcao) {
        this.porcao = porcao;
        calcularNutrientes();
    }

    public double getProteina() {
        return proteina;
    }

    public double getGordura() {
        return gordura;
    }
    public double getCarboidrato() {
        return carboidrato;
    }

    public double getCalorias() {
        return calorias;
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
        return "\n\nAlimentoRefeicoes" + 
                "\nId = " + id + 
                "\nRefeicao = " + refeicao.getNomeRefeicao() +
                "\nAlimento = " + alimento.getNome() +
                "\nPorcao = " + porcao +
                "\nProteina = " + proteina +
                "\nGordura = " + gordura +
                "\nCalorias = " + calorias +
                "\nDataCriacao = " + dataCriacao +
                "\nDataModificacao = " + dataModificacao;
    }

}
