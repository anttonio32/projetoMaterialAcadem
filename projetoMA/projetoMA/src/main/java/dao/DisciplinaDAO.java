package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Disciplina;
import service.ConnectionBD;

public class DisciplinaDAO {
    
    public List<Disciplina> listarPorEtapa(String etapa) {
        List<Disciplina> lista = new ArrayList<>();

        String sql = "SELECT d.idDisc, d.nome AS disciplina,  d.idSem "
                   + "FROM Disciplina d "
                   + "JOIN Semestre s ON s.idSem = d.idSem "
                   + "WHERE s.Etapa = ?";

        try (Connection conn = ConnectionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, etapa);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Disciplina disc = new Disciplina();
                    disc.setIdDisc(rs.getInt("idDisc"));
                    disc.setNome(rs.getString("disciplina"));
                    disc.setIdSem(rs.getInt("idSem"));
                    lista.add(disc);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // busca uma disciplina específica pelo id (útil pro botão "ver"/"editar")
    public Disciplina buscarPorId(int idDisc) {
        Disciplina disc = null;

        String sql = "SELECT d.idDisc, d.nome AS disciplina, d.idSem "
                   + "FROM Disciplina d "
                   + "WHERE d.idDisc = ?";

        try (Connection conn = ConnectionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idDisc);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    disc = new Disciplina();
                    disc.setIdDisc(rs.getInt("idDisc"));
                    disc.setNome(rs.getString("disciplina"));
                    disc.setIdSem(rs.getInt("idSem"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return disc;
    }

    // insere uma nova disciplina (INSERT ligado ao botão "Nova Disciplina")
    public boolean inserir(Disciplina disc) {
        String sql = "INSERT INTO Disciplina (nome, idSem) VALUES (?, ?)";

        try (Connection conn = ConnectionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, disc.getNome());
            stmt.setInt(3, disc.getIdSem());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // atualiza uma disciplina existente (botão "editar")
    public boolean atualizar(Disciplina disc) {
        String sql = "UPDATE Disciplina SET nome = ?, idSem = ? WHERE idDisc = ?";

        try (Connection conn = ConnectionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, disc.getNome());
            stmt.setInt(3, disc.getIdSem());
            stmt.setInt(4, disc.getIdDisc());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // exclui uma disciplina pelo id (botão "excluir")
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
