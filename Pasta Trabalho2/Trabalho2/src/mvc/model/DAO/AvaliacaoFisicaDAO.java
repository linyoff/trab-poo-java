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
import mvc.model.AvaliacaoFisica;
import mvc.model.Pessoa;

/**
 *
 * @author josea
 */
public class AvaliacaoFisicaDAO {

    PessoaDAO pessoaDAO;

    public AvaliacaoFisicaDAO(PessoaDAO pessoaDAO) {
        this.pessoaDAO = pessoaDAO;
    }

    public AvaliacaoFisica adiciona(AvaliacaoFisica avaliacaoFisica) {
        
        try {

            String sql = "INSERT INTO avaliacao_fisica "
                    + "(id_pessoa, pescoco, quadril, cintura, altura, peso, fator_atividade, data_criacao, data_modificacao)"
                    + " VALUES (?,?,?,?,?,?,?,?,?)";

            try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

                // Configura os parâmetros da instrução SQL
                stmt.setLong(1, avaliacaoFisica.getP().getId());
                stmt.setDouble(2, avaliacaoFisica.getPescoco());
                stmt.setDouble(3, avaliacaoFisica.getQuadril());
                stmt.setDouble(4, avaliacaoFisica.getCintura());
                stmt.setDouble(5, avaliacaoFisica.getAltura());
                stmt.setDouble(6, avaliacaoFisica.getPeso());
                stmt.setDouble(7, avaliacaoFisica.getFatorAtividade());
                stmt.setDate(8, java.sql.Date.valueOf(avaliacaoFisica.getDataCriacao()));
                stmt.setDate(9, java.sql.Date.valueOf(avaliacaoFisica.getDataModificacao()));

                stmt.executeUpdate();
                System.out.println("Avaliação física adicionada com sucesso.");

            } catch (SQLException e) {

                System.err.println("Erro SQL ao adicionar avaliação física: " + e.getMessage());
                throw new RuntimeException(e);
            }
        } catch (RuntimeException e) {

            System.err.println("Erro ao adicionar avaliação física: " + e.getMessage());
            throw e;
        }

        return avaliacaoFisica;
    }

    public void exclui(Pessoa p) {
        try {

            AvaliacaoFisica avaliacaoFisica = buscarAvaliacaoFisica(p);

            if (avaliacaoFisica == null) {
                System.out.println("A pessoa não possui uma avaliação física");
                return;
            }

            String sql = "DELETE FROM avaliacao_fisica WHERE id = ? AND id_pessoa = ?";

            try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

                stmt.setLong(1, avaliacaoFisica.getId());
                stmt.setLong(2, avaliacaoFisica.getP().getId());

                int linhasAfetadas = stmt.executeUpdate();

                if (linhasAfetadas > 0) {
                    System.out.println("Avaliação física excluída com sucesso.");
                } else {
                    System.out.println("Nenhuma avaliação física encontrada com o ID especificado para a pessoa especificada.");
                }

            } catch (SQLException e) {

                System.err.println("Erro SQL ao excluir avaliação física: " + e.getMessage());
                throw new RuntimeException(e);
            }

        } catch (RuntimeException e) {

            System.err.println("Erro ao excluir avaliação física: " + e.getMessage());
            throw e;
        }
    }

    public List<AvaliacaoFisica> lista() {
        List<AvaliacaoFisica> avaliacoesFisicas = new ArrayList<>();

        // Query SQL para listar avaliações físicas
        String sql = "SELECT * FROM avaliacao_fisica";

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                long id = rs.getLong("id");
                long pessoaId = rs.getLong("id_pessoa");
                double pescoco = rs.getDouble("pescoco");
                double quadril = rs.getDouble("quadril");
                double cintura = rs.getDouble("cintura");
                double altura = rs.getDouble("altura");
                double peso = rs.getDouble("peso");
                double fatorAtividade = rs.getDouble("fator_atividade");
                LocalDate dataCriacao = rs.getDate("data_criacao").toLocalDate();
                LocalDate dataModificacao = rs.getDate("data_modificacao").toLocalDate();

                // Lógica para obter a Pessoa associada à avaliação física
                Pessoa pessoa = pessoaDAO.buscarPessoaPorId(pessoaId);

                AvaliacaoFisica avaliacaoFisica = new AvaliacaoFisica(id, pessoa, pescoco, quadril, cintura, altura, peso, fatorAtividade, dataCriacao, dataModificacao);
                avaliacoesFisicas.add(avaliacaoFisica);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar avaliações físicas: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return avaliacoesFisicas;
    }

    public AvaliacaoFisica buscarAvaliacaoFisica(Pessoa p) {
        List<AvaliacaoFisica> avaliacoes = lista();

        for (AvaliacaoFisica avaliacao : avaliacoes) {
            if (avaliacao != null && avaliacao.getP().getId() == p.getId()) {
                return avaliacao;
            }
        }
        return null;
    }

    public AvaliacaoFisica altera(Pessoa p, double novoPescoco, double novoQuadril, double novaCintura, double novaAltura, double novoPeso, double novoFatorAtividade) {

        AvaliacaoFisica avaliacaoFisica = buscarAvaliacaoFisica(p);

        if (avaliacaoFisica == null) {
            System.out.println("A pessoa não possui uma avaliação física.");
            return null;
        }

        String sql = "UPDATE avaliacao_fisica SET pescoco = ?, quadril = ?, cintura = ?, altura = ?, peso = ?, fator_atividade = ?, data_modificacao = ? WHERE id = ?";

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setDouble(1, novoPescoco);
            stmt.setDouble(2, novoQuadril);
            stmt.setDouble(3, novaCintura);
            stmt.setDouble(4, novaAltura);
            stmt.setDouble(5, novoPeso);
            stmt.setDouble(6, novoFatorAtividade);
            java.sql.Date dataSql = java.sql.Date.valueOf(LocalDate.now());
                    stmt.setDate(7, dataSql);
            stmt.setLong(8, avaliacaoFisica.getId());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Avaliação física alterada com sucesso.");
            } else {
                System.out.println("Nenhuma avaliação física encontrada com o ID especificado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar avaliação física", e);
        }

        return null;
    }

    
    
}
