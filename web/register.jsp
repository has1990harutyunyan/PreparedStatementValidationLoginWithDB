<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
<%
    if (request.getAttribute("errMessage") != null) {
%>
<span style="color: red"> <%=request.getAttribute("errMessage")%> </span>

<%
    }
%>
<form action="/register" method="post">
    name: <input type="text" name="name"> <br><br>
    surname: <input type="text" name="surname"><br><br>
    email: <input type="text" name="email"><br><br>
    gender: male<input type="radio" name="gender" value="MALE">
    female<input type="radio" name="gender" value="FEMALE"> <br><br>
    password: <input type="password" name="password"> <br><br>
    <input type="submit" value="register">

</form>
<a href="index.jsp">login</a>
</body>
</html>
