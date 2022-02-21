<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User Management Application</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
</head>
<body>

<header>
    <nav class="navbar navbar-expand-md navbar-dark"
         style="background-color: cadetblue">

        <ul class="navbar-nav">
            <li><a href="<%=request.getContextPath()%>/list"
                   class="nav-link">Students Note</a></li>
        </ul>
    </nav>
</header>
<br>
<div class="container text-left">

    <a href="<%=request.getContextPath()%>/?action=createForm" class="btn btn-success">Add Note</a>
</div>
<br>
<div class="row">
    <div class="container">
        <h3 class="text-center">List of Notes</h3>
        <hr>
        <br>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>Student</th>
                <th>Date</th>
                <th>Description</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="note" items="${listNotes}">

                <tr>
                    <td><c:out value="${note.fio}"/></td>
                    <td><c:out value="${note.date}"/></td>
                    <td><c:out value="${note.description}"/></td>
                </tr>
            </c:forEach>
            </tbody>

        </table>
    </div>
</div>
</body>
</html>


