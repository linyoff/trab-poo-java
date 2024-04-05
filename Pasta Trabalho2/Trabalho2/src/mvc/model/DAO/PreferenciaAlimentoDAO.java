/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mvc.model.DAO;

import java.time.LocalDate;
import java.util.List;
import mvc.model.AlimentoReceita;
import mvc.model.Pessoa;
import mvc.model.PreferenciaAlimento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import mvc.control.ConnectionFactory;

/**
 *
 * @author Aliny
 */
public class PreferenciaAlimentoDAO {

    PessoaDAO pessoaDAO;
    AlimentoReceitaDAO alimentoDAO;

    public PreferenciaAlimentoDAO(PessoaDAO pessoaDAO, AlimentoReceitaDAO alimentoDAO) {

        this.pessoaDAO = pessoaDAO;
        this.alimentoDAO = alimentoDAO;

    }

    public PreferenciaAlimento adiciona(Pessoa pessoa, long idAlimento) {
        try {
            AlimentoReceita alimento = this.alimentoDAO.buscarAlimentoPorId(idAlimento);

            if (alimento != null && alimento.getPessoa().getId() == pessoa.getId()) {
                PreferenciaAlimento preferenciaAlimento = new PreferenciaAlimento(
                        0, pessoa, alimento, LocalDate.now(), LocalDate.now()
                );

                String sql = "INSERT INTO preferencia_alimento "
                        + "(id, pessoa_id, alimento_id, data_criacao, data_modificacao) "
                        + "VALUES (?,?,?,?,?)";

                try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

                    // Configura os valores
                    stmt.setLong(1, preferenciaAlimento.getId());
                    stmt.setLong(2, preferenciaAlimento.getPessoa().getId());
                    stmt.setLong(3, preferenciaAlimento.getAlimento().getId());
                    java.sql.Date dataSql1 = java.sql.Date.valueOf(preferenciaAlimento.getDataCriacao());
                    stmt.setDate(4, dataSql1);
                    java.sql.Date dataSql2 = java.sql.Date.valueOf(preferenciaAlimento.getDataModificacao());
                    stmt.setDate(5, dataSql2);

                    stmt.execute();
                    System.out.println("Alimento adicionado como preferência!");
                    return preferenciaAlimento;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("O alimento não pertence à pessoa especificada.");
            }
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            throw e;
        }
        return null;
    }

    public void exclui(Pessoa pessoa, long id) {

        try {
            PreferenciaAlimento alimento = buscarPreferenciaPorId(id);

            if (alimento != null && alimento.getPessoa().getId() == pessoa.getId()) {

                String sql = "DELETE FROM preferencia_alimento WHERE id = ? AND pessoa_id = ?";

                try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

                    stmt.setLong(1, alimento.getId());
                    stmt.setLong(2, pessoa.getId());

                    int linhasAfetadas = stmt.executeUpdate();

                    if (linhasAfetadas > 0) {
                        System.out.println("Preferência de alimento excluída com sucesso!");
                    } else {
                        System.out.println("Nenhuma preferência de alimento encontrada com o ID especificado para a pessoa especificada.");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("O alimento não pertence à pessoa especificada.");
            }
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            throw e;
        }

    }

    public List<PreferenciaAlimento> lista() {
        String sql = "SELECT * FROM preferencia_alimento";
        List<PreferenciaAlimento> preferencias = new ArrayList<>();

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Long id = rs.getLong("id");
                Long idPessoa = rs.getLong("pessoa_id");
                Long idAlimento = rs.getLong("alimento_id");
                LocalDate dataCriacao = rs.getDate("data_criacao").toLocalDate();
                LocalDate dataModificacao = rs.getDate("data_modificacao").toLocalDate();

                Pessoa pessoa = pessoaDAO.buscarPessoaPorId(idPessoa);
                AlimentoReceita alimento = alimentoDAO.buscarAlimentoPorId(idAlimento);

                PreferenciaAlimento preferencia = new PreferenciaAlimento(id, pessoa, alimento, dataCriacao, dataModificacao);
                preferencias.add(preferencia);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return preferencias;
    }

    public PreferenciaAlimento buscarPreferenciaPorId(long id) {
        List<PreferenciaAlimento> preferencias = lista();

        for (PreferenciaAlimento p : preferencias) {
            if (p != null && p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public void exibirMinhasPreferencias(Pessoa usuarioLogado) {
        List<PreferenciaAlimento> preferencias = lista();

        if (preferencias.isEmpty()) {
            System.out.println("Não há preferências para exibir.");
        } else {
            boolean encontrouPreferencias = false;

            for (PreferenciaAlimento preferencia : preferencias) {
                if (preferencia.getPessoa() != null && preferencia.getPessoa().getId() == usuarioLogado.getId()) {
                    System.out.println(preferencia);
                    encontrouPreferencias = true;
                }
            }

            if (!encontrouPreferencias) {
                System.out.println("Não há preferências associadas a este usuário.");
            }
        }
    }

}
