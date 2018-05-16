<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="/">Exchange Rates</a>
        </div>
        <ul class="nav navbar-nav navbar-right">
            <c:if test="${empty user}">
                <li><a href="/registration"><span class="glyphicon glyphicon-user"></span>Sign Up</a></li>
            </c:if>
            <c:if test="${!empty user}">
                <li>
                    <a href="/update"><span class="glyphicon glyphicon-user"></span>Profile</a>
                </li>
                <li>
                    <a href="/logout"><span class="glyphicon glyphicon-log-out"></span>Log out</a>
                </li>
            </c:if>
        </ul>
    </div>
</nav>
