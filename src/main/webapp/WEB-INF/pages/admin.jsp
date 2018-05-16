<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
</head>
<body>
<%@include file="navbar.jsp" %>
<div class="container">
    <h3 class="text-center">All users</h3>
    <table class="table table-striped">
        <thead>
        <th>Username</th>
        <th>First name</th>
        <th>Second name</th>
        <th>Email</th>
        <th>Role</th>
        <th>Currencies</th>
        <th>Delete</th>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="u">
            <tr>
                <td>${u.username}</td>
                <td>${u.firstName}</td>
                <td>${u.secondName}</td>
                <td>${u.email}</td>
                <td>${u.authorities}</td>
                <td>${u.currencies}</td>
                <c:if test="${user.id != u.id}">
                    <form method="GET" action="admin/delete/${u.id}">
                        <td><input type="submit" class="btn btn-danger" value="delete" name="name"></td>
                    </form>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>