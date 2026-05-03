<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<html>
<body>
<h2>Cadastro</h2>
<%
    String sucesso = request.getParameter("sucesso");
    if (sucesso != null) {
%>
<h3>Item cadastrado com sucesso.</h3>
<%
    }
%>
<form action="${pageContext.request.contextPath}/cadastrar" method="post">
    Nome: <input type="text" name="nome"><br><br>
    Cargo:
    <select name="cargo">
        <option value="programador">Programador</option>
        <option value="testador">Testador</option>
        <option value="engenheiro_software">Engenheiro de Software</option>
        <option value="analista_requisitos">Analista de Requisitos</option>
    </select>
    <br><br>
    <fieldset>
        <legend>Tecnologias de Domínio:</legend>
        <input type="checkbox" name="tecnologia" value="java">Java<br>
        <input type="checkbox" name="tecnologia" value="php">PHP<br>
        <input type="checkbox" name="tecnologia" value="nodejs"> Node.js<br>
        <input type="checkbox" name="tecnologia" value="cpp"> C++<br>
        <input type="checkbox" name="tecnologia" value="dotnet"> Dot.Net
    </fieldset>
    <br><br>
    <button type="submit">Cadastrar</button>
</form>
<a href="${pageContext.request.contextPath}/index.jsp">Voltar</a>
</body>
</html>
