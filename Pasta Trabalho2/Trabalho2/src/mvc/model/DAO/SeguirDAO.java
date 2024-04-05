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
import java.util.Date;
import java.util.List;
import mvc.control.ConnectionFactory;
import mvc.model.Pessoa;
import mvc.model.Post;
import mvc.model.Seguir;

/**
 *
 * @author Aliny
 */
public class SeguirDAO {

    PessoaDAO pessoaDAO;

    public SeguirDAO(PessoaDAO pessoaDAO) {

        this.pessoaDAO = pessoaDAO;

    }

    public List<Seguir> lista(Seguir elemento) {
        String sql = "SELECT * FROM seguir";
        List<Seguir> listaSeguir = new ArrayList<>();

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Long id = rs.getLong("id");
                Long idOrigem = rs.getLong("origem_id");
                Pessoa origem = pessoaDAO.buscarPessoaPorId(idOrigem);
                Long idSeguindo = rs.getLong("seguindo_id");
                Pessoa seguindo = pessoaDAO.buscarPessoaPorId(idSeguindo);
                LocalDate dataCriacao = rs.getDate("data_criacao").toLocalDate();
                LocalDate dataModificacao = rs.getDate("data_modificacao").toLocalDate();

                Seguir seguir = new Seguir(id, origem, seguindo, dataCriacao, dataModificacao);
                listaSeguir.add(seguir);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaSeguir;
    }

    public Seguir seguirPessoa(Pessoa origem, Pessoa seguindo) {
        if (estaSeguindo(origem, seguindo) == null) {
            Seguir seguir = new Seguir(0, origem, seguindo, LocalDate.now(), LocalDate.now());

            String sql = "INSERT INTO seguir (id, origem_id, seguindo_id, data_criacao, data_modificacao) VALUES (?,?,?,?,?)";

            try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

                stmt.setLong(1, seguir.getId());
                stmt.setLong(2, seguir.getOrigem().getId());
                stmt.setLong(3, seguir.getSeguindo().getId());
                java.sql.Date dataSql1 = java.sql.Date.valueOf(seguir.getDataCriacao());
                stmt.setDate(4, dataSql1);
                java.sql.Date dataSql2 = java.sql.Date.valueOf(seguir.getDataModificacao());
                stmt.setDate(5, dataSql2);

                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Agora você está seguindo " + seguindo.getNome());

                    return seguir;
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Não foi possível realizar a operação");
        }

        return null;
    }

    public boolean deixarDeSeguir(Pessoa origem, Pessoa seguindo) {
        Seguir seguirExistente = estaSeguindo(origem, seguindo);
        
        if (seguirExistente != null) {
            String sql = "DELETE FROM seguir WHERE id = ?";

            try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

                stmt.setLong(1, seguirExistente.getId());

                
                int linhasAfetadas = stmt.executeUpdate();

                if (linhasAfetadas > 0) {
                    System.out.println("Você deixou de seguir " + seguindo.getNome());
                    return true;
                } else {
                    System.out.println("Não foi possível realizar a operação. Nenhuma linha afetada.");
                }

            } catch (SQLException e) {
                throw new RuntimeException("Erro ao deixar de seguir", e);
            }
        } else {
            System.out.println("Você não estava seguindo " + seguindo.getNome());
        }

        return false;
    }

    public Seguir estaSeguindo(Pessoa origem, Pessoa seguindo) {
        List<Seguir> listaSeguir = lista(null);

        for (Seguir seguir : listaSeguir) {
            if (seguir.getOrigem().getId()==origem.getId() && seguir.getSeguindo().getId()==seguindo.getId()) {
                System.out.println("Você segue essa pessoa");
                return seguir;
            }
        }
        return null;
    }

    public void mostrarQuemEstouSeguindo(Pessoa origem) {

        List<Seguir> listaSeguir = lista(null);

        System.out.println("\nSeguindo essas pessoas: ");

        for (Seguir seguido : listaSeguir) {
            if (seguido != null) {
                if (seguido.getOrigem().getId() == origem.getId()) {
                    System.out.println("Nome: " + seguido.getSeguindo().getNome() + "  -  Id: " + seguido.getSeguindo().getId());
                }
            }
        }
    }

    public void mostrarSeguidores(Pessoa pessoa) {

        List<Seguir> listaSeguir = lista(null);

        System.out.println("\nPessoas que te seguem: ");

        for (Seguir seguidor : listaSeguir) {
            if (seguidor != null) {
                if (seguidor.getSeguindo().getId() == pessoa.getId()) {
                    System.out.println("Nome: " + seguidor.getOrigem().getNome() + "  -  Id: " + seguidor.getOrigem().getId());
                }
            }
        }
    }

}
