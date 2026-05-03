<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<body>

<h2>Formulário</h2>

<form action="${pageContext.request.contextPath}/q3criarsessao" method="post">
    Nome: <input type="text" name="nome"><br>
    Idade: <input type="text" name="idade"><br>
    <input type="submit" value="Enviar">
</form>

<br>

<a href="${pageContext.request.contextPath}/q3mostrarsessao">
    Mostrar Sessão
</a>

<a href="${pageContext.request.contextPath}/index.jsp">Voltar</a>
</body>
</html>