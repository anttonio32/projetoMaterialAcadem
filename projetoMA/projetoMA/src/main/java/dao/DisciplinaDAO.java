package dao;

import model.Disciplina;
import service.ConnectionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DisciplinaDAO {

    public List<Disciplina> listarPorEtapa(String etapa) {
        List<Disciplina> lista = new ArrayList<>();
        String sql = "SELECT d.idDisc, d.nome, d.idSem "
                   + "FROM Disciplina d "
                   + "JOIN Semestre s ON s.idSem = d.idSem "
                   + "WHERE s.Etapa = ?";

        try (Connection conn = ConnectionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, etapa);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Disciplina d = new Disciplina();
                    d.setIdDisc(rs.getInt("idDisc"));
                    d.setNome(rs.getString("nome"));
                    d.setIdSem(rs.getInt("idSem"));
                    lista.add(d);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public Disciplina buscarPorId(int idDisc) {
        Disciplina d = null;
        String sql = "SELECT idDisc, nome, idSem FROM Disciplina WHERE idDisc = ?";

        try (Connection conn = ConnectionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idDisc);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    d = new Disciplina();
                    d.setIdDisc(rs.getInt("idDisc"));
                    d.setNome(rs.getString("nome"));
                    d.setIdSem(rs.getInt("idSem"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return d;
    }

    public boolean inserir(Disciplina d) {
        String sql = "INSERT INTO Disciplina (nome, idSem) VALUES (?, ?)";

        try (Connection conn = ConnectionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, d.getNome());
            stmt.setInt(2, d.getIdSem());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizar(Disciplina d) {
        String sql = "UPDATE Disciplina SET nome = ?, idSem = ? WHERE idDisc = ?";

        try (Connection conn = ConnectionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, d.getNome());
            stmt.setInt(2, d.getIdSem());
            stmt.setInt(3, d.getIdDisc());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(int idDisc) {
        String sql = "DELETE FROM Disciplina WHERE idDisc = ?";

        try (Connection conn = ConnectionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idDisc);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}