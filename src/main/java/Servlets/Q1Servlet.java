package Servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/q1")
public class Q1Servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String p = request.getParameter("p");

        if (p == null || p.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/no.jsp");
            return;
        }

        switch (p) {
            case "ok":
                response.sendRedirect(request.getContextPath() + "/q1/ok.jsp");
                break;

            case "yes":
                response.sendRedirect(request.getContextPath() + "/q1/yes.jsp");
                break;

            case "no":
                response.sendRedirect(request.getContextPath() + "/q1/no.jsp");
                break;

            default:
                response.sendRedirect(request.getContextPath() + "/q1/no.jsp");
                break;
        }
    }
}
