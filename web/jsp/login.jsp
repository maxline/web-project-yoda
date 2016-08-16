<%-- 
    Document   : login
    Created on : 08.07.2016, 17:59:04
    Author     : Sergey Mikhluk
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<fmt:setLocale value="${not empty param.CHOSEN_LOCALE ? param.CHOSEN_LOCALE: 'en_US'}"/>
<fmt:setBundle basename="dictionary" var="dictionary"/>
<html>
<head>
    <title>Yoda login</title>
    <link href="css/bootstrap.min.css" rel="stylesheet"/>
    <link href="css/signin.css" rel="stylesheet"/>
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/dashboard.js"></script>
</head>

<body>
<form method="post" id="generalForm" action="controller" accept-charset="UTF-8">
    <input class="hidden" id="COMMAND" name="COMMAND" type="text" value="">
    <input class="hidden" id="CHOSEN_LOCALE" name="CHOSEN_LOCALE" type="text" value="${param.CHOSEN_LOCALE}">
    <nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">Yoda</a>
            </div>
            <div class="collapse navbar-collapse" id="myNavbar">

                <ul class="nav navbar-nav">
                    <li><a><c:if test='${not empty MESSAGE}'><fmt:message key="${MESSAGE}"
                                                                          bundle="${dictionary}"/></c:if></a></li>
                </ul>

                <ul class="nav navbar-nav navbar-right">
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href=""><fmt:message key="SETTINGS"
                                                                                               bundle="${dictionary}"/><span
                                class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="" onclick="return setCommandAndLocale('CHOOSE_LOCALE', 'ru_RU')">Ru</a></li>
                            <li><a href="" onclick="return setCommandAndLocale('CHOOSE_LOCALE', 'en_US')">En</a></li>
                        </ul>
                    </li>

                    <li><a href="" onclick="return setCommand('LOGIN')">
                        <span class="glyphicon glyphicon-log-in"></span>
                        <fmt:message key="LOGIN" bundle="${dictionary}"/></a></li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container">
        <div class="form-signin">
            <h2 class="form-signin-heading"><fmt:message key="SIGN_IN_PLEASE" bundle="${dictionary}"/></h2>
            <div style="margin-bottom: 25px" class="input-group">
                <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                <input id="login-username" type="text" class="form-control" name="LOGIN" value=""
                       placeholder="<fmt:message key="LOGIN" bundle="${dictionary}"/>">
            </div>
            <div style="margin-bottom: 25px" class="input-group">
                <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                <input id="login-password" type="password" class="form-control" name="PASSWORD"
                       placeholder="<fmt:message key="PASSWORD" bundle="${dictionary}"/>">
            </div>
            <div class="checkbox">
                <label>
                    <input type="checkbox" name="REMEMBER_ME" value="enabled"> <fmt:message key="REMEMBER_ME"
                                                                                            bundle="${dictionary}"/>
                </label>
            </div>
            <button onclick="return setCommand('LOGIN')"
                    class="btn btn-lg btn-primary btn-block">
                <fmt:message key="SIGN_IN" bundle="${dictionary}"/></button>
        </div>
    </div> <!-- /container -->
</form>
</body>
</html>
