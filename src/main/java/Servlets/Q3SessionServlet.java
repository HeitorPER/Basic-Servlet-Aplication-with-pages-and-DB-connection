package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import classes.Pessoa1;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/q3mostrarsessao")
public class Q3SessionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        if (session == null) {
            out.println("<h2>Sem sessão</h2>");
            return;
        }

        Pessoa1 p = (Pessoa1) session.getAttribute("pessoa");

        out.println("<h2>ID: " + session.getId() + "</h2>");
        out.println("<h2>Último acesso: " + new Date(session.getLastAccessedTime()) + "</h2>");

        if (p != null) {
            out.println("<p>Nome: " + p.getNome() + "</p>");
            out.println("<p>Idade: " + p.getIdade() + "</p>");
        } else {
            out.println("<p>Nenhuma pessoa na sessão</p>");
        }
    }
}