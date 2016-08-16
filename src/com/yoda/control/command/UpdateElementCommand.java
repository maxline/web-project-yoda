package com.yoda.control.command;

import com.yoda.config.manager.ConfigManager;
import com.yoda.config.manager.DictionaryManager;
import com.yoda.control.icommand.ICommand;
import com.yoda.model.entities.Activity;
import com.yoda.model.entities.Category;
import com.yoda.model.entities.Status;
import com.yoda.model.entities.User;
import com.yoda.model.factory.DAOFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.yoda.config.enums.EConfigKey.PAGE_MAIN_ADMIN;
import static com.yoda.config.enums.EDictionaryKey.ELEMENT_NOT_UPDATED;
import static com.yoda.config.enums.EDictionaryKey.ELEMENT_UPDATED;
import static com.yoda.config.enums.EPageAttribute.MESSAGE;
import static com.yoda.config.enums.EPageParameter.*;

/**
 * @author Sergey Mikhluk.
 */
public class UpdateElementCommand implements ICommand {

    private static final Logger logger = Logger.getLogger(UpdateElementCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long currentElementId;

        try {
            currentElementId = Long.parseLong(request.getParameter(CHOSEN_ELEMENT_ID.name()));
        } catch (NumberFormatException e) {
            request.getSession().setAttribute(MESSAGE.name(), DictionaryManager.getInstance().getProperty(ELEMENT_NOT_UPDATED));
            logger.error("getParameter(CHOSEN_ELEMENT_ID.name()): " + e);
            return ConfigManager.getInstance().getProperty(PAGE_MAIN_ADMIN);
        }

        String chosenItem = request.getParameter(CHOSEN_ITEM.name());
        logger.debug("execute() getParameter(CHOSEN_ITEM.name()) = " + chosenItem);
        logger.debug("execute() request.getParameter(CURRENT_ELEMENT_NAME.name()):" + request.getParameter(CURRENT_ELEMENT_NAME.name()));

        boolean updateSuccess = false;
        switch (chosenItem) {
            case "CATEGORY":
                updateSuccess = DAOFactory.createCategoryDAO().update(
                        new Category(currentElementId, request.getParameter(CURRENT_ELEMENT_NAME.name())));
                break;
            case "STATUS":
                updateSuccess = DAOFactory.createStatusDAO().update(
                        new Status(currentElementId, request.getParameter(CURRENT_ELEMENT_NAME.name())));
                break;
            case "ACTIVITY":
                updateSuccess = DAOFactory.createActivityDAO().update(
                        new Activity(currentElementId, request.getParameter(CURRENT_ELEMENT_NAME.name())));
                break;
            case "USER":
                updateSuccess = DAOFactory.createUserDAO().update(
                        new User(currentElementId,
                                request.getParameter(CURRENT_USER_LOGIN.name()),
                                request.getParameter(CURRENT_USER_PASSWORD.name()),
                                request.getParameter(CURRENT_USER_TYPE.name())));
                break;
        }

        if (updateSuccess) {
            request.getSession().setAttribute(MESSAGE.name(), DictionaryManager.getInstance().getProperty(ELEMENT_UPDATED));
        } else {
            request.getSession().setAttribute(MESSAGE.name(), DictionaryManager.getInstance().getProperty(ELEMENT_NOT_UPDATED));
        }
        return ConfigManager.getInstance().getProperty(PAGE_MAIN_ADMIN);
    }
}