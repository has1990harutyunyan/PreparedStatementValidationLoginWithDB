<%@ page import="model.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Book" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
</head>
<body>
<%
    User user = (User) session.getAttribute("user");
%>
<h3> Hello <%= user.getName()%>
</h3>

<div style="width: 1000px">
    <div style="width: 500px" >
        <h2> Add Book </h2>
        <% if (request.getAttribute("errMessage") != null) {%>
        <span style="color: red;"> <%=request.getAttribute("errMessage")%>  </span>
        <% }%>
        <% if (request.getAttribute("info") != null) {%>
        <span> <%=request.getAttribute("info")%></span>
        <% }%>
        <form action="/home" method="post">
            name:<br> <input type="text" name="name"> <br>
            author:<br> <input type="text" name="author"> <br>
            price: <br> <input type="number" name="price"> <br>
            description: <br> <input type="text" name="description"><br>
            <input type="submit" value="submit">
        </form>
    </div>

    <div style="width: 500px; float: right;">
        <table border="1">
            <tr>
                <th> Book Name</th>
            </tr>
            <%
                if (request.getAttribute("userBooks") != null) {
                    ArrayList<Book> userBooks =
                            (ArrayList<Book>) request.getAttribute("userBooks");
                    for (Book userBook : userBooks) {
            %>

            <tr>
                <td><%=userBook.getName()%>
                </td>
            </tr>

            <%
                    }
                }
            %>

        </table>


    </div>


</div>


</body>
<html>