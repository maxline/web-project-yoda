package com.epam.yoda.control.command;

import com.epam.yoda.config.enums.EDictionaryKey;
import com.epam.yoda.config.manager.DictionaryManager;
import com.epam.yoda.model.entities.User;
import com.epam.yoda.model.factory.DAOFactory;
import com.epam.yoda.model.idao.IUserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.yoda.config.enums.EConfigKey.*;
import static com.epam.yoda.config.enums.EPageAttribute.MESSAGE_ERROR;
import static com.epam.yoda.config.enums.EPageAttribute.USER;
import static com.epam.yoda.config.manager.ConfigManager.getInstance;

/**
 * @author Sergey Mikhluk.
 */
public class LoginCommand implements ICommand {
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final int COOKIE_EXPIRY_SECONDS = 30 * 60;
    public static final int ADMIN_USER_TYPE_ID = 1;
    private final IUserDAO userDAO = DAOFactory.createUserDAO();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page;
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);

        User user = userDAO.find(login, password);
        if (user != null) {
            user.setPassword("");

            request.getSession().setAttribute(USER.name(), user);
            Cookie cookie = new Cookie(USER.name(), login);
            cookie.setMaxAge(COOKIE_EXPIRY_SECONDS);
            response.addCookie(cookie);
            if (user.getUserTypeId() == ADMIN_USER_TYPE_ID) {
                page = getInstance().getProperty(PAGE_MAIN_ADMIN);
            } else {
                page = getInstance().getProperty(PAGE_MAIN_USER);
            }
        } else {
            request.getSession().setAttribute(MESSAGE_ERROR.name(), DictionaryManager.getInstance().getProperty(EDictionaryKey.LOGIN_ERROR));
            page = getInstance().getProperty(PAGE_ERROR);
        }
        return page;
    }
}
