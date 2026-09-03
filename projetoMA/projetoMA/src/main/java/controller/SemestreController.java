package controller;
import dao.SemestreDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Semestre;
import java.io.IOException;
import java.util.List;

@WebServlet("/semestre")
public class SemestreController extends HttpServlet {

    private final SemestreDAO dao = new SemestreDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Semestre> semestres = dao.listarTodos();
        request.setAttribute("semestres", semestres);
        request.getRequestDispatcher("/listaSemestres.jsp").forward(request, response);
    }
}