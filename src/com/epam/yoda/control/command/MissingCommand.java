package com.epam.yoda.control.command;

import com.epam.yoda.config.manager.ConfigManager;
import com.epam.yoda.model.entities.User;
import com.epam.yoda.model.factory.DAOFactory;
import com.epam.yoda.model.idao.IUserDAO;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.yoda.config.enums.EConfigKey.PAGE_LOGIN;
import static com.epam.yoda.config.enums.EPageAttribute.USER;

/**
 * @author Sergey Mikhluk.
 */
public class MissingCommand implements ICommand {
    private static final Logger logger = Logger.getLogger(MissingCommand.class.getName());
    private final IUserDAO userDAO = DAOFactory.createUserDAO();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("execute() getSession().getAttribute(USER.name()) = " + request.getSession().getAttribute(USER.name()));

        if (request.getSession().getAttribute(USER.name()) != null) {
            return DefinePageHelper.definePage(request);
        }

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
        if (cookie == null) {
            return ConfigManager.getInstance().getProperty(PAGE_LOGIN);
        }
        User user = userDAO.findByLogin(cookie.getValue());
        if (user == null) {
            return ConfigManager.getInstance().getProperty(PAGE_LOGIN);
        }

        request.getSession().setAttribute(USER.name(), user);
        return DefinePageHelper.definePage(request);
    }
}