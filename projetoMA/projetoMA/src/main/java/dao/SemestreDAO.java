package dao;
import model.Semestre;
import service.ConnectionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class SemestreDAO {

    public List<String> listarEtapasCadastradas() {
        List<String> etapas = new ArrayList<>();
        String sql = "SELECT DISTINCT Etapa FROM Semestre "
                   + "ORDER BY FIELD(Etapa,'I','II','III','IV','V','VI','VII','VIII')";

        try (Connection conn = ConnectionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                etapas.add(rs.getString("Etapa"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return etapas;
    }
    
    
    // lista todos os semestres cadastrados, do mais recente pro mais antigo
    public List<Semestre> listarTodos() {
        List<Semestre> lista = new ArrayList<>();
        String sql = "SELECT idSem, dataInicio, dataFim, Etapa FROM Semestre ORDER BY dataInicio DESC";

        try (Connection conn = ConnectionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // busca um semestre pelo id
    public Semestre buscarPorId(int idSem) {
        Semestre sem = null;
        String sql = "SELECT idSem, dataInicio, dataFim, Etapa FROM Semestre WHERE idSem = ?";

        try (Connection conn = ConnectionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idSem);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    sem = mapear(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sem;
    }

    // busca o semestre mais recente de uma determinada etapa (ex: "I")
    // útil pra saber qual idSem usar quando o usuário clica na aba "I"
    public Semestre buscarAtualPorEtapa(String etapa) {
        Semestre sem = null;
        String sql = "SELECT idSem, dataInicio, dataFim, Etapa FROM Semestre "
                   + "WHERE Etapa = ? ORDER BY dataInicio DESC LIMIT 1";

        try (Connection conn = ConnectionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, etapa);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    sem = mapear(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sem;
    }

    // insere um novo semestre
    public String inserir(Semestre sem) {
    String sql = "INSERT INTO Semestre (dataInicio, dataFim, Etapa) VALUES (?, ?, ?)";

    try (Connection conn = ConnectionBD.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setDate(1, sem.getDataInicio());
        stmt.setDate(2, sem.getDataFim());
        stmt.setString(3, sem.getEtapa());
        stmt.executeUpdate();
        return null; // sucesso

    } catch (SQLIntegrityConstraintViolationException e) {
        // capturado quando o unique index de Etapa é violado
        return "Já existe um semestre cadastrado para a Etapa " + sem.getEtapa() + ".";

    } catch (SQLException e) {
        // capturado quando a trigger de validação de data dispara (SIGNAL SQLSTATE '45000')
        if (e.getSQLState() != null && e.getSQLState().equals("45000")) {
            return e.getMessage();
        }
        e.printStackTrace();
        return "Erro ao cadastrar semestre.";
    }
}

    // atualiza um semestre existente
    public boolean atualizar(Semestre sem) {
        String sql = "UPDATE Semestre SET dataInicio = ?, dataFim = ?, Etapa = ? WHERE idSem = ?";

        try (Connection conn = ConnectionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, sem.getDataInicio());
            stmt.setDate(2, sem.getDataFim());
            stmt.setString(3, sem.getEtapa());
            stmt.setInt(4, sem.getIdSem());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // exclui um semestre pelo id
    public boolean excluir(int idSem) {
        String sql = "DELETE FROM Semestre WHERE idSem = ?";

        try (Connection conn = ConnectionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idSem);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Semestre mapear(ResultSet rs) throws SQLException {
        Semestre sem = new Semestre();
        sem.setIdSem(rs.getInt("idSem"));
        sem.setDataInicio(rs.getDate("dataInicio"));
        sem.setDataFim(rs.getDate("dataFim"));
        sem.setEtapa(rs.getString("Etapa"));
        return sem;
    }
}