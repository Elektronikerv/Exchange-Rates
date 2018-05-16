<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</head>
<body>

<%@include file="navbar.jsp"%>

<div class="container">
  <form:form action="/update" method="post" modelAttribute="user">
    <h3 class="text-center">Profile update</h3>
    <a href="/changePass">Change password</a><br/>
    <div class="form-group" hidden>
      <form:label path="username">Name</form:label>
      <form:input cssClass="form-control" path="username"/>
    </div>
    <div class="form-group">
      <form:label path="firstName">First name</form:label>
      <form:input cssClass="form-control" path="firstName" id="firstName" required="true"/>
    </div>
    <div class="form-group">
      <form:label path="secondName">Second name</form:label>
      <form:input cssClass="form-control" path="secondName" required="true" name="secondName"/>

    </div>
    <div class="form-group">
      <form:label path="email">Email</form:label>
      <form:input cssClass="form-control" path="email"/>
    </div>
    <div class="form-group">
      <form:label path="currencies">Choose currencies:</form:label>
      <ul>
        <form:checkboxes element="li" path="currencies" items="${checkboxes}"/>
      </ul>
    </div>
    <input class="btn btn-success form-control" type="submit" value="update"/>
  </form:form>
</div>
</body>
</html>
