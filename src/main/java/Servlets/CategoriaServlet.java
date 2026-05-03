package Servlets;

import DAO.CategoriaDAO;
import classes.Categoria;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/categoria")
public class CategoriaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        String nome = request.getParameter("nome");
        String prioridade = request.getParameter("prioridade");
        String[] assuntosArray = request.getParameterValues("assuntos");

        List<String> assuntos = new ArrayList<>();
        if (assuntosArray != null) {
            for (String a : assuntosArray) {
                assuntos.add(a);
            }
        }

        try {
            Categoria c = new Categoria(nome, prioridade, assuntos);

            if (idParam != null && !idParam.trim().isEmpty()) {
                c.setId(Integer.parseInt(idParam));
                new CategoriaDAO().atualizar(c);
                response.sendRedirect(request.getContextPath() + "/categoria?sucesso=1");
                return;
            }

            new CategoriaDAO().inserir(c);
            response.sendRedirect(request.getContextPath() + "/categoria?sucesso=1");
        } catch (SQLIntegrityConstraintViolationException e) {
            response.sendRedirect(request.getContextPath() + "/categoria?erro=duplicado");
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().toLowerCase().contains("nome ja existe")) {
                response.sendRedirect(request.getContextPath() + "/categoria?erro=duplicado");
            } else {
                response.sendRedirect(request.getContextPath() + "/categoria?erro=banco");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String delete = request.getParameter("delete");
            String edit = request.getParameter("edit");

            if (delete != null) {
                int id = Integer.parseInt(delete);
                new CategoriaDAO().deletar(id);
                response.sendRedirect(request.getContextPath() + "/categoria");
                return;
            }

            if (edit != null) {
                int id = Integer.parseInt(edit);
                Categoria categoriaEdicao = new CategoriaDAO().buscarPorId(id);
                request.setAttribute("categoriaEdicao", categoriaEdicao);
            }

            List<Categoria> lista = new CategoriaDAO().listar();
            request.setAttribute("lista", lista);
        } catch (Exception e) {
            request.setAttribute("lista", new ArrayList<Categoria>());
            request.setAttribute("erroBanco", "Nao foi possivel consultar o banco de dados: " + e.getMessage());
        }

        request.getRequestDispatcher("/q5/q5.jsp").forward(request, response);
    }
}
