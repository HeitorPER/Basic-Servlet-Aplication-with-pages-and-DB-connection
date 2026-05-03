<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<body>
<h1>MAIN PAGE</h1>

<ul>
    <li><a href="${pageContext.request.contextPath}/q1?p=ok">Q1 - OK</a></li>
    <li><a href="${pageContext.request.contextPath}/q1?p=yes">Q1 - YES</a></li>
    <li><a href="${pageContext.request.contextPath}/q1?p=no">Q1 - NO</a></li>

    <li><a href="${pageContext.request.contextPath}/q2?all=1">Q2 - Lista cheia</a></li>
    <li><a href="${pageContext.request.contextPath}/q2?all=2">Q2 - Lista vazia</a></li>

    <li><a href="${pageContext.request.contextPath}/q3/q3.jsp">Q3</a></li>
    <li><a href="${pageContext.request.contextPath}/q4/q4.jsp">Q4</a></li>
    <li><a href="${pageContext.request.contextPath}/categoria">Q5</a></li>
</ul>
</body>
</html>
