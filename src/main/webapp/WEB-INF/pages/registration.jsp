<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign up</title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

</head>

<body>
    <nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="/">Exchange Rates</a>
            </div>
        </div>
    </nav>

    <div class="container">
        <form:form  action="/registration" method="post" modelAttribute="user">
            <h3 class="text-center">Sign up</h3>

            <div class="form-group">
                <form:label path="username">Name</form:label>
                <form:input cssClass="form-control" path="username"/>
                <small class="text-danger">
                    <form:errors path="username"/>
                </small>
            </div>
            <div class="form-group">
                <form:label path="password">password</form:label>
                <form:password cssClass="form-control" path="password"/>
                <small class="text-danger">
                    <form:errors path="password"/>
                </small>
            </div>
            <div class="form-group">
                <form:label path="firstName">First name</form:label>
                <form:input cssClass="form-control" path="firstName"/>
                <small class="text-danger">
                    <form:errors path="firstName"/>
                </small>
            </div>
            <div class="form-group">
                <form:label path="secondName">Second name</form:label>
                <form:input cssClass="form-control" path="secondName"/>
                <small class="text-danger">
                    <form:errors path="secondName"/>
                </small>
            </div>
            <div class="form-group">
                <form:label path="email">Email</form:label>
                <form:input cssClass="form-control" path="email"/>
                <small class="text-danger">
                    <form:errors path="email"/>
                </small>
            </div>
            <div class="form-group">
                <form:label path="currencies">Choose currencies:</form:label>
                <ul>
                    <small class="text-danger">
                        <form:errors path="currencies"/>
                    </small>
                    <form:checkboxes element="li" path="currencies" items="${checkboxes}"/>
                </ul>
            </div>
            <input class="btn btn-success form-control" type="submit" value="Sign up"/>
        </form:form>
    </div>
</body>
</html>