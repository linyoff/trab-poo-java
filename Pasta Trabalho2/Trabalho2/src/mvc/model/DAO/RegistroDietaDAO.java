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
import mvc.model.RegistroDieta;
import mvc.model.TipoDieta;

/**
 *
 * @author Aliny
 */
public class RegistroDietaDAO {

    PessoaDAO pessoaDAO;
    TipoDietaDAO tipoDietaDAO;
    AvaliacaoFisicaDAO avaliacaoDAO;

    public RegistroDietaDAO(PessoaDAO pessoaDAO, AvaliacaoFisicaDAO avaliacaoDAO, TipoDietaDAO tipoDietaDAO) {

        this.pessoaDAO = pessoaDAO;
        this.tipoDietaDAO = tipoDietaDAO;
        this.avaliacaoDAO = avaliacaoDAO;
    }

    public RegistroDieta adiciona(RegistroDieta registroDieta) {
        String sql = "INSERT INTO registro_dieta (id, pessoa_id, avaliacao_fisica_id, tipo_dieta_id, objetivo, calorias, numero_refeicoes, data_criacao, data_modificacao) "
                + "VALUES (?,?,?,?,?,?,?,?,?)";

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, registroDieta.getId());
            stmt.setLong(2, registroDieta.getP().getId());
            stmt.setLong(3, registroDieta.getAvaliacaoFisica().getId());
            stmt.setLong(4, registroDieta.getTipoDieta().getId());
            stmt.setString(5, registroDieta.getObjetivo());
            stmt.setDouble(6, registroDieta.getCalorias());
            stmt.setInt(7, registroDieta.getNumeroRefeicoes());
            stmt.setDate(8, java.sql.Date.valueOf(registroDieta.getDataCriacao()));
            stmt.setDate(9, java.sql.Date.valueOf(registroDieta.getDataModificacao()));

            // Executa a instrução SQL
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Registro de Dieta adicionado com sucesso.");
            } else {
                System.out.println("Falha ao adicionar Registro de Dieta.");
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar Registro de Dieta", e);
        }
        return registroDieta;
    }

    public List<RegistroDieta> lista() {
        List<RegistroDieta> registrosDieta = new ArrayList<>();

        String sql = "SELECT * FROM registro_dieta";

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                long id = rs.getLong("id");
                long pessoaId = rs.getLong("pessoa_id");
                long avaliacaoFisicaId = rs.getLong("avaliacao_fisica_id");
                long tipoDietaId = rs.getLong("tipo_dieta_id");
                String objetivo = rs.getString("objetivo");
                double calorias = rs.getDouble("calorias");
                int numeroRefeicoes = rs.getInt("numero_refeicoes");
                LocalDate dataCriacao = rs.getDate("data_criacao").toLocalDate();
                LocalDate dataModificacao = rs.getDate("data_modificacao").toLocalDate();

                Pessoa pessoa = pessoaDAO.buscarPessoaPorId(pessoaId);
                AvaliacaoFisica avaliacaoFisica = avaliacaoDAO.buscarAvaliacaoFisica(pessoa);
                TipoDieta tipoDieta = tipoDietaDAO.buscarTipoDietaPorId(tipoDietaId);

                RegistroDieta registroDieta = new RegistroDieta(id, pessoa, avaliacaoFisica, tipoDieta, objetivo, numeroRefeicoes, dataCriacao, dataModificacao);

                registrosDieta.add(registroDieta);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar Registros de Dieta", e);
        }

        return registrosDieta;
    }

    public void exibirMeusRegistrosDieta(Pessoa usuarioLogado) {
        List<RegistroDieta> registrosDieta = lista();

        if (registrosDieta.isEmpty()) {
            System.out.println("Não há registros de dieta para exibir.");
        } else {
            boolean encontrouRegistrosDieta = false;

            for (RegistroDieta registroDieta : registrosDieta) {
                if (registroDieta.getP() != null && registroDieta.getP().getId() == usuarioLogado.getId()) {
                    System.out.println(registroDieta);
                    encontrouRegistrosDieta = true;
                }
            }

            if (!encontrouRegistrosDieta) {
                System.out.println("Não há registros de dieta associados a este usuário.");
            }
        }
    }

    public void exclui(long id, Pessoa p) {

        try {
            String sql = "DELETE FROM registro_dieta WHERE id = ?";

            try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

                stmt.setLong(1, id);

                int linhasAfetadas = stmt.executeUpdate();

                if (linhasAfetadas > 0) {
                    System.out.println("Registro de Dieta excluído com sucesso.");
                } else {
                    System.out.println("Falha ao excluir Registro de Dieta. Registro não encontrado.");
                }

            } catch (SQLException e) {
                throw new RuntimeException("Erro ao excluir Registro de Dieta", e);
            }
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            throw e;
        }
    }

    public RegistroDieta buscarRegistroDietaPorId(long id) {
        List<RegistroDieta> registros = lista();

        for (RegistroDieta rg : registros) {
            if (rg != null && rg.getId() == id) {
                return rg;
            }
        }
        return null;
    }

    public RegistroDieta altera(Pessoa pessoa, long idRegistroDieta, TipoDieta novoTipoDieta, String novoObjetivo, double novasCalorias, int novoNumeroRefeicoes) {

        RegistroDieta registroDieta = buscarRegistroDietaPorId(idRegistroDieta);
        if (registroDieta == null || registroDieta.getP() == null || registroDieta.getP().getId() != pessoa.getId()) {
            System.out.println("O registro de dieta não existe ou não pertence à pessoa especificada.");
            return null;
        }

        String sql = "UPDATE registro_dieta SET tipo_dieta_id = ?, objetivo = ?, calorias = ?, numero_refeicoes = ?, data_modificacao = ? WHERE id = ?";

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, novoTipoDieta.getId());
            stmt.setString(2, novoObjetivo);
            stmt.setDouble(3, novasCalorias);
            stmt.setInt(4, novoNumeroRefeicoes);
            java.sql.Date dataSql = java.sql.Date.valueOf(LocalDate.now());
            stmt.setDate(5, dataSql);
            stmt.setLong(6, idRegistroDieta);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Registro de dieta alterado com sucesso.");
            } else {
                System.out.println("Nenhum registro de dieta encontrado com o ID especificado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar registro de dieta", e);
        }

        return null;
    }

}
