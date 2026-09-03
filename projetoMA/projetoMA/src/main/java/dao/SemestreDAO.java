package dao;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Semestre;
import service.ConnectionBD;

public class SemestreDAO {
    
    // lista todos os semestres cadastrados, do mais recente pro mais antigo
    public List<Semestre> listarTodos() {
        List<Semestre> lista = new ArrayList<>();
        String sql = "SELECT idSem, Etapa FROM Semestre ORDER BY dataInicio DESC";

        try (Connection conn = ConnectionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Semestre sem = new Semestre();
                sem.setIdSem(rs.getInt("idSem"));
                sem.setEtapa(rs.getString("Etapa"));
                lista.add(sem);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
