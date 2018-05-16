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

<%@include file="navbar.jsp" %>
<body style="background: #6f472f">
<div class="container">
    <div class="col-lg-4"></div>
    <div class="col-lg-4">
        <div class="jumbotron" style="margin-top: 100px; ">
            <h2>Login</h2>
            <form name='f' action="login" method='POST'>
                <div class=" form-group">
                    <c:if test="${param.error ne null}">
                        <div style="color: red">Invalid username or password</div>
                    </c:if>
                    <label for='username'>User:</label>
                    <div>
                        <input type='text' class="form-control" id="username" name='username' value=''>
                    </div>
                </div>
                <div class="form-group">
                    <label for='password'>Password:</label>
                    <div>
                        <input type='password' class="form-control" id='password' name='password'/>
                    </div>
                </div>
                <div>
                    <input name="submit" class="btn btn-success form-control" type="submit" value="Log in"/>
                </div>
            </form>
        </div>
    </div>
    <div class="col-lg-4"></div>
</div>
</body>
</html>