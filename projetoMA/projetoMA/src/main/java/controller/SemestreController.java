package controller;
import dao.SemestreDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Semestre;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/semestre")
public class SemestreController extends HttpServlet {

    private final SemestreDAO dao = new SemestreDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");
        if (acao == null) acao = "listar";

        switch (acao) {
            case "excluir":
                excluir(request, response);
                break;
            default:
                listar(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");
        if (acao == null) acao = "";

        switch (acao) {
            case "atualizar":
                atualizar(request, response);
                break;
            default:
                inserir(request, response);
        }
    }

    private void listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Semestre> semestres = dao.listarTodos();
        request.setAttribute("semestres", semestres);
        request.getRequestDispatcher("/semestres.jsp").forward(request, response);
    }

    private void inserir(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Semestre sem = new Semestre();
        sem.setDataInicio(Date.valueOf(request.getParameter("dataInicio"))); // formato yyyy-MM-dd
        sem.setDataFim(Date.valueOf(request.getParameter("dataFim")));
        sem.setEtapa(request.getParameter("etapa"));

        boolean sucesso = dao.inserir(sem);

        if (!sucesso) {
            request.setAttribute("erro", "Não foi possível cadastrar o semestre. Verifique se a data de fim é posterior à data de início.");
        }

        response.sendRedirect("semestre");
    }

    private void atualizar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Semestre sem = new Semestre();
        sem.setIdSem(Integer.parseInt(request.getParameter("idSem")));
        sem.setDataInicio(Date.valueOf(request.getParameter("dataInicio")));
        sem.setDataFim(Date.valueOf(request.getParameter("dataFim")));
        sem.setEtapa(request.getParameter("etapa"));

        dao.atualizar(sem);
        response.sendRedirect("semestre");
    }

    private void excluir(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idSem = Integer.parseInt(request.getParameter("id"));
        dao.excluir(idSem);
        response.sendRedirect("semestre");
    }
}