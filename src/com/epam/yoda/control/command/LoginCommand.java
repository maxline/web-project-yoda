package com.epam.yoda.control.command;

import com.epam.yoda.config.enums.EDictionaryKey;
import com.epam.yoda.config.manager.ConfigManager;
import com.epam.yoda.control.icommand.ICommand;
import com.epam.yoda.model.entities.User;
import com.epam.yoda.model.factory.DAOFactory;
import com.epam.yoda.model.idao.IUserDAO;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.yoda.config.enums.EConfigKey.COOKIE_EXPIRY_SECONDS;
import static com.epam.yoda.config.enums.EConfigKey.PAGE_LOGIN;
import static com.epam.yoda.config.enums.EPageAttribute.MESSAGE;
import static com.epam.yoda.config.enums.EPageAttribute.USER;
import static com.epam.yoda.config.enums.EPageParameter.*;
import static com.epam.yoda.config.manager.ConfigManager.getInstance;

/**
 * @author Sergey Mikhluk.
 */
public class LoginCommand implements ICommand {
    private static final Logger logger = Logger.getLogger(LoginCommand.class.getName());
    private static final String ENABLED = "enabled";
    private final IUserDAO userDAO = DAOFactory.createUserDAO();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page;
        String login = request.getParameter(LOGIN.name());
        String password = request.getParameter(PASSWORD.name());
        String rememberMe = request.getParameter(REMEMBER_ME.name());

        logger.debug("execute() getParameter(REMEMBER_ME.name()) = " + rememberMe);

        User user = userDAO.find(login, password);
        if (user != null) {
            user.setPassword("");

            request.getSession().setAttribute(USER.name(), user);
            Cookie cookie = new Cookie(USER.name(), login);

            if (ENABLED.equals(rememberMe)) {
                cookie.setMaxAge(Integer.parseInt(ConfigManager.getInstance().getProperty(COOKIE_EXPIRY_SECONDS)));
            } else {
                cookie.setMaxAge(0);
            }
            response.addCookie(cookie);

            page = DisplayHelper.getInstance().definePage(request);
        } else {
            request.getSession().setAttribute(MESSAGE.name(), EDictionaryKey.LOGIN_ERROR.name());
            page = getInstance().getProperty(PAGE_LOGIN);
        }
        return page;
    }
}
