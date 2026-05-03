package Servlets;

import java.io.IOException;

import classes.Pessoa1;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/q3criarsessao")
public class Q3Servlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nome = request.getParameter("nome");
        int idade = Integer.parseInt(request.getParameter("idade"));

        Pessoa1 p = new Pessoa1(nome, idade);

        HttpSession session = request.getSession();
        session.setAttribute("pessoa", p);

        response.sendRedirect(request.getContextPath() + "/q3/q3.jsp");
    }
}