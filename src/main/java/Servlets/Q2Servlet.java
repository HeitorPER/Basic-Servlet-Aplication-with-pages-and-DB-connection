package Servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/q2")
public class Q2Servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String all = request.getParameter("all");
        List<String> lista = new ArrayList<>();

        if ("1".equals(all)) {
            for (int i = 1; i <= 10; i++) {
                lista.add("String " + i);
            }
        }

        // para all=2 → lista fica vazia

        request.setAttribute("lista", lista);
        request.getRequestDispatcher("/q2/q2.jsp").forward(request, response);
    }
}