<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>

<html>
<body>

<%
    List<String> lista = (List<String>) request.getAttribute("lista");

    if (lista == null || lista.isEmpty()) {
%>
<h2>Lista Vazia</h2>
<a href="index.jsp">Voltar</a>
<%
} else {
%>
<h2>Tamanho: <%= lista.size() %></h2>

<%
    for (String s : lista) {
%>
<p><%= s %></p>
<%
        }
    }
%>

<a href="${pageContext.request.contextPath}/index.jsp">Voltar</a>
</body>
</html>