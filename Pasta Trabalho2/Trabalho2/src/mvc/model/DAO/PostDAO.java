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
import mvc.model.Post;
import mvc.model.Seguir;

/**
 *
 * @author Aliny
 */
public class PostDAO {

    private PessoaDAO pessoaDAO;
    private SeguirDAO seguirDAO;

    public PostDAO(PessoaDAO pessoaDAO, SeguirDAO seguirDAO) {

        this.pessoaDAO = pessoaDAO;
        this.seguirDAO = seguirDAO;

    }

    public Post adiciona(Post p) {

        String sql = "insert into post "
                + "(id, autor_id, conteudo_da_mensagem, data_criacao, data_modificacao)" + " values (?,?,?,?,?)";

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            // seta os valores
            stmt.setLong(1, p.getId());
            stmt.setLong(2, p.getAutor().getId());
            stmt.setString(3, p.getConteudoDaMensagem());
            java.sql.Date dataSql1 = java.sql.Date.valueOf(p.getDataCriacao());
            stmt.setDate(4, dataSql1);
            java.sql.Date dataSql2 = java.sql.Date.valueOf(p.getDataModificacao());
            stmt.setDate(5, dataSql2);

            stmt.execute();

            System.out.println("Post feito com sucesso!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return p;
    }

    public void altera(Post novoPost, Pessoa autor) {
        try {
            Post postExistente = buscarPostPorId(novoPost.getId());

            if (postExistente != null && postExistente.getAutor().getId() == autor.getId()) {
                String sql = "UPDATE post SET conteudo_da_mensagem = ?, data_modificacao = ? WHERE id = ?";

                try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

                    stmt.setString(1, novoPost.getConteudoDaMensagem());
                    java.sql.Date dataSql = java.sql.Date.valueOf(novoPost.getDataModificacao());
                    stmt.setDate(2, dataSql);
                    stmt.setLong(3, novoPost.getId());

                    int linhasAfetadas = stmt.executeUpdate();

                    if (linhasAfetadas > 0) {
                        System.out.println("Post alterado com sucesso.");
                    } else {
                        System.out.println("Nenhum post encontrado com o ID especificado ou não autorizado para alteração.");
                    }

                } catch (SQLException e) {
                    System.err.println("Erro ao alterar post: " + e.getMessage());
                    throw new RuntimeException("Erro ao alterar post", e);
                }
            } else {
                System.out.println("Você não possui um post com esse id");
            }
        } catch (RuntimeException e) {
            System.err.println("Erro ao buscar post por ID: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar post por ID", e);
        }
    }

    public List<Post> lista() {
        String sql = "SELECT * FROM post";
        List<Post> posts = new ArrayList<>();

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Long id = rs.getLong("id");
                String conteudoMensagem = rs.getString("conteudo_da_mensagem");
                Long idAutor = rs.getLong("autor_id");
                Pessoa autor = pessoaDAO.buscarPessoaPorId(idAutor);
                LocalDate dataCriacao = rs.getDate("data_criacao").toLocalDate();
                LocalDate dataModificacao = rs.getDate("data_modificacao").toLocalDate();

                Post post = new Post(id, autor, conteudoMensagem, dataCriacao, dataModificacao);
                posts.add(post);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return posts;
    }

    public Post exclui(long id, Pessoa autor) {
        try {
            Post post = buscarPostPorId(id);

            if (post != null && post.getAutor().getId() == autor.getId()) {
                String sql = "DELETE FROM post WHERE id = ?";

                try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

                    stmt.setLong(1, id);

                    int linhasAfetadas = stmt.executeUpdate();

                    if (linhasAfetadas > 0) {
                        System.out.println("Post excluído com sucesso.");
                    } else {
                        System.out.println("Nenhum post encontrado com o ID especificado.");
                    }

                } catch (SQLException e) {
                    System.err.println("Erro ao excluir post: " + e.getMessage());
                    throw new RuntimeException("Erro ao excluir post", e);
                }
            } else {
                System.out.println("Você não possui um post com esse ID.");
            }

            return null;
        } catch (RuntimeException e) {
            System.err.println("Erro ao buscar post por ID: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar post por ID", e);
        }
    }

    public void imprimeLista(List<Post> posts) {
        for (Post p : posts) {
            System.out.println(p.toString());
        }
    }

    public Post buscarPostPorId(long id) {
        List<Post> posts = lista();

        for (Post post : posts) {
            if (post != null && post.getId() == id) {
                return post;
            }
        }
        return null;
    }

    public void exibirMeusPosts(Pessoa usuarioLogado) {
        List<Post> posts = lista();

        if (posts.isEmpty()) {
            System.out.println("Não há posts para exibir.");
        } else {
            boolean encontrouPosts = false;

            for (Post post : posts) {
                if (post.getAutor() != null && post.getAutor().getId() == usuarioLogado.getId()) {
                    System.out.println("Id: " + post.getId() + " - " + post);
                    encontrouPosts = true;
                }
            }

            if (!encontrouPosts) {
                System.out.println("Não há posts associados a este usuário.");
            }
        }
    }

    public void timeline(Pessoa usuarioLogado) {
        System.out.println("**** Timeline ****");

        List<Seguir> listaSeguir = seguirDAO.lista(null);
        List<Post> posts = lista();

        for (Seguir seguidor : listaSeguir) {
            if (seguidor.getOrigem().getId() == usuarioLogado.getId()) {
                for (Post post : posts) {

                    if (post != null && post.getAutor() != null && post.getAutor().getId() == seguidor.getSeguindo().getId()) {
                        System.out.println("\nPostagem feita por " + post.getAutor().getNome());
                        System.out.println(post.getConteudoDaMensagem());
                    }
                }
            }
        }
    }

}
