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
         style="background-color: cadetblue">

        <ul class="navbar-nav">
            <li><a href="<%=request.getContextPath()%>/list"
                   class="nav-link">Notes</a></li>
        </ul>
    </nav>
</header>
<br>

<div class="row">
    <div class="container">

        <h3 class="text-center">List of Students</h3>
        <hr>
        <div class="container text-left">
        </div>
        <br>
        <tr><a href="list?action=showAllNotesByStudent&fio=Aleksandr%20Korotkov"><h3 class="text-center">Aleksandr
            Korotkov</h3></a></tr>
        <tr><a href="?action=showAllNotesByStudent&fio=Damir%20Yaminov"><h3 class="text-center">Damir Yaminov</h3></a>
        </tr>
        <tr><a href="?action=showAllNotesByStudent&fio=Evgeniya%20Barysheva"><h3 class="text-center">Evgeniya
            Barysheva</h3></a></tr>
        <tr><a href="?action=showAllNotesByStudent&fio=Nikolay%20Gritsenko"><h3 class="text-center">Nikolay
            Gritsenko</h3></a></tr>
        <tr><a href="?action=showAllNotesByStudent&fio=Oleg%20Dumava"><h3 class="text-center">Oleg Dumava</h3></a></tr>
        <tr><a href="?action=showAllNotesByStudent&fio=Sergey%20Ponomarev"><h3 class="text-center">Sergey Ponomarev</h3>
        </a></tr>
        </td>

    </div>
</div>
</body>
</html>


