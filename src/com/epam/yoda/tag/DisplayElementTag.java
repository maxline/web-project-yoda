package com.epam.yoda.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.Map;

import static com.epam.yoda.config.enums.ECommand.DISPLAY_ADMIN;

/**
 * @author Sergey Mikhluk.
 */
public class DisplayElementTag extends SimpleTagSupport {
    private String columnName;
    private Map<String, String> elementMap;

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();

        out.print("<div class='table-responsive'>");
        out.print("<table class='nav table table-striped'>");
        out.print("<thead>");
        out.print("<tr><th><small>id</small></th>");
        out.print("<th><small>" + columnName + "</small></th></tr>");
        out.print("</thead>");
        out.print("<tbody>");

        for (Map.Entry entry : elementMap.entrySet()) {
            out.print("<tr><td><small>" + entry.getKey() + "</small></td>");
            out.print("<td><div class='table-link'>");
            out.print("<a href='' onclick=\"return setCommandAndId('" + DISPLAY_ADMIN.name() + "', " + entry.getKey() + ")\">");
            out.print("<small>" + entry.getValue() + "</small>");
            out.print("</a>");
            out.print("</div></td></tr>");
        }

        out.print("</tbody>");
        out.print("</table>");
        out.print("</div>");
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public void setElementMap(Map<String, String> elementMap) {
        this.elementMap = elementMap;
    }
}
