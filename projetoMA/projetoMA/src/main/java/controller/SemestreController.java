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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String acao = request.getParameter("acao");
        if (acao == null) acao = "listar";

        if ("novo".equals(acao)) {
            request.getRequestDispatcher("/formSemestre.jsp").forward(request, response);
        } else {
            listar(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Semestre sem = new Semestre();
        sem.setDataInicio(Date.valueOf(request.getParameter("dataInicio")));
        sem.setDataFim(Date.valueOf(request.getParameter("dataFim")));
        sem.setEtapa(request.getParameter("etapa"));

        String erro = dao.inserir(sem);

        if (erro != null) {
            request.setAttribute("erro", erro);
            request.getRequestDispatcher("/formSemestre.jsp").forward(request, response);
            return;
        }

        response.sendRedirect("disciplina?acao=novo");
    }

    private void listar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Semestre> semestres = dao.listarTodos();
        request.setAttribute("semestres", semestres);
        request.getRequestDispatcher("/listaSemestres.jsp").forward(request, response);
    }
}