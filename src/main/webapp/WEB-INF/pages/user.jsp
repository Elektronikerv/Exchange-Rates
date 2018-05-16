<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1"/>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

    <script>
        function isValidDate() {
            var date = new Date(document.getElementById("date").value);
            var checkButton = document.getElementById("check");
            var currentDate = new Date();
            if(date > currentDate) {
                alert("Invalid date");
                checkButton.disabled = true;
            }
            else
                checkButton.disabled = false;
        }
    </script>
</head>
<body>
<%@include file="navbar.jsp"%>

<div class="container">
    <h2>Exchange Rates at ${date} to USD</h2><br/>

    <form class="form" action="/user" method="get">
        <div class="form-group">
        <label for="date">Choose date for currencies </label>
            <input class="form-conrol" type="date" name="date" id="date" required="true" onchange="isValidDate()" />
            <input class="btn btn-primary" type="submit" value="Check" id="check">
        </div>
    </form>

    <table class="table table-striped">
        <thead>
            <th>Currency</th>
            <th>Description</th>
            <th>Value</th>
        </thead>
        <c:forEach items="${currencies}" var="entry">
            <tr>
                <td>${entry.key}</td>
                <td>${entry.key.description}</td>
                <td>${entry.value}</td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>