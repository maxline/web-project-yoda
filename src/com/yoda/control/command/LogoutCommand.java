package com.yoda.control.command;

import com.yoda.config.manager.ConfigManager;
import com.yoda.control.icommand.ICommand;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.yoda.config.enums.EConfigKey.PAGE_LOGIN;
import static com.yoda.config.enums.EPageAttribute.USER;

/**
 * @author Sergey Mikhluk.
 */
public class LogoutCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().removeAttribute(USER.name());
        request.getSession().invalidate();

        Cookie cookie = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie element : cookies) {
                if (element.getName().equals(USER.name())) {
                    cookie = element;
                    break;
                }
            }
        }
        if (cookie != null) {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
        return ConfigManager.getInstance().getProperty(PAGE_LOGIN);
    }
}
