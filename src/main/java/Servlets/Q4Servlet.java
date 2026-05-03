package Servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import DAO.FuncionarioDAO;
import classes.Funcionario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/cadastrar")
public class Q4Servlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nome = request.getParameter("nome");
        String cargo = request.getParameter("cargo");

        String[] tecnologiasArray = request.getParameterValues("tecnologia");

        List<String> tecnologias = new ArrayList<>();

        if (tecnologiasArray != null) {
            for (String t : tecnologiasArray) {
                tecnologias.add(t);
            }
        }

        Funcionario f = new Funcionario(nome, cargo, tecnologias);

        FuncionarioDAO dao = new FuncionarioDAO();
        dao.inserir(f);

        // redireciona com parâmetro de sucesso
        response.sendRedirect(request.getContextPath() + "/q4/q4.jsp?sucesso=1");
    }
}
