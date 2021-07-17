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
    <h3 class="my-3">
        Welcome, ${username}
    </h3>
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
                    <button class="btn btn-warning" type="button">Delete <i class="fa fa-trash"></i></button>
                    <button class="btn btn-primary" type="button">Edit <i class="fa fa-pencil"></i></button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>

