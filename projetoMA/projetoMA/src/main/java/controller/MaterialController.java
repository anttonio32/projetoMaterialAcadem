package controller;
import dao.DisciplinaDAO;
import dao.MaterialDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.List;
import model.Disciplina;
import model.Material;

@WebServlet("/material")
@MultipartConfig(maxFileSize = 10 * 1024 * 1024) // limite de 10MB por arquivo
public class MaterialController extends HttpServlet {

    private final MaterialDAO dao = new MaterialDAO();
    private final DisciplinaDAO disciplinaDao = new DisciplinaDAO();

    // pasta física onde os arquivos vão ficar salvos no servidor
    private static final String PASTA_UPLOAD = "C:/uploads/materialacademico/";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String acao = request.getParameter("acao");
        if (acao == null) acao = "listar";

        if ("excluir".equals(acao)) {
            excluir(request, response);
        } else {
            listar(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        inserir(request, response);
    }

    // GET /material?idDisc=3 -> lista os materiais da disciplina
    private void listar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int idDisc = Integer.parseInt(request.getParameter("idDisc"));
        Disciplina disciplina = disciplinaDao.buscarPorId(idDisc);
        List<Material> materiais = dao.listarPorDisciplina(idDisc);

        request.setAttribute("disciplina", disciplina);
        request.setAttribute("materiais", materiais);
        request.getRequestDispatcher("/material.jsp").forward(request, response);
    }

    // POST /material -> faz upload e cadastra o material
    private void inserir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int idDisc = Integer.parseInt(request.getParameter("idDisc"));
        String nome = request.getParameter("nome");
        String tipo = request.getParameter("tipo");
        Part arquivo = request.getPart("arquivo");

        // garante que a pasta existe
        File pasta = new File(PASTA_UPLOAD);
        if (!pasta.exists()) {
            pasta.mkdirs();
        }

        // gera um nome único pro arquivo no disco (evita sobrescrever arquivos com mesmo nome)
        String nomeOriginal = extrairNomeArquivo(arquivo);
        String nomeArquivoDisco = System.currentTimeMillis() + "_" + nomeOriginal;
        String caminhoCompleto = PASTA_UPLOAD + nomeArquivoDisco;

        arquivo.write(caminhoCompleto);

        Material m = new Material();
        m.setNome(nome);
        m.setTipo(tipo);
        m.setCaminhoArquivo(caminhoCompleto);
        m.setIdDisc(idDisc);

        dao.inserir(m);

        response.sendRedirect("material?idDisc=" + idDisc);
    }

    // GET /material?acao=excluir&id=7&idDisc=3
    private void excluir(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int idMat = Integer.parseInt(request.getParameter("id"));
        int idDisc = Integer.parseInt(request.getParameter("idDisc"));

        Material m = dao.buscarPorId(idMat);
        if (m != null) {
            File arquivo = new File(m.getCaminhoArquivo());
            if (arquivo.exists()) {
                arquivo.delete();
            }
        }

        dao.excluir(idMat);
        response.sendRedirect("material?idDisc=" + idDisc);
    }

    private String extrairNomeArquivo(Part part) {
        String header = part.getHeader("content-disposition");
        for (String token : header.split(";")) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return "arquivo";
    }
}