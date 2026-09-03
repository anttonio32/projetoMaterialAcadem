package dao;
import model.Material;
import service.ConnectionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAO {

    public List<Material> listarPorDisciplina(int idDisc) {
        List<Material> lista = new ArrayList<>();
        String sql = "SELECT idMat, nome, tipo, caminhoArquivo, idDisc "
                   + "FROM MaterialAcademico WHERE idDisc = ?";

        try (Connection conn = ConnectionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idDisc);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Material m = new Material();
                    m.setIdMat(rs.getInt("idMat"));
                    m.setNome(rs.getString("nome"));
                    m.setTipo(rs.getString("tipo"));
                    m.setCaminhoArquivo(rs.getString("caminhoArquivo"));
                    m.setIdDisc(rs.getInt("idDisc"));
                    lista.add(m);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public boolean inserir(Material m) {
        String sql = "INSERT INTO MaterialAcademico (nome, tipo, caminhoArquivo, idDisc) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, m.getNome());
            stmt.setString(2, m.getTipo());
            stmt.setString(3, m.getCaminhoArquivo());
            stmt.setInt(4, m.getIdDisc());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(int idMat) {
        String sql = "DELETE FROM MaterialAcademico WHERE idMat = ?";

        try (Connection conn = ConnectionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMat);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // usado no controller pra saber o caminho do arquivo antes de excluir do disco
    public Material buscarPorId(int idMat) {
        Material m = null;
        String sql = "SELECT idMat, nome, tipo, caminhoArquivo, idDisc FROM MaterialAcademico WHERE idMat = ?";

        try (Connection conn = ConnectionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMat);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    m = new Material();
                    m.setIdMat(rs.getInt("idMat"));
                    m.setNome(rs.getString("nome"));
                    m.setTipo(rs.getString("tipo"));
                    m.setCaminhoArquivo(rs.getString("caminhoArquivo"));
                    m.setIdDisc(rs.getInt("idDisc"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return m;
    }
}