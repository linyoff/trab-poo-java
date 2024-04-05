/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mvc.model.DAO;

import mvc.model.AlimentoReceita;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import mvc.control.ConnectionFactory;
import mvc.model.Pessoa;

/**
 *
 * @author Aliny
 */
public class AlimentoReceitaDAO {

    PessoaDAO pessoaDAO;

    //TESTES
    public AlimentoReceitaDAO(PessoaDAO pessoaDAO) {

        this.pessoaDAO = pessoaDAO;

    }

    public AlimentoReceita adiciona(AlimentoReceita alimento) {
        String sql = "INSERT INTO alimento_receita "
                + "(id, pessoa_id, nome, carboidratos, proteinas, gorduras, calorias, porcao, data_criacao, data_modificacao) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?)";

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            // Configura os valores
            stmt.setLong(1, alimento.getId());
            stmt.setLong(2, alimento.getPessoa().getId());
            stmt.setString(3, alimento.getNome());
            stmt.setDouble(4, alimento.getCarboidratos());
            stmt.setDouble(5, alimento.getProteinas());
            stmt.setDouble(6, alimento.getGorduras());
            stmt.setDouble(7, alimento.getCalorias());
            stmt.setDouble(8, alimento.getPorcao());

            java.sql.Date dataSql1 = java.sql.Date.valueOf(alimento.getDataCriacao());
            stmt.setDate(9, dataSql1);

            java.sql.Date dataSql2 = java.sql.Date.valueOf(alimento.getDataModificacao());
            stmt.setDate(10, dataSql2);

            stmt.execute();

            System.out.println("Alimento adicionado com sucesso!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return alimento;
    }

    public AlimentoReceita exclui(long id, Pessoa pessoa) {
        try {
            AlimentoReceita alimento = buscarAlimentoPorId(id);

            if (alimento != null && alimento.getPessoa().getId() == pessoa.getId()) {
                String sql = "DELETE FROM alimento_receita WHERE id = ?";

                try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

                    stmt.setLong(1, id);

                    int linhasAfetadas = stmt.executeUpdate();

                    if (linhasAfetadas > 0) {
                        System.out.println("Alimento excluído com sucesso.");
                    } else {
                        System.out.println("Nenhum alimento encontrado com o ID especificado.");
                    }

                } catch (SQLException e) {
                    System.err.println("Erro ao excluir alimento: " + e.getMessage());
                    throw new RuntimeException("Erro ao excluir alimento", e);
                }
            } else {
                System.out.println("Você não possui um alimento com esse ID.");
            }

            return null;
        } catch (RuntimeException e) {
            System.err.println("Erro ao buscar alimento por ID: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar alimento por ID", e);
        }
    }

    public void altera(AlimentoReceita novoAlimento, Pessoa pessoa) {
        try {
            AlimentoReceita alimentoExistente = buscarAlimentoPorId(novoAlimento.getId());

            if (alimentoExistente != null && alimentoExistente.getPessoa().getId() == pessoa.getId()) {
                String sql = "UPDATE alimento_receita SET pessoa_id = ?, nome = ?, carboidratos = ?, proteinas = ?, gorduras = ?, calorias = ?, porcao = ?, data_modificacao = ? WHERE id = ?";

                try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

                    stmt.setLong(1, novoAlimento.getPessoa().getId());
                    stmt.setString(2, novoAlimento.getNome());
                    stmt.setDouble(3, novoAlimento.getCarboidratos());
                    stmt.setDouble(4, novoAlimento.getProteinas());
                    stmt.setDouble(5, novoAlimento.getGorduras());
                    stmt.setDouble(6, novoAlimento.getCalorias());
                    stmt.setDouble(7, novoAlimento.getPorcao());

                    java.sql.Date dataSql = java.sql.Date.valueOf(novoAlimento.getDataModificacao());
                    stmt.setDate(8, dataSql);

                    stmt.setLong(9, novoAlimento.getId());

                    int linhasAfetadas = stmt.executeUpdate();

                    if (linhasAfetadas > 0) {
                        System.out.println("Alimento alterado com sucesso.");
                    } else {
                        System.out.println("Nenhum alimento encontrado com o ID especificado ou não autorizado para alteração.");
                    }

                } catch (SQLException e) {
                    System.err.println("Erro ao alterar alimento: " + e.getMessage());
                    throw new RuntimeException("Erro ao alterar alimento", e);
                }
            } else {
                System.out.println("Você não possui um alimento com esse ID.");
            }
        } catch (RuntimeException e) {
            System.err.println("Erro ao buscar alimento por ID: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar alimento por ID", e);
        }
    }

    public List<AlimentoReceita> lista() {
        String sql = "SELECT * FROM alimento_receita";
        List<AlimentoReceita> alimentos = new ArrayList<>();

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                long id = rs.getLong("id");
                long pessoa_id = rs.getLong("pessoa_id");
                String nome = rs.getString("nome");
                double carboidratos = rs.getDouble("carboidratos");
                double proteinas = rs.getDouble("proteinas");
                double gorduras = rs.getDouble("gorduras");
                double porcao = rs.getDouble("porcao");
                LocalDate dataCriacao = rs.getDate("data_criacao").toLocalDate();
                LocalDate dataModificacao = rs.getDate("data_modificacao").toLocalDate();

                Pessoa pessoa = pessoaDAO.buscarPessoaPorId(pessoa_id);

                AlimentoReceita alimento = new AlimentoReceita(id, pessoa, nome, carboidratos, proteinas, gorduras, porcao, dataCriacao, dataModificacao);
                alimentos.add(alimento);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return alimentos;
    }

    public AlimentoReceita buscarAlimentoPorId(long id) {
        List<AlimentoReceita> alimentos = lista();

        for (AlimentoReceita alimento : alimentos) {
            if (alimento != null && alimento.getId() == id) {
                return alimento;
            }
        }
        return null;
    }

    public void imprimeLista() {

        List<AlimentoReceita> alimentos_rc = lista();

        for (AlimentoReceita ar : alimentos_rc) {
            System.out.println(ar);
        }
    }

    public void exibirMeusAlimentos(Pessoa usuarioLogado) {
        List<AlimentoReceita> alimentos = lista();

        if (alimentos.isEmpty()) {
            System.out.println("Não há alimentos para exibir.");
        } else {
            boolean encontrouAlimentos = false;

            for (AlimentoReceita alimento : alimentos) {
                if (alimento.getPessoa() != null && alimento.getPessoa().getId() == usuarioLogado.getId()) {
                    System.out.println(alimento);
                    encontrouAlimentos = true;
                }
            }
            if (!encontrouAlimentos) {
                System.out.println("Não há alimentos associados a este usuário.");
            }
        }
    }

}
