package com.epam.yoda.control.command;

import com.epam.yoda.config.enums.EPageAttribute;
import com.epam.yoda.config.manager.ConfigManager;
import com.epam.yoda.config.manager.DictionaryManager;
import com.epam.yoda.model.entities.Activity;
import com.epam.yoda.model.entities.Category;
import com.epam.yoda.model.entities.Status;
import com.epam.yoda.model.entities.User;
import com.epam.yoda.model.factory.DAOFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.yoda.config.enums.EConfigKey.PAGE_MAIN_ADMIN;
import static com.epam.yoda.config.enums.EDictionaryKey.ELEMENT_ADDED;
import static com.epam.yoda.config.enums.EDictionaryKey.ELEMENT_NOT_ADDED;
import static com.epam.yoda.config.enums.EPageParameter.*;

/**
 * @author Sergey Mikhluk.
 */
public class AddElementCommand implements ICommand {

    private static final Logger logger = Logger.getLogger(AddElementCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String chosenItem = request.getParameter(CHOSEN_ITEM.name());
        logger.debug("execute() getParameter(CHOSEN_ITEM.name()) = " + chosenItem);
        boolean addSuccess;

        switch (chosenItem) {
            case "CATEGORY":
                addSuccess = DAOFactory.createCategoryDAO().add(
                        new Category(-1L, request.getParameter(CURRENT_ELEMENT_NAME.name())));
                break;
            case "STATUS":
                addSuccess = DAOFactory.createStatusDAO().add(
                        new Status(-1L, request.getParameter(CURRENT_ELEMENT_NAME.name())));
                break;
            case "ACTIVITY":
                addSuccess = DAOFactory.createActivityDAO().add(
                        new Activity(-1L, request.getParameter(CURRENT_ELEMENT_NAME.name())));
                break;
            case "USER":
                logger.debug("execute() getParameter(CURRENT_USER_LOGIN.name()) = " + request.getParameter(CURRENT_USER_LOGIN.name()));
                logger.debug("execute() getParameter(CURRENT_USER_PASSWORD.name()) = " + request.getParameter(CURRENT_USER_PASSWORD.name()));
                logger.debug("execute() getParameter(CURRENT_USER_TYPE.name()) = " + request.getParameter(CURRENT_USER_TYPE.name()));
                addSuccess = DAOFactory.createUserDAO().add(
                        new User(-1L, request.getParameter(CURRENT_USER_LOGIN.name()),
                                request.getParameter(CURRENT_USER_PASSWORD.name()),
                                request.getParameter(CURRENT_USER_TYPE.name())));
                break;
            default:
                throw new IllegalArgumentException();
        }

        if (addSuccess) {
            request.getSession().setAttribute(EPageAttribute.MESSAGE.name(), DictionaryManager.getInstance().getProperty(ELEMENT_ADDED));
        } else {
            request.getSession().setAttribute(EPageAttribute.MESSAGE.name(), DictionaryManager.getInstance().getProperty(ELEMENT_NOT_ADDED));
        }
        return ConfigManager.getInstance().getProperty(PAGE_MAIN_ADMIN);
    }
}
