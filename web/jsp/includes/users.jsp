<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="col-md-7 col-md-offset-2 main">
    <h2 class="sub-header"> ${DICTIONARY_BUNDLE.get("USERS")} </h2>
    <div class="table-responsive">
        <table class="nav table table-striped">
            <thead>
            <tr>
                <th>
                    <small>id</small>
                </th>
                <th>
                    <small>${DICTIONARY_BUNDLE.get("LOGIN")}</small>
                </th>
                <th>
                    <small>${DICTIONARY_BUNDLE.get("PASSWORD")}</small>
                </th>
                <th>
                    <small>${DICTIONARY_BUNDLE.get("USER_TYPE")}</small>
                </th>
            </tr>
            </thead>

            <tbody>
            <c:forEach var="user" items="${USERS_LIST}">
                <tr>
                    <td>
                        <small>${user.userId}</small>
                    </td>
                    <td>
                        <div class="table-link">
                            <a href="" onclick="return setCommandAndId('DISPLAY_ADMIN',${user.userId})">
                                <small>${user.login}</small>
                            </a>
                        </div>
                    </td>
                    <td>
                        <small>${user.password}</small>
                    </td>
                    <td>
                        <small>${USER_TYPES_MAP.get(Long.valueOf(user.userTypeId))}</small>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<div class="col-md-3 right-sidebar">
    <form class="form-horizontal" role="form" method="post" accept-charset="UTF-8">
        <h3>${DICTIONARY_BUNDLE.get("DETAILS")}
            <button onclick="return setCommand('DELETE_ELEMENT')" class="btn btn-lg btn-default"
                    style="font-size: large">
                <span class="glyphicon glyphicon-trash"></span>
            </button>
        </h3>

        <h4>id = ${param.CHOSEN_ELEMENT_ID}</h4>

        <div class="form-group">
            ${DICTIONARY_BUNDLE.get("LOGIN")}:
            <input type="text" class="input-text" name="CURRENT_USER_LOGIN" value="${CURRENT_ELEMENT.login}">

            ${DICTIONARY_BUNDLE.get("PASSWORD")}:
            <input type="password" class="input-text" name="CURRENT_USER_PASSWORD" value="${CURRENT_ELEMENT.password}">

            ${DICTIONARY_BUNDLE.get("USER_TYPE")}:
            <select name="CURRENT_USER_TYPE">
                <c:forEach var="userTypeEntry" items="${USER_TYPES_MAP.entrySet()}">
                    <c:choose>
                        <c:when test="${userTypeEntry.key==CURRENT_ELEMENT.userTypeId}">
                            <option selected
                                    value="${userTypeEntry.key}">${userTypeEntry.value}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${userTypeEntry.key}">${userTypeEntry.value}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <button onclick="return setCommand('ADD_ELEMENT')" class="btn btn-success">
                <span class="glyphicon glyphicon-plus"></span>
                ${DICTIONARY_BUNDLE.get("ADD")}
            </button>

            <button onclick="return setCommand('UPDATE_ELEMENT')" class="btn btn-primary">
                <span class="glyphicon glyphicon-ok"></span>
                ${DICTIONARY_BUNDLE.get("UPDATE")}
            </button>
        </div>
    </form>
</div>