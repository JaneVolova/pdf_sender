<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Notes Management Application</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
</head>
<body>

<header>
    <nav class="navbar navbar-expand-md navbar-dark"
    <%--         style="background-color: tomato">--%>

    <ul class="navbar-nav">
        <li><a href="<%=request.getContextPath()%>/list"
               class="nav-link">Notes</a></li>
    </ul>
    </nav>
</header>
<br>


<div class="row">
    <div class="container">
<%--        <div class="container text-left">--%>

<%--&lt;%&ndash;            <a href="<%=request.getContextPath()%>/?action=showAll" class="btn btn-success">User List</a>&ndash;%&gt;--%>
<%--        </div>--%>
        <h3 class="text-center">List of Students</h3>
        <hr>
        <div class="container text-left">
        </div>
        <br>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>Name</th>
            </tr>
            </thead>
            <tbody>
<%--            <c:forEach var="note" items="${listName}">--%>

                <tr>
<%--                    <td><c:out value="${note.noteId}"/></td>--%>
                    <td><a href="?action=createNote&fio=Evgeniya"><h3 class="text-center">Evgeniya</h3></a></td>
<%--                    <td><c:out value="${note.date}"/></td>--%>
<%--                    <td><c:out value="${note.description}"/></td>--%>
<%--                    <td><a href="?action=showNotesById&noteId=<c:out value='${note.noteId}' />">Edit</a></td>--%>
<%--                    <td><a href="?action=deleteNote&noteId=<c:out value='${note.noteId}' />">Delete</a></td>--%>
                    </td>
                </tr>
<%--            </c:forEach>--%>
            </tbody>

        </table>
    </div>
</div>
</body>
</html>


