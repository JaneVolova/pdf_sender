<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
</head>
<body>

<header>
    <nav class="navbar navbar-expand-md navbar-dark"
         style="background-color: cadetblue">

    </nav>
</header>
<br>
<div class="container col-md-5">
    <div class="card">
        <div class="card-body">
            <c:if test="${note == null}">
            <form action="?action=createNote" method="post">
                </c:if>
                <caption>
                    <h2>
                        <c:if test="${note == null}">
                            Create Note
                        </c:if>
                    </h2>
                </caption>

                <c:if test="${note == null}">
                    <input type="text" name="fio" value="<c:out value='${note.fio}'/>"/>
                </c:if>
                <fieldset class=" form-group">
                    <label>Date</label> <input type="date"
                                               value="<c:out value='${note.date}' />" class="form-control"
                                               name="date" required="required">
                </fieldset>


                <fieldset class="form-group">
                    <label>Description</label> <input type="text"
                                                      value="<c:out value='${note.description}' />"
                                                      class="form-control"
                                                      name="description">
                </fieldset>

                <button type="submit" class="btn btn-success">Save</button>

            </form>
        </div>
    </div>
</div>
</body>
</html>
