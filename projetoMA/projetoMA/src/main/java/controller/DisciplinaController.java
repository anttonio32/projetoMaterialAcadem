package controller;
import dao.DisciplinaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.Disciplina;

@WebServlet("/disciplina")
public class DisciplinaController extends HttpServlet {

    private final DisciplinaDAO dao = new DisciplinaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String acao = request.getParameter("acao");
        if (acao == null) acao = "listar";

        switch (acao) {
            case "excluir":
                excluir(request, response);
                break;
            case "ver":
                ver(request, response);
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

    // GET /disciplinas?etapa=I  -> lista as disciplinas da etapa (ou I por padrão)
    private void listar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String etapa = request.getParameter("etapa");
        if (etapa == null || etapa.isEmpty()) {
            etapa = "I";
        }
        List<Disciplina> disciplinas = dao.listarPorEtapa(etapa);
        request.setAttribute("etapaAtual", etapa);
        request.setAttribute("disciplinas", disciplinas);
        request.getRequestDispatcher("/semestres.jsp").forward(request, response);
    }

    // GET /disciplinas?acao=ver&id=5 -> exibe detalhes de uma disciplina
    private void ver(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int idDisc = Integer.parseInt(request.getParameter("id"));
        Disciplina disciplina = dao.buscarPorId(idDisc);
        request.setAttribute("disciplina", disciplina);
        request.getRequestDispatcher("/disciplinaDetalhe.jsp").forward(request, response);
    }

    // POST /disciplinas -> cadastra nova disciplina
    private void inserir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Disciplina disc = new Disciplina();
        disc.setNome(request.getParameter("nome"));
        disc.setIdSem(Integer.parseInt(request.getParameter("idSem")));
        dao.inserir(disc);
        // volta pra listagem da etapa correspondente
        String etapa = request.getParameter("etapa");
        response.sendRedirect("disciplinas?etapa=" + (etapa != null ? etapa : "I"));
    }

    // POST /disciplinas?acao=atualizar -> edita disciplina existente
    private void atualizar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Disciplina disc = new Disciplina();
        disc.setIdDisc(Integer.parseInt(request.getParameter("idDisc")));
        disc.setNome(request.getParameter("nome"));
        disc.setIdSem(Integer.parseInt(request.getParameter("idSem")));
        dao.atualizar(disc);
        String etapa = request.getParameter("etapa");
        response.sendRedirect("disciplinas?etapa=" + (etapa != null ? etapa : "I"));
    }

    // GET /disciplinas?acao=excluir&id=5&etapa=I -> remove disciplina
    private void excluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        int idDisc = Integer.parseInt(request.getParameter("id"));
        dao.excluir(idDisc);
        String etapa = request.getParameter("etapa");
        response.sendRedirect("disciplinas?etapa=" + (etapa != null ? etapa : "I"));
    }
}
