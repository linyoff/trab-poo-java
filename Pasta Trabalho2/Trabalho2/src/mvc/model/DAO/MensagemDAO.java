/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mvc.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import mvc.control.ConnectionFactory;
import mvc.model.Mensagem;
import mvc.model.Pessoa;
import java.sql.ResultSet;

/**
 *
 * @author Aliny
 */
public class MensagemDAO {

    PessoaDAO pessoaDAO;
    private SeguirDAO seguirDAO;

    public MensagemDAO(PessoaDAO pessoaDAO, SeguirDAO seguirDAO) {

        this.seguirDAO = seguirDAO;
        this.pessoaDAO = pessoaDAO;
    }

    public Mensagem adiciona(Mensagem mensagem) {
        try {
            if (seguirDAO.estaSeguindo(mensagem.getOrigem(), mensagem.getDestino())!=null) {
                String sql = "INSERT INTO mensagem "
                        + "(id, origem_id, destino_id, mensagem_texto, data_criacao, data_modificacao) "
                        + "VALUES (?, ?, ?, ?, ?, ?)";

                try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

                    // Configura os valores
                    stmt.setLong(1, mensagem.getId());
                    stmt.setLong(2, mensagem.getOrigem().getId());
                    stmt.setLong(3, mensagem.getDestino().getId());
                    stmt.setString(4, mensagem.getMensagem());
                    java.sql.Date dataSql1 = java.sql.Date.valueOf(mensagem.getDataCriacao());
                    stmt.setDate(5, dataSql1);
                    java.sql.Date dataSql2 = java.sql.Date.valueOf(mensagem.getDataModificacao());
                    stmt.setDate(6, dataSql2);

                    stmt.execute();

                    System.out.println("Mensagem enviada com sucesso!");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("Você só pode enviar mensagens para usuários que você segue.");
            }
        } catch (RuntimeException e) {
            System.err.println("Erro ao verificar se o usuário segue outro: " + e.getMessage());
            throw new RuntimeException("Erro ao enviar mensagem", e);
        }

        return mensagem;
    }

    /*public Mensagem adiciona(Mensagem m) {

        String sql = "insert into mensagem "
                + "(id, origem_id, destino_id, mensagem_texto, data_criacao, data_modificacao)" + " values (?,?,?,?,?,?)";

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            // seta os valores
            stmt.setLong(1, m.getId());
            stmt.setLong(2, m.getOrigem().getId());
            stmt.setLong(3, m.getDestino().getId());
            stmt.setString(4, m.getMensagem());
            java.sql.Date dataSql1 = java.sql.Date.valueOf(m.getDataCriacao());
            stmt.setDate(5, dataSql1);
            java.sql.Date dataSql2 = java.sql.Date.valueOf(m.getDataModificacao());
            stmt.setDate(6, dataSql2);

            stmt.execute();

            System.out.println("Mensagem enviada com sucesso!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return m;
    }*/
    public void exclui(long id, Pessoa origem) {
        try {
            Mensagem m = buscarMensagemPorId(id);

            if (m != null && m.getOrigem().getId() == origem.getId()) {
                String sql = "DELETE FROM mensagem WHERE id = ?";

                try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

                    stmt.setLong(1, id);
                    int linhasAfetadas = stmt.executeUpdate();

                    if (linhasAfetadas > 0) {
                        System.out.println("Mensagem excluída com sucesso!");
                    } else {
                        System.out.println("Nenhuma mensagem encontrada com o ID fornecido.");
                    }

                } catch (SQLException e) {
                    System.err.println("Erro ao excluir mensagem: " + e.getMessage());
                    throw new RuntimeException("Erro ao excluir mensagem", e);
                }
            } else {
                System.out.println("Você não possui uma mensagem com esse ID.");
            }
        } catch (RuntimeException e) {
            System.err.println("Erro ao buscar mensagem por ID: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar mensagem por ID", e);
        }
    }

    public List<Mensagem> lista() {
        String sql = "SELECT * FROM mensagem";
        List<Mensagem> mensagens = new ArrayList<>();

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = (ResultSet) stmt.executeQuery()) {

            while (rs.next()) {
                Long id = rs.getLong("id");
                Long idOrigem = rs.getLong("origem_id");
                Long idDestino = rs.getLong("destino_id");
                String mensagemTexto = rs.getString("mensagem_texto");
                LocalDate dataCriacao = rs.getDate("data_criacao").toLocalDate();
                LocalDate dataModificacao = rs.getDate("data_modificacao").toLocalDate();

                Pessoa origem = pessoaDAO.buscarPessoaPorId(idOrigem);
                Pessoa destino = pessoaDAO.buscarPessoaPorId(idDestino);

                Mensagem mensagem = new Mensagem(id, origem, destino, mensagemTexto, dataCriacao, dataModificacao);
                mensagens.add(mensagem);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return mensagens;
    }

    public Mensagem buscarMensagemPorId(long id) {
        List<Mensagem> mensagens = lista();

        for (Mensagem mensagem : mensagens) {
            if (mensagem != null && mensagem.getId() == id) {
                return mensagem;
            }
        }
        return null;
    }

    public void exibirMensagens() {
        try {
            List<Mensagem> mensagens = lista();

            if (!mensagens.isEmpty()) {
                for (Mensagem msg : mensagens) {
                    System.out.println("\nMensagem de " + msg.getOrigem().getNome());
                    System.out.println(msg.getMensagem());
                    System.out.println("Data: " + msg.getDataModificacao());
                }
            } else {
                System.out.println("Não há mensagens enviadas");
            }
        } catch (RuntimeException e) {
            System.out.println("Erro ao exibir mensagens: " + e.getMessage());
            // e.printStackTrace();
        }
    }

    public void exibirMensagensRecebidas(Pessoa p) {
        try {
            List<Mensagem> mensagens = lista();

            boolean temMensagem = false;

            for (Mensagem msg : mensagens) {
                if (p.getId() == msg.getDestino().getId()) {
                    System.out.println("\nMensagem de " + msg.getOrigem().getNome());
                    System.out.println(msg.getMensagem());
                    System.out.println("Data: " + msg.getDataModificacao());
                    temMensagem = true;
                }
            }

            if (!temMensagem) {
                System.out.println("Não há mensagens recebidas");
            }
        } catch (RuntimeException e) {
            System.out.println("Erro ao exibir mensagens recebidas: " + e.getMessage());
        }
    }

    public void exibirMensagensEnviadas(Pessoa p) {
        try {
            List<Mensagem> mensagens = lista();

            boolean temMensagem = false;

            for (Mensagem msg : mensagens) {
                if (msg.getOrigem().getId() == p.getId()) {
                    System.out.println("\nMensagem de id = " + msg.getId() + " para " + msg.getDestino().getNome());
                    System.out.println(msg.getMensagem());
                    System.out.println("Data: " + msg.getDataModificacao());
                    temMensagem = true;
                }
            }

            if (!temMensagem) {
                System.out.println("Não há mensagens enviadas");
            }
        } catch (RuntimeException e) {
            System.out.println("Erro ao exibir mensagens enviadas: " + e.getMessage());
        }
    }

}
