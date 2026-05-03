<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ page import="java.util.List, classes.Categoria" %>

<%
    Categoria categoriaEdicao = (Categoria) request.getAttribute("categoriaEdicao");
    boolean modoEdicao = categoriaEdicao != null;
%>

<html>
<body>

<h2><%= modoEdicao ? "Editar Categoria" : "Cadastro Categoria" %></h2>

<%
    if (request.getParameter("sucesso") != null) {
%>
<p style="color:green;">Operacao realizada com sucesso!</p>
<%
    }
    String erro = request.getParameter("erro");
    if ("duplicado".equals(erro)) {
%>
<p style="color:red;">Ja existe uma categoria com esse nome.</p>
<%
    }
    if ("banco".equals(erro)) {
%>
<p style="color:red;">Erro de banco/conexao ao salvar categoria.</p>
<%
    }
    String erroBanco = (String) request.getAttribute("erroBanco");
    if (erroBanco != null) {
%>
<p style="color:red;"><%= erroBanco %></p>
<%
    }
%>

<form action="${pageContext.request.contextPath}/categoria" method="post">
    <%
        if (modoEdicao) {
    %>
    <input type="hidden" name="id" value="<%= categoriaEdicao.getId() %>">
    <%
        }
    %>

    Nome: <input type="text" name="nome" value="<%= modoEdicao ? categoriaEdicao.getNome() : "" %>" required><br><br>

    Prioridade:
    <select name="prioridade">
        <option <%= modoEdicao && "Alta".equals(categoriaEdicao.getPrioridade()) ? "selected" : "" %>>Alta</option>
        <option <%= modoEdicao && "Media".equals(categoriaEdicao.getPrioridade()) ? "selected" : "" %>>Media</option>
        <option <%= modoEdicao && "Baixa".equals(categoriaEdicao.getPrioridade()) ? "selected" : "" %>>Baixa</option>
    </select>

    <br><br>

    Assuntos:<br>
    <input type="checkbox" name="assuntos" value="Clima"
            <%= modoEdicao && categoriaEdicao.getAssuntos().contains("Clima") ? "checked" : "" %>> Clima<br>
    <input type="checkbox" name="assuntos" value="Comercio"
            <%= modoEdicao && categoriaEdicao.getAssuntos().contains("Comercio") ? "checked" : "" %>> Comercio<br>
    <input type="checkbox" name="assuntos" value="Industria"
            <%= modoEdicao && categoriaEdicao.getAssuntos().contains("Industria") ? "checked" : "" %>> Industria<br>
    <input type="checkbox" name="assuntos" value="Governo"
            <%= modoEdicao && categoriaEdicao.getAssuntos().contains("Governo") ? "checked" : "" %>> Governo<br>

    <br>
    <button type="submit"><%= modoEdicao ? "Atualizar" : "Cadastrar" %></button>
    <%
        if (modoEdicao) {
    %>
    <a href="${pageContext.request.contextPath}/categoria">Cancelar edicao</a>
    <%
        }
    %>
</form>

<hr>

<h3>Lista de Categorias</h3>

<%
    List<Categoria> lista = (List<Categoria>) request.getAttribute("lista");

    if (lista != null && !lista.isEmpty()) {
        for (Categoria c : lista) {
%>
<p>
    ID: <%= c.getId() %> |
    Nome: <%= c.getNome() %> |
    Prioridade: <%= c.getPrioridade() %> |
    Assuntos: <%= c.getAssuntos() %>
</p>

<a href="${pageContext.request.contextPath}/categoria?edit=<%= c.getId() %>">Editar</a>
|
<a href="${pageContext.request.contextPath}/categoria?delete=<%= c.getId() %>">Deletar</a>
<br><br>
<%
    }
} else {
%>
<p>Lista vazia</p>
<%
    }
%>
<a href="${pageContext.request.contextPath}/index.jsp">Voltar</a>
</body>
</html>
