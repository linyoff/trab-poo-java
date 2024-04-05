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
public class AvaliacaoFisica {

    private Pessoa p;
    private long id;
    private double pescoco, quadril, cintura, altura, peso, fatorAtividade;
    private LocalDate dataModificacao, dataCriacao;

    public AvaliacaoFisica(long id, Pessoa p, double pescoco, double quadril, double cintura, double altura, double peso, double fatorAtividade, LocalDate dataCriacao, LocalDate dataModificacao) {
        this.id = id;
        this.p = p;
        this.pescoco = pescoco;
        this.quadril = quadril;
        this.cintura = cintura;
        this.altura = altura;
        this.peso = peso;
        this.fatorAtividade = fatorAtividade;
        this.dataCriacao = dataCriacao;
        this.dataModificacao = dataModificacao;

    }

    public Pessoa getP() {
        return p;
    }

    public long getId() {
        return id;
    }

    public double getPescoco() {
        return pescoco;
    }

    public void setPescoco(double pescoco) {
        this.pescoco = pescoco;
    }

    public double getQuadril() {
        return quadril;
    }

    public void setQuadril(double quadril) {
        this.quadril = quadril;
    }

    public double getCintura() {
        return cintura;
    }

    public void setCintura(double cintura) {
        this.cintura = cintura;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getFatorAtividade() {
        return fatorAtividade;
    }

    public void setFatorAtividade(double fatorAtividade) {
        this.fatorAtividade = fatorAtividade;
    }

    public LocalDate getDataModificacao() {
        return dataModificacao;
    }

    public void setDataModificacao(LocalDate dataModificacao) {
        this.dataModificacao = dataModificacao;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public double IMC() {
        double imc;
        imc = getPeso() / (getAltura() * getAltura());
        return imc;
    }

    public double TMB() {
        double tmb = 0;
        String sexo = String.valueOf(p.getSexo()).toLowerCase(); // Converte char para String e coloca em minúsculo

        if ("feminino".equals(sexo)) {
            tmb = getFatorAtividade() * (66 + (13.7 * getPeso()) + (1.8 * getAltura()) - (4.7 * p.getIdade()));
        } else if ("masculino".equals(sexo)) {
            tmb = getFatorAtividade() * (655 + (9.6 * getPeso()) + (1.8 * getAltura()) - (4.7 * p.getIdade()));
        }
        return tmb;
    }

    public double calcularPercentualGordura() {
        String sexo = String.valueOf(p.getSexo()).toLowerCase(); // Converte char para String e coloca em minúsculo

        if ("feminino".equals(sexo)) {
            double percentualGordura = 163.205 * Math.log10(getCintura() + getQuadril() - getPescoco()) - 97.684 * Math.log10(getAltura()) - 78.387;
            return percentualGordura;
        } else if ("masculino".equals(sexo)) {
            double percentualGordura = 86.010 * Math.log10(getCintura() - getPescoco()) - 70.041 * Math.log10(getAltura()) + 36.76;
            return percentualGordura;
        } else {
            return -1;
        }
    }

    public void gerarRelatorioGorduraCorporal(AvaliacaoFisica af) {
        String sexo = af.getP().getSexo();
        int idade = af.getP().getIdade();
        double percentualGordura = af.calcularPercentualGordura();

        System.out.println("\nRelatório de Percentual de Gordura Corporal:");
        System.out.println("Sexo: " + sexo);
        System.out.println("Idade: " + idade + " anos");
        System.out.printf("Percentual de Gordura: %.2f%%\n", percentualGordura);

        if (sexo.equals("homem")) {
            if (idade >= 20 && idade <= 29) {
                if (percentualGordura < 11) {
                    System.out.println("Classificação: Atleta");
                } else if (percentualGordura >= 11 && percentualGordura < 13) {
                    System.out.println("Classificação: Bom");
                } else if (percentualGordura >= 13 && percentualGordura < 15) {
                    System.out.println("Classificação: Normal");
                } else if (percentualGordura >= 15 && percentualGordura < 17) {
                    System.out.println("Classificação: Elevado");
                } else {
                    System.out.println("Classificação: Muito elevado");
                }
            } else if (idade >= 30 && idade <= 39) {
                if (percentualGordura < 11) {
                    System.out.println("Classificação: Atleta");
                } else if (percentualGordura >= 11 && percentualGordura < 13) {
                    System.out.println("Classificação: Bom");
                } else if (percentualGordura >= 13 && percentualGordura < 15) {
                    System.out.println("Classificação: Normal");
                } else if (percentualGordura >= 15 && percentualGordura < 17) {
                    System.out.println("Classificação: Elevado");
                } else {
                    System.out.println("Classificação: Muito elevado");
                }
            } else if (idade >= 40 && idade <= 49) {
                if (percentualGordura < 11) {
                    System.out.println("Classificação: Atleta");
                } else if (percentualGordura >= 11 && percentualGordura < 13) {
                    System.out.println("Classificação: Bom");
                } else if (percentualGordura >= 13 && percentualGordura < 15) {
                    System.out.println("Classificação: Normal");
                } else if (percentualGordura >= 15 && percentualGordura < 17) {
                    System.out.println("Classificação: Elevado");
                } else {
                    System.out.println("Classificação: Muito elevado");
                }
            } else if (idade >= 50 && idade <= 59) {
                if (percentualGordura < 11) {
                    System.out.println("Classificação: Atleta");
                } else if (percentualGordura >= 11 && percentualGordura < 13) {
                    System.out.println("Classificação: Bom");
                } else if (percentualGordura >= 13 && percentualGordura < 15) {
                    System.out.println("Classificação: Normal");
                } else if (percentualGordura >= 15 && percentualGordura < 17) {
                    System.out.println("Classificação: Elevado");
                } else {
                    System.out.println("Classificação: Muito elevado");
                }
            }
        } else if (sexo.equals("mulher")) {
            if (idade >= 20 && idade <= 29) {
                if (percentualGordura < 11) {
                    System.out.println("Classificação: Atleta");
                } else if (percentualGordura >= 11 && percentualGordura < 13) {
                    System.out.println("Classificação: Bom");
                } else if (percentualGordura >= 13 && percentualGordura < 15) {
                    System.out.println("Classificação: Normal");
                } else if (percentualGordura >= 15 && percentualGordura < 17) {
                    System.out.println("Classificação: Elevado");
                } else {
                    System.out.println("Classificação: Muito elevado");
                }
            } else if (idade >= 30 && idade <= 39) {
                if (percentualGordura < 11) {
                    System.out.println("Classificação: Atleta");
                } else if (percentualGordura >= 11 && percentualGordura < 13) {
                    System.out.println("Classificação: Bom");
                } else if (percentualGordura >= 13 && percentualGordura < 15) {
                    System.out.println("Classificação: Normal");
                } else if (percentualGordura >= 15 && percentualGordura < 17) {
                    System.out.println("Classificação: Elevado");
                } else {
                    System.out.println("Classificação: Muito elevado");
                }
            } else if (idade >= 40 && idade <= 49) {
                if (percentualGordura < 11) {
                    System.out.println("Classificação: Atleta");
                } else if (percentualGordura >= 11 && percentualGordura < 13) {
                    System.out.println("Classificação: Bom");
                } else if (percentualGordura >= 13 && percentualGordura < 15) {
                    System.out.println("Classificação: Normal");
                } else if (percentualGordura >= 15 && percentualGordura < 17) {
                    System.out.println("Classificação: Elevado");
                } else {
                    System.out.println("Classificação: Muito elevado");
                }
            } else if (idade >= 50 && idade <= 59) {
                if (percentualGordura < 11) {
                    System.out.println("Classificação: Atleta");
                } else if (percentualGordura >= 11 && percentualGordura < 13) {
                    System.out.println("Classificação: Bom");
                } else if (percentualGordura >= 13 && percentualGordura < 15) {
                    System.out.println("Classificação: Normal");
                } else if (percentualGordura >= 15 && percentualGordura < 17) {
                    System.out.println("Classificação: Elevado");
                } else {
                    System.out.println("Classificação: Muito elevado");
                }
            }
        }
    }

    @Override
    public String toString() {
        return "\nId da Avaliação = " + id
                + "\nPescoco = " + pescoco
                + "\nQuadril=" + quadril
                + "\nCintura=" + cintura
                + "\nAltura=" + altura
                + "\nPeso=" + peso
                + "\nFator Atividade = " + fatorAtividade
                + "\nData de Modificação = " + dataModificacao
                + "\nData de Criação = " + dataCriacao;
    }

}
