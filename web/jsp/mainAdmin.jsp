<%--
  Created by IntelliJ IDEA.
  User: Serg
  Date: 17.06.2016
  Time: 14:57
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" uri="http://epam.com/yoda/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Yoda 1.00</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/dashboard.css" rel="stylesheet">
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/dashboard.js"></script>
</head>
<body>
<form method="post" id="generalForm" action="controller" accept-charset="UTF-8">
    <input class="hidden" id="COMMAND" name="COMMAND" type="text" value="">
    <input class="hidden" id="CHOSEN_LOCALE" name="CHOSEN_LOCALE" type="text" value="">
    <input class="hidden" id="CHOSEN_ITEM" name="CHOSEN_ITEM" type="text" value="${param.CHOSEN_ITEM}">
    <input class="hidden navbar-form navbar-right form-control" id="CHOSEN_ELEMENT_ID" name="CHOSEN_ELEMENT_ID"
           type="text" value="${param.CHOSEN_ELEMENT_ID}">

    <nav class="navbar navbar-inverse navbar-fixed-top">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                        aria-expanded="false" aria-controls="navbar">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="">Yoda</a>
            </div>

            <div id="navbar" class="navbar-collapse collapse">
                <ul class="nav navbar-nav">
                    <li><a> ${MESSAGE}</a></li>
                </ul>

                <ul class="nav navbar-nav navbar-right">
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">${DICTIONARY_BUNDLE.get("SETTINGS")}<span
                                class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="" onclick="return setCommandAndLocale('CHOOSE_LOCALE', 'EN')">En</a></li>
                            <li><a href="" onclick="return setCommandAndLocale('CHOOSE_LOCALE', 'RU')">Ru</a></li>
                        </ul>
                    </li>

                    <li><a>${USER.login}</a></li>
                    <li><a href="" onclick="return setCommand('LOGOUT')">
                        <span class="glyphicon glyphicon-log-out"></span> ${DICTIONARY_BUNDLE.get("LOGOUT")}</a></li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container-fluid">
        <div class="row">
            <div class="col-md-2 sidebar">
                <ul class="nav nav-sidebar">
                    <li class="active"><a href="" onclick="return false">${DICTIONARY_BUNDLE.get("ADMIN_PAGE")}<span
                            class="sr-only">(current)</span></a></li>
                    <li><a href="" onclick="return setCommandAndItem('DISPLAY_ADMIN', 'CATEGORY')">${DICTIONARY_BUNDLE.get("CATEGORIES")}</a></li>
                    <li><a href="" onclick="return setCommandAndItem('DISPLAY_ADMIN', 'STATUS')">${DICTIONARY_BUNDLE.get("STATUSES")}</a></li>
                    <li><a href="" onclick="return setCommandAndItem('DISPLAY_ADMIN', 'ACTIVITY')">${DICTIONARY_BUNDLE.get("ACTIVITIES")}</a></li>
                    <li><a href="" onclick="return setCommandAndItem('DISPLAY_ADMIN', 'USER')">${DICTIONARY_BUNDLE.get("USERS")}</a></li>
                </ul>
            </div>

            <c:choose>
                <c:when test='${"CATEGORY".equals(param.CHOSEN_ITEM)}'>
                    <jsp:include page="includes/categories.jsp"></jsp:include>
                </c:when>
                <c:when test='${"STATUS".equals(param.CHOSEN_ITEM)}'>
                    <jsp:include page="includes/statuses.jsp"></jsp:include>
                </c:when>
                <c:when test='${"ACTIVITY".equals(param.CHOSEN_ITEM)}'>
                    <jsp:include page="includes/activities.jsp"></jsp:include>
                </c:when>
                <c:when test='${"USER".equals(param.CHOSEN_ITEM)}'>
                    <jsp:include page="includes/users.jsp"></jsp:include>
                </c:when>
                <c:otherwise>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</form>
</body>
</html>