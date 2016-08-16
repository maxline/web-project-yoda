<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" uri="http://yoda.com/yoda/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="col-md-7 col-md-offset-2 main">
    <h2 class="sub-header"> ${DICTIONARY_BUNDLE.get("ACTIVITIES")} </h2>
    <tags:displayElementTag columnName="${DICTIONARY_BUNDLE.get('NAME')}" elementMap="${ACTIVITIES_MAP}"/>
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
            ${DICTIONARY_BUNDLE.get("NAME")}:
            <input type="text" class="input-text" name="CURRENT_ELEMENT_NAME" value="${CURRENT_ELEMENT.name}">
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