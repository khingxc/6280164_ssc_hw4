<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Login Webapp</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</head>
<title>Home Page</title>
<body>

<div class="container mt-4">
    <nav class="navbar navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand">SSC - Login Webapp</a>
            <a class="btn btn-danger" type="button" href="/logout"><i class="fa fa-sign-out"></i>Log Out</a>
        </div>
    </nav>
    <div class ="row">
        <div class ="col-12">
            <h3 class="my-3">
                Welcome, ${username}
            </h3>
        </div>
    </div>
    <div class ="row">
        <div class ="col-12">
            <c:if test="${not empty message}">
                <c:choose>
                    <c:when test="${hasError}">
                        <div class="alert alert-danger" role="alert">
                                ${message}
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="alert alert-success" role="alert">
                                ${message}
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </div>
    </div>
    <div class ="row mb-4">
        <div class ="col-12">
            <a class="btn btn-info" type="button" href="/user/create">
                <i class="fa fa-user"></i> &nbsp; New User
            </a>
        </div>
    </div>
    <div class ="row">
        <div class ="col-12">
            <table class="table table-dark table-striped table-bordered">
                <thead>
                <tr>
                    <th class="py-2">Id</th>
                    <th class="py-2">Username</th>
                    <th class="py-2">Display Name</th>
                    <th class="py-2">Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var = "user" items="${users}">
                    <tr>
                        <td class="py-2">${user.id}</td>
                        <td class="py-2">${user.username}</td>
                        <td class="py-2">${user.displayName}</td>
                        <td class="align-middle">
                            <!-- Button trigger modal -->
                            <button class="btn btn-primary" type="button">Edit<i class="fa fa-pencil"></i></button>
                            <c:if test="${currentUser.username != user.username}">
                                <%--                        let's add remove user confirmation!--%>
                                <button
                                        class="btn btn-warning"
                                        type="button" href="/user/delete?username=${user.username}"
                                        data-bs-toggle="modal"
                                        data-bs-target="#delete-modal-${user.id}">
                                    Delete<i class="fa fa-trash"></i>
                                </button>


                                <!-- Modal -->
                                <div class="modal fade" id="delete-modal-${user.id}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="exampleModalLabel" style="color:black">User Deleting Confirmation</h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body my-4" style="color:gray">
                                                Do you want to delete <b style="color:darkred">${user.displayName}</b> (<b style="color:darkgreen">${user.username}</b>) ?
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                <a class="btn btn-warning" href="/user/delete?username=${user.username}"> &nbsp; Delete<i class="fa fa-trash"></i></a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

</div>
</body>
</html>
<%--<%--%>
<%--    session.removeAttribute("hasError");--%>
<%--    session.removeAttribute("message");--%>
<%--%>--%>
