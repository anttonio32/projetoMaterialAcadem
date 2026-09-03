package controller;

import dao.DisciplinaDAO;
import dao.SemestreDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.Disciplina;
import model.Semestre;

@WebServlet("/disciplina")
public class DisciplinaController extends HttpServlet {

    private final DisciplinaDAO dao = new DisciplinaDAO();
    private final SemestreDAO semestreDao = new SemestreDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String acao = request.getParameter("acao");
        if (acao == null) acao = "listar";

        switch (acao) {
            case "excluir":
                excluir(request, response);
                break;
            case "novo":
                novo(request, response);
                break;
            case "editar":
                editar(request, response);
                break;
            default:
                listar(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

    // GET /disciplina?etapa=I  -> lista as disciplinas da etapa (ou I por padrão)
   private void listar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<String> etapasDisponiveis = semestreDao.listarEtapasCadastradas();

        String etapa = request.getParameter("etapa");
        if (etapa == null || etapa.isEmpty()) {
            etapa = etapasDisponiveis.isEmpty() ? null : etapasDisponiveis.get(0);
        }

        List<Disciplina> disciplinas = (etapa != null) ? dao.listarPorEtapa(etapa) : new java.util.ArrayList<>();

        request.setAttribute("etapasDisponiveis", etapasDisponiveis);
        request.setAttribute("etapaAtual", etapa);
        request.setAttribute("disciplinas", disciplinas);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    // GET /disciplina?acao=novo&etapa=I -> abre form de cadastro em branco
    private void novo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Semestre> semestres = semestreDao.listarTodos();
        request.setAttribute("semestres", semestres);
        request.setAttribute("etapaAtual", request.getParameter("etapa"));
        request.getRequestDispatcher("/formDisciplina.jsp").forward(request, response);
    }

    // GET /disciplina?acao=editar&id=5 -> abre form já preenchido
    private void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int idDisc = Integer.parseInt(request.getParameter("id"));
        Disciplina disciplina = dao.buscarPorId(idDisc);
        List<Semestre> semestres = semestreDao.listarTodos();

        request.setAttribute("disciplina", disciplina);
        request.setAttribute("semestres", semestres);
        request.getRequestDispatcher("/formDisciplina.jsp").forward(request, response);
    }

    // POST /disciplina -> cadastra nova disciplina
    private void inserir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Disciplina disc = new Disciplina();
        disc.setNome(request.getParameter("nome"));
        disc.setIdSem(Integer.parseInt(request.getParameter("idSem")));
        dao.inserir(disc);

        String etapa = request.getParameter("etapa");
        response.sendRedirect("disciplina?etapa=" + (etapa != null ? etapa : "I"));
    }

    // POST /disciplina?acao=atualizar -> edita disciplina existente
    private void atualizar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Disciplina disc = new Disciplina();
        disc.setIdDisc(Integer.parseInt(request.getParameter("idDisc")));
        disc.setNome(request.getParameter("nome"));
        disc.setIdSem(Integer.parseInt(request.getParameter("idSem")));
        dao.atualizar(disc);

        String etapa = request.getParameter("etapa");
        response.sendRedirect("disciplina?etapa=" + (etapa != null ? etapa : "I"));
    }

    // GET /disciplina?acao=excluir&id=5&etapa=I -> remove disciplina
    private void excluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int idDisc = Integer.parseInt(request.getParameter("id"));
        dao.excluir(idDisc);

        String etapa = request.getParameter("etapa");
        response.sendRedirect("disciplina?etapa=" + (etapa != null ? etapa : "I"));
    }
}