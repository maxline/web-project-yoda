function setCommand(valueCommand) {
    $('#COMMAND').val(valueCommand);
    $('#generalForm').submit();
    return false;
}

function setCommandAndId(valueCommand, valueId) {
    $('#COMMAND').val(valueCommand);
    $('#CHOSEN_ELEMENT_ID').val(valueId);
    $('#generalForm').submit();
    return false;
}

function setCommandAndItem(valueCommand, valueId) {
    $('#COMMAND').val(valueCommand);
    $('#CHOSEN_ITEM').val(valueId);
    $('#generalForm').submit();
    return false;
}

function setCommandAndLocale(valueCommand, valueLocale) {
    $('#COMMAND').val(valueCommand);
    $('#CHOSEN_LOCALE').val(valueLocale);
    $('#generalForm').submit();
    return false;
}

function setCheckedSubFilter(subFilter, subFilterId) {
    var checkedUnchecked = document.getElementById(subFilterId).checked != true;
    var element = document.getElementById(subFilter);
    var elements = element.getElementsByTagName("input");

    for (var i = 0, len = elements.length; i < len; i++) {
        elements[i].checked = false;
    }
    document.getElementById(subFilterId).checked = checkedUnchecked;
}