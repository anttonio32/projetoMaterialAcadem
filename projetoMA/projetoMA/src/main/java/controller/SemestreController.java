package controller;
import dao.SemestreDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import model.Semestre;


@WebServlet("/semestre")
public class SemestreController extends HttpServlet {
    private final SemestreDAO dao = new SemestreDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Semestre> semestres = dao.listarTodos();
        request.setAttribute("semestres", semestres);
        request.getRequestDispatcher("/semestres.jsp").forward(request, response);
    }
}
