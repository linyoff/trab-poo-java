/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mvc.model.DAO;

/**
 *
 * @author josea
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import mvc.control.ConnectionFactory;
import mvc.model.AlimentoReceita;
import mvc.model.AlimentoRefeicoes;
import mvc.model.Refeicoes;

public class AlimentosRefeicoesDAO {

    AlimentoReceitaDAO arDAO;
    RefeicoesDAO rfDAO;

    public AlimentosRefeicoesDAO(AlimentoReceitaDAO arDAO, RefeicoesDAO rfDAO) {

        this.arDAO = arDAO;
        this.rfDAO = rfDAO;

    }

    public AlimentoRefeicoes adiciona(AlimentoRefeicoes alimentoRefeicoes) {
        if (existeAlimentoRefeicoes(alimentoRefeicoes) != -1) {
            System.out.println("Já existe uma refeicao igual a essa. Não será adicionada.");
            return null;
        }
        String sql = "INSERT INTO alimento_refeicoes (id_refeicao, id_alimento_receita, porcao, proteina, gordura, carboidrato, calorias, data_criacao, data_modificacao) "
                + "VALUES (?,?,?,?,?,?,?,?,?)";

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, alimentoRefeicoes.getRefeicao().getId());
            stmt.setLong(2, alimentoRefeicoes.getAlimento().getId());
            stmt.setDouble(3, alimentoRefeicoes.getPorcao());
            stmt.setDouble(4, alimentoRefeicoes.getProteina());
            stmt.setDouble(5, alimentoRefeicoes.getGordura());
            stmt.setDouble(6, alimentoRefeicoes.getCarboidrato());
            stmt.setDouble(7, alimentoRefeicoes.getCalorias());
            stmt.setDate(8, java.sql.Date.valueOf(alimentoRefeicoes.getDataCriacao()));
            stmt.setDate(9, java.sql.Date.valueOf(alimentoRefeicoes.getDataModificacao()));

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Alimento da Refeição adicionado com sucesso.");
            } else {
                System.out.println("Falha ao adicionar Alimento da Refeição.");
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar Alimento da Refeição", e);
        }

        return alimentoRefeicoes;
    }

    public List<AlimentoRefeicoes> lista() {
        String sql = "SELECT * FROM alimento_refeicoes";
        List<AlimentoRefeicoes> alimentosRefeicoes = new ArrayList<>();

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                long id = rs.getLong("id");
                long idRefeicao = rs.getLong("id_refeicao");
                long idAlimentoReceita = rs.getLong("id_alimento_receita");
                double porcao = rs.getDouble("porcao");
                double proteina = rs.getDouble("proteina");
                double gordura = rs.getDouble("gordura");
                double carboidrato = rs.getDouble("carboidrato");
                double calorias = rs.getDouble("calorias");
                LocalDate dataCriacao = rs.getDate("data_criacao").toLocalDate();
                LocalDate dataModificacao = rs.getDate("data_modificacao").toLocalDate();

                Refeicoes refeicao = rfDAO.buscaRefeicaoPorID(idRefeicao);
                AlimentoReceita alimento = arDAO.buscarAlimentoPorId(idAlimentoReceita);

                AlimentoRefeicoes alimentoRefeicoes = new AlimentoRefeicoes(id, alimento, refeicao, porcao, proteina, gordura, carboidrato, dataCriacao, dataModificacao);
                alimentosRefeicoes.add(alimentoRefeicoes);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return alimentosRefeicoes;
    }

    public AlimentoRefeicoes buscarAlimentoRefeicoesPorId(long id) {
        List<AlimentoRefeicoes> arf = lista();

        for (AlimentoRefeicoes ar : arf) {
            if (ar != null && ar.getId() == id) {
                return ar;
            }
        }
        return null;
    }

    public long existeAlimentoRefeicoes(AlimentoRefeicoes alimentoRefeicoes) {
        String sql = "SELECT id FROM alimento_refeicoes WHERE id_refeicao = ? AND id_alimento_receita = ? AND porcao = ? AND proteina = ? AND gordura = ? AND carboidrato = ? AND calorias = ? AND data_criacao = ? AND data_modificacao = ?";

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, alimentoRefeicoes.getRefeicao().getId());
            stmt.setLong(2, alimentoRefeicoes.getAlimento().getId());
            stmt.setDouble(3, alimentoRefeicoes.getPorcao());
            stmt.setDouble(4, alimentoRefeicoes.getProteina());
            stmt.setDouble(5, alimentoRefeicoes.getGordura());
            stmt.setDouble(6, alimentoRefeicoes.getCarboidrato());
            stmt.setDouble(7, alimentoRefeicoes.getCalorias());
            stmt.setDate(8, java.sql.Date.valueOf(alimentoRefeicoes.getDataCriacao()));
            stmt.setDate(9, java.sql.Date.valueOf(alimentoRefeicoes.getDataModificacao()));

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getLong("id");
            } else {
                return -1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void imprimeLista(List<AlimentoRefeicoes> alimentosRefeicoesList) {
        for (AlimentoRefeicoes ar : alimentosRefeicoesList) {
            System.out.println(ar.toString());
        }
    }

    public AlimentoRefeicoes altera(long idAlimentoRefeicoes, long idRefeicao, long idAlimentoReceita, double novaPorcao, double novaProteina, double novaGordura, double novoCarboidrato, double novasCalorias) {
        String sql = "UPDATE alimento_refeicoes SET id_refeicao = ?, id_alimento_receita = ?, porcao = ?, proteina = ?, gordura = ?, carboidrato = ?, calorias = ?, data_modificacao = ? WHERE id = ?";

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, idRefeicao);
            stmt.setLong(2, idAlimentoReceita);
            stmt.setDouble(3, novaPorcao);
            stmt.setDouble(4, novaProteina);
            stmt.setDouble(5, novaGordura);
            stmt.setDouble(6, novoCarboidrato);
            stmt.setDouble(7, novasCalorias);
            java.sql.Date dataSql = java.sql.Date.valueOf(LocalDate.now());
            stmt.setDate(8, dataSql);
            stmt.setLong(9, idAlimentoRefeicoes);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Alimento para refeição alterado com sucesso.");
            } else {
                System.out.println("Nenhum alimento para refeição encontrado com o ID especificado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar alimento para refeição", e);
        }

        return null;
    }

    public void exibirAlimentosRefeicoes() {
        List<AlimentoRefeicoes> alimentosRefeicoesList = lista();

        if (alimentosRefeicoesList.isEmpty()) {
            System.out.println("Não há alimentos para refeições cadastrados.");
        } else {
            for (AlimentoRefeicoes ar : alimentosRefeicoesList) {
                if (ar != null) {
                    System.out.println(ar.toString());
                }
            }
        }
    }

    public AlimentoRefeicoes exclui(long id) {
        String sql = "DELETE FROM alimento_refeicoes WHERE id = ?";

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Alimento para Refeição excluído com sucesso.");
            } else {
                System.out.println("Nenhum Alimento para Refeição encontrado com o ID especificado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir Alimento para Refeição", e);
        }

        return null;
    }

}
