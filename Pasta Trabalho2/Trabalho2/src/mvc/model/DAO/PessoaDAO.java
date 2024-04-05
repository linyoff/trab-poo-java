/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mvc.model.DAO;

import java.time.LocalDate;
import mvc.model.Pessoa;
import mvc.control.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Aliny
 */
public class PessoaDAO {


    public Pessoa adiciona(Pessoa p) {

        if (existePessoa(p) != -1) {
            System.out.println("Já existe uma pessoa com esses dados. Não será adicionada.");
            return null;
        }

        String sql = "insert into pessoa "
                + "(id, idade, nome, sexo, dataNascimento, login, senha, data_criacao, data_modificacao)" + " values (?,?,?,?,?,?,?,?,?)";

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            // seta os valores
            stmt.setLong(1, p.getId());
            stmt.setInt(2, p.getIdade());
            stmt.setString(3, p.getNome());
            stmt.setString(4, p.getSexo());
            java.sql.Date dataSql = java.sql.Date.valueOf(p.getNascimento());
            stmt.setDate(5, dataSql);
            stmt.setString(6, p.getLogin());
            stmt.setString(7, p.getSenha());
            java.sql.Date dataSql2 = java.sql.Date.valueOf(p.getDataCriacao());
            stmt.setDate(8, dataSql2);
            java.sql.Date dataSql3 = java.sql.Date.valueOf(p.getDataModificacao());
            stmt.setDate(9, dataSql3);

            stmt.execute();

            System.out.println("Elemento inserido com sucesso.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return p;
    }

    public List<Pessoa> lista(Pessoa elemento) {
        String sql = "SELECT * FROM pessoa";
        List<Pessoa> pessoas = new ArrayList<>();

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Long id = rs.getLong("id");
                String nome = rs.getString("nome");
                String sexo = rs.getString("sexo");
                LocalDate dataNascimento = rs.getDate("dataNascimento").toLocalDate();
                String login = rs.getString("login");
                String senha = rs.getString("senha");
                LocalDate dataCriacao = rs.getDate("data_criacao").toLocalDate();
                LocalDate dataModificacao = rs.getDate("data_modificacao").toLocalDate();

                Pessoa pessoa = new Pessoa(id, nome, sexo, dataNascimento, login, senha, dataCriacao, dataModificacao);
                pessoas.add(pessoa);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pessoas;
    }

    public Pessoa buscarAcesso(String login, String senha) {
        List<Pessoa> pessoas = lista(null);

        for (Pessoa pessoa : pessoas) {
            if (pessoa.getLogin().equals(login) && pessoa.getSenha().equals(senha)) {
                return pessoa;
            }
        }
        return null;
    }

    public Pessoa buscarPessoaPorId(long id) {
        List<Pessoa> pessoas = lista(null);

        for (Pessoa pessoa : pessoas) {
            if (pessoa != null && pessoa.getId() == id) {
                return pessoa;
            }
        }
        return null;
    }
    
    public Pessoa buscarPessoaPorNome(String nome) {
        List<Pessoa> pessoas = lista(null);

        for (Pessoa pessoa : pessoas) {
            if (pessoa != null && pessoa.getNome().equalsIgnoreCase(nome)) {
                System.out.println("Pessoa encontrada");
                return pessoa;
            }
        }
        System.out.println("Pessoa não encontrada");
        return null;
    }
    
    public boolean verificarLoginExistente(String novoLogin) {
        
        List<Pessoa> pessoas = lista(null);

        for (Pessoa pessoa : pessoas) {
            if(pessoa!=null && pessoa.getLogin().equals(novoLogin)){
                return true;
            }
        }
        
        return false;
    }

    public long existePessoa(Pessoa p) {
        String sql = "SELECT id FROM pessoa WHERE nome = ? AND idade = ? AND sexo = ? AND dataNascimento = ? AND login = ? AND senha = ?";

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            // seta os valores para a verificação
            stmt.setString(1, p.getNome());
            stmt.setInt(2, p.getIdade());
            stmt.setString(3, p.getSexo());
            java.sql.Date dataSql = java.sql.Date.valueOf(p.getNascimento());
            stmt.setDate(4, dataSql);
            stmt.setString(5, p.getLogin());
            stmt.setString(6, p.getSenha());

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

    public void imprimeLista(List<Pessoa> pessoas) {
        for (Pessoa p : pessoas) {
            System.out.println(p.toString());
        }
    }

    public Pessoa altera(long id, String novoNome, String novoSexo, LocalDate novaDataNascimento, String novoLogin, String novaSenha) {
        String sql = "UPDATE pessoa SET nome = ?, sexo = ?, dataNascimento = ?, idade = ?, login = ?, senha = ? WHERE id = ?";

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, novoNome);
            stmt.setString(2, novoSexo);
            java.sql.Date dataSql = java.sql.Date.valueOf(novaDataNascimento);
            stmt.setDate(3, dataSql);
            int novaIdade = calcularIdade(novaDataNascimento);
            stmt.setInt(4, novaIdade);
            stmt.setString(5, novoLogin);
            stmt.setString(6, novaSenha);
            stmt.setLong(7, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Elemento alterado com sucesso.");
            } else {
                System.out.println("Nenhum elemento encontrado com o ID especificado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    private int calcularIdade(LocalDate dataNascimento) {
        LocalDate dataAtual = LocalDate.now();
        Period periodo = Period.between(dataNascimento, dataAtual);
        return periodo.getYears();
    }

    public Pessoa exclui(long id) {
        String sql = "DELETE FROM pessoa WHERE id = ?";

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Elemento excluído com sucesso.");
            } else {
                System.out.println("Nenhum elemento encontrado com o ID especificado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

}
