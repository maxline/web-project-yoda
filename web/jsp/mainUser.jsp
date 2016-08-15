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
                <a class="navbar-brand" href="#">Yoda</a>
            </div>

            <div id="navbar" class="navbar-collapse collapse">

                <ul class="nav navbar-nav">
                    <li><a> ${MESSAGE}</a></li>
                </ul>

                <ul class="nav navbar-nav navbar-right">
                    <li><a href="controller">${DICTIONARY_BUNDLE.get("TASKS")}</a></li>
                    <li><a href="controller">${DICTIONARY_BUNDLE.get("KNOWLEDGE_BASE")}</a></li>

                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="">${DICTIONARY_BUNDLE.get("SETTINGS")}<span
                                class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="" onclick="return setCommandAndLocale('CHOOSE_LOCALE', 'EN')">En</a></li>
                            <li><a href="" onclick="return setCommandAndLocale('CHOOSE_LOCALE', 'RU')">Ru</a></li>
                        </ul>
                    </li>
                    <li><a>${USER.login}</a></li>
                    <li><a href="" onclick="return setCommand('LOGOUT')">
                        <span class="glyphicon glyphicon-log-out"></span>${DICTIONARY_BUNDLE.get("LOGOUT")}</a></li>
                </ul>

                <input type="text" class="navbar-form navbar-right form-control" name="SUB_FILTER_TASK_NAME"
                       value="${param.SUB_FILTER_TASK_NAME}"
                       placeholder="Search...">
            </div>
        </div>
    </nav>

    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-2 col-md-2 sidebar">
                <h3>${DICTIONARY_BUNDLE.get("CATEGORIES")}</h3>

                <div class="form-group">
                    <div id="categories" class="checkbox">
                        <c:forEach var="categoryEntry" items="${CATEGORIES_MAP.entrySet()}">
                            <label>
                                <input type="checkbox" id="SUB_FILTER_CATEGORY${categoryEntry.key}"
                                       name="SUB_FILTER_CATEGORY${categoryEntry.key}" value="enabled"
                                    <c:set var="categoryId" value="SUB_FILTER_CATEGORY${categoryEntry.key}"
                                           scope="page"/>
                                       <c:if test='${param.get(categoryId)== "enabled"}'>checked="checked"</c:if>
                                >${categoryEntry.value}
                            </label>
                            <br>
                        </c:forEach>
                    </div>
                </div>

                <h3>${DICTIONARY_BUNDLE.get("STATUSES")}</h3>
                <div class="form-group">
                    <div id="statuses" class="checkbox">
                        <c:forEach var="statusEntry" items="${STATUSES_MAP.entrySet()}">
                            <label>
                                <input type="checkbox" id="SUB_FILTER_STATUS${statusEntry.key}"
                                       name="SUB_FILTER_STATUS${statusEntry.key}" value="enabled"
                                    <c:set var="statusId" value="SUB_FILTER_STATUS${statusEntry.key}"
                                           scope="page"/>
                                       <c:if test='${param.get(statusId)== "enabled"}'>checked="checked"</c:if>
                                >${statusEntry.value}
                            </label>
                            <br>
                        </c:forEach>
                    </div>
                </div>

                <h3>${DICTIONARY_BUNDLE.get("ACTIVITIES")}</h3>
                <div class="form-group">
                    <div class="checkbox">
                        <c:forEach var="activityEntry" items="${ACTIVITIES_MAP.entrySet()}">
                            <label>
                                <input type="checkbox" name="SUB_FILTER_ACTIVITY${activityEntry.key}" value="enabled"
                                    <c:set var="activityId" value="SUB_FILTER_ACTIVITY${activityEntry.key}"
                                           scope="page"/>
                                       <c:if test='${param.get(activityId)== "enabled"}'>checked="checked"</c:if>
                                >${activityEntry.value}
                            </label>
                            <br>
                        </c:forEach>
                    </div>
                </div>

                <div class="form-group">
                    <h3>${DICTIONARY_BUNDLE.get("ORDER_BY")}:</h3>
                    <select name="SUB_FILTER_ORDER_BY">
                        <c:choose>
                            <c:when test="${'taskId'.equals(param.SUB_FILTER_ORDER_BY)}">
                                <option selected value="taskId">id</option>
                            </c:when>
                            <c:otherwise>
                                <option value="taskId">id</option>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${'priority'.equals(param.SUB_FILTER_ORDER_BY)}">
                                <option selected value="priority">${DICTIONARY_BUNDLE.get("PRIORITY")}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="priority">${DICTIONARY_BUNDLE.get("PRIORITY")}</option>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${'deadline'.equals(param.SUB_FILTER_ORDER_BY)}">
                                <option selected value="deadline">${DICTIONARY_BUNDLE.get("DEADLINE")}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="deadline">${DICTIONARY_BUNDLE.get("DEADLINE")}</option>
                            </c:otherwise>
                        </c:choose>
                    </select>
                </div>

                <div class="form-group">
                    <button onclick="return setCommand('DISPLAY_USER')"
                            class="btn btn-primary">${DICTIONARY_BUNDLE.get("SET_FILTERS")}</button>
                </div>
            </div>

            <div class="col-sm-7 col-sm-offset-2 col-md-7 col-md-offset-2 main">
                <div class="col-md-2">
                    <h4>${DICTIONARY_BUNDLE.get("TASKS")}, ${TASKS_LIST.size()}</h4>
                </div>

                <div class="col-md-10">
                    <button onclick="return setCheckedSubFilter('categories','SUB_FILTER_CATEGORY0')"
                            class="btn btn-primary">${DICTIONARY_BUNDLE.get('INBOX')}
                    </button>

                    <button onclick="return setCheckedSubFilter('statuses', 'SUB_FILTER_STATUS1')"
                            class="btn btn-primary">${DICTIONARY_BUNDLE.get('COMPLETED')}
                    </button>

                    <button onclick="return setCheckedSubFilter('statuses', 'SUB_FILTER_STATUS0')"
                            class="btn btn-primary">${DICTIONARY_BUNDLE.get('UNCOMPLETED')}
                    </button>
                </div>

                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>
                            <small>id</small>
                        </th>
                        <th>
                            <small>${DICTIONARY_BUNDLE.get('CATEGORY')}</small>
                        </th>
                        <th>
                            <small>${DICTIONARY_BUNDLE.get("NAME")}</small>
                        </th>
                        <th>
                            <small>${DICTIONARY_BUNDLE.get("PRIORITY")}</small>
                        </th>
                        <th>
                            <small>${DICTIONARY_BUNDLE.get('STATUS')}</small>
                        </th>
                        <th>
                            <small>${DICTIONARY_BUNDLE.get('ACTIVITY')}</small>
                        </th>
                        <th>
                            <small>${DICTIONARY_BUNDLE.get('DEADLINE')}</small>
                        </th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:forEach var="task" items="${TASKS_LIST}">
                        <tr>
                            <td>
                                <div class="table-link">
                                    <a href="" onclick="return setCommandAndId('DISPLAY_USER',${task.taskId})">
                                        <small>${task.taskId}</small>
                                    </a>
                                </div>
                            </td>
                            <td>
                                <small>${CATEGORIES_MAP.get(Long.valueOf(task.categoryId))}</small>
                            </td>
                            <td>
                                <div class="table-link">
                                    <a href="" onclick="return setCommandAndId('DISPLAY_USER',${task.taskId})">
                                        <small>${task.name}</small>
                                    </a>
                                </div>
                            </td>
                            <td>
                                <small>${task.priority}</small>
                            </td>
                            <td>
                                <small>${STATUSES_MAP.get(Long.valueOf(task.statusId))}</small>
                            </td>
                            <td>
                                <small>${ACTIVITIES_MAP.get(Long.valueOf(task.activityId))}</small>
                            </td>
                            <td>
                                <small>${task.deadline}</small>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="col-sm-offset-9 col-sm-3 col-md-offset-9 col-md-3 right-sidebar">
                <h3>${DICTIONARY_BUNDLE.get("DETAILS")}

                    <button onclick="return setCommand('DELETE_TASK')" class="btn btn-lg btn-default"
                            style="font-size: large">
                        <span class="glyphicon glyphicon-trash"></span>
                    </button>

                </h3>
                <h4>id: ${param.CHOSEN_ELEMENT_ID}</h4>

                <div class="form-group">
                    ${DICTIONARY_BUNDLE.get("DEADLINE")}:
                    <input type="date" name="CURRENT_TASK_DEADLINE" value="${CURRENT_TASK.deadline}">
                </div>

                <div class="form-group">
                    ${DICTIONARY_BUNDLE.get("NAME")}:
                    <%--<input type="text" height="100" size="50" name="CURRENT_TASK_NAME"--%>
                    <input type="text" class="input-text" name="CURRENT_TASK_NAME"
                           value="${CURRENT_TASK.name}">
                </div>

                <div class="form-group">
                    ${DICTIONARY_BUNDLE.get("CONTEXT")}:
                    <textarea class="form-control" cols="40" rows="5" id="CURRENT_TASK_CONTENT"
                              name="CURRENT_TASK_CONTENT">${CURRENT_TASK.content}
                    </textarea>
                </div>

                <div class="form-group">
                    <table>
                        <tr>
                            <td>
                                ${DICTIONARY_BUNDLE.get("CATEGORY")}:
                            </td>
                            <td>
                                <select name="CURRENT_TASK_CATEGORY">
                                    <c:forEach var="categoryEntry" items="${CATEGORIES_MAP.entrySet()}">
                                        <c:choose>
                                            <c:when test="${categoryEntry.key==CURRENT_TASK.categoryId}">
                                                <option selected
                                                        value="${categoryEntry.key}">${categoryEntry.value}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${categoryEntry.key}">${categoryEntry.value}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                ${DICTIONARY_BUNDLE.get("STATUS")}:
                            </td>
                            <td>
                                <select name="CURRENT_TASK_STATUS">
                                    <c:forEach var="statusEntry" items="${STATUSES_MAP.entrySet()}">
                                        <c:choose>
                                            <c:when test="${statusEntry.key==CURRENT_TASK.statusId}">
                                                <option selected
                                                        value="${statusEntry.key}">${statusEntry.value}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${statusEntry.key}">${statusEntry.value}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                ${DICTIONARY_BUNDLE.get("ACTIVITY")}:
                            </td>
                            <td>
                                <select name="CURRENT_TASK_ACTIVITY">
                                    <c:forEach var="activityEntry" items="${ACTIVITIES_MAP.entrySet()}">
                                        <c:choose>
                                            <c:when test="${activityEntry.key==CURRENT_TASK.activityId}">
                                                <option selected
                                                        value="${activityEntry.key}">${activityEntry.value}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${activityEntry.key}">${activityEntry.value}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                ${DICTIONARY_BUNDLE.get("PRIORITY")}:
                            </td>
                            <td>
                                <select name="CURRENT_TASK_PRIORITY">
                                    <c:forEach var="i" begin="1" end="5">
                                        <c:choose>
                                            <c:when test="${i==CURRENT_TASK.priority}">
                                                <option selected value="${i}">${i}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${i}">${i}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                    </table>
                </div>

                <div class="form-group">
                    <button onclick="return setCommand('ADD_TASK')" class="btn btn-success">
                        <span class="glyphicon glyphicon-plus"></span>
                        ${DICTIONARY_BUNDLE.get("ADD")}
                    </button>

                    <button onclick="return setCommand('UPDATE_TASK')" class="btn btn-primary">
                        <span class="glyphicon glyphicon-ok"></span>
                        ${DICTIONARY_BUNDLE.get("UPDATE")}
                    </button>
                </div>
            </div>
        </div>
    </div>
</form>
</body>
</html>