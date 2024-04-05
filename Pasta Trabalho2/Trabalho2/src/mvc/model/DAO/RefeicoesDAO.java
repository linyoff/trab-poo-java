/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mvc.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import mvc.control.ConnectionFactory;
import mvc.model.Pessoa;
import mvc.model.Refeicoes;
import mvc.model.RegistroDieta;

/**
 *
 * @author josea
 */
public class RefeicoesDAO {

    PessoaDAO pessoaDAO;
    RegistroDietaDAO dietaDAO;

    public RefeicoesDAO(PessoaDAO pessoaDAO, RegistroDietaDAO dietaDAO) {

        this.pessoaDAO = pessoaDAO;
        this.dietaDAO = dietaDAO;

    }

    public Refeicoes adiciona(Refeicoes refeicao) {

        String sql = "INSERT INTO refeicoes "
                + "(registro_dieta_id, carboidrato, proteina, gordura, calorias, nome_refeicao, data_criacao, data_modificacao) "
                + "VALUES (?,?,?,?,?,?,?,?)";

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            // seta os valores
            stmt.setLong(1, refeicao.getDieta().getId());
            stmt.setDouble(2, refeicao.getCarboidrato());
            stmt.setDouble(3, refeicao.getProteina());
            stmt.setDouble(4, refeicao.getGordura());
            stmt.setDouble(5, refeicao.getCalorias());
            stmt.setString(6, refeicao.getNomeRefeicao());
            java.sql.Date dataSqlCriacao = java.sql.Date.valueOf(refeicao.getDataCriacao());
            stmt.setDate(7, dataSqlCriacao);
            java.sql.Date dataSqlModificacao = java.sql.Date.valueOf(refeicao.getDataModificacao());
            stmt.setDate(8, dataSqlModificacao);

            stmt.execute();

            System.out.println("Refeição inserida com sucesso.");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar refeição", e);
        }

        return refeicao;
    }

    public Refeicoes buscaRefeicaoPorID(long id) {
        List<Refeicoes> refeicoes = lista();

        for (Refeicoes rf : refeicoes) {
            if (rf != null && rf.getId() == id) {
                return rf;
            }
        }
        return null;
    }

    public List<Refeicoes> lista() {
        String sql = "SELECT * FROM refeicoes";
        List<Refeicoes> refeicoesList = new ArrayList<>();

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                long id = rs.getLong("id");
                long dietaId = rs.getLong("registro_dieta_id");
                String nomeRefeicao = rs.getString("nome_refeicao");
                double carboidrato = rs.getDouble("carboidrato");
                double proteina = rs.getDouble("proteina");
                double gordura = rs.getDouble("gordura");
                LocalDate dataCriacao = rs.getDate("data_criacao").toLocalDate();
                LocalDate dataModificacao = rs.getDate("data_modificacao").toLocalDate();

                RegistroDieta dieta = dietaDAO.buscarRegistroDietaPorId(dietaId);

                Refeicoes refeicao = new Refeicoes(id, dieta, nomeRefeicao, carboidrato, proteina, gordura, dataCriacao, dataModificacao);

                refeicoesList.add(refeicao);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar refeições", e);
        }

        return refeicoesList;
    }

    public Refeicoes altera(long id, double novoCarboidrato, double novaProteina, double novaGordura, String novoNomeRefeicao) {
        String sql = "UPDATE refeicoes SET carboidrato = ?, proteina = ?, gordura = ?, nome_refeicao = ? WHERE id = ?";

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setDouble(1, novoCarboidrato);
            stmt.setDouble(2, novaProteina);
            stmt.setDouble(3, novaGordura);
            stmt.setString(4, novoNomeRefeicao);
            stmt.setLong(5, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Refeição alterada com sucesso.");
            } else {
                System.out.println("Nenhuma refeição encontrada com o ID especificado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar refeição", e);
        }

        return null;
    }

    public Refeicoes exclui(long id) {
        String sql = "DELETE FROM refeicoes WHERE id = ?";

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Refeição excluída com sucesso.");
            } else {
                System.out.println("Nenhuma Refeição encontrada com o ID especificado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public void exibirRefeicoes() {
        List<Refeicoes> refeicoes = lista(); 

        if (refeicoes.isEmpty()) {
            System.out.println("Não há refeições cadastradas");
        } else {
            for (Refeicoes r : refeicoes) {
                if (r != null) {
                    System.out.println(r.toString());
                }
            }
        }
    }

    public double caloriasTotais() {
        List<Refeicoes> refeicoes = lista();

        double calorias = 0;

        for (Refeicoes refeicao : refeicoes) {
            if (refeicao != null) {
                calorias += refeicao.getCalorias();
            }
        }

        return calorias;
    }

}
