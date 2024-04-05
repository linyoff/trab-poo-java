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
import mvc.model.TipoDieta;

/**
 *
 * @author Aliny
 */
public class TipoDietaDAO {

    public TipoDieta adiciona(TipoDieta tipoDieta) {
        String sql = "INSERT INTO tipo_dieta (id, nome, carboidrato, proteina, gordura, data_criacao, data_modificacao) "
                + "VALUES (?,?,?,?,?,?,?)";

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, tipoDieta.getId());
            stmt.setString(2, tipoDieta.getNome());
            stmt.setDouble(3, tipoDieta.getCarboidrato());
            stmt.setDouble(4, tipoDieta.getProteina());
            stmt.setDouble(5, tipoDieta.getGordura());
            stmt.setDate(6, java.sql.Date.valueOf(tipoDieta.getDataCriacao()));
            stmt.setDate(7, java.sql.Date.valueOf(tipoDieta.getDataModificacao()));

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Tipo de Dieta adicionado com sucesso.");
            } else {
                System.out.println("Falha ao adicionar Tipo de Dieta.");
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar Tipo de Dieta", e);
        }
        return tipoDieta;
    }

    public List<TipoDieta> lista() {
        String sql = "SELECT * FROM tipo_dieta";
        List<TipoDieta> tiposDieta = new ArrayList<>();

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                long id = rs.getLong("id");
                String nome = rs.getString("nome");
                double carboidrato = rs.getDouble("carboidrato");
                double proteina = rs.getDouble("proteina");
                double gordura = rs.getDouble("gordura");
                LocalDate dataCriacao = rs.getDate("data_criacao").toLocalDate();
                LocalDate dataModificacao = rs.getDate("data_modificacao").toLocalDate();

                TipoDieta tipoDieta = new TipoDieta(id, nome, carboidrato, proteina, gordura, dataCriacao, dataModificacao);
                tiposDieta.add(tipoDieta);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tiposDieta;
    }
    
    public TipoDieta buscarTipoDietaPorId(long id) {
        List<TipoDieta> tiposDieta = lista();

        for (TipoDieta tipoDieta : tiposDieta) {
            if (tipoDieta != null && tipoDieta.getId() == id) {
                return tipoDieta;
            }
        }
        return null;
    }
}
