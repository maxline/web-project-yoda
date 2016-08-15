package com.epam.yoda.control.command;

import com.epam.yoda.config.manager.ConfigManager;
import com.epam.yoda.config.manager.DictionaryManager;
import com.epam.yoda.model.factory.DAOFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.yoda.config.enums.EConfigKey.PAGE_MAIN_ADMIN;
import static com.epam.yoda.config.enums.EDictionaryKey.ELEMENT_DELETED;
import static com.epam.yoda.config.enums.EDictionaryKey.ELEMENT_NOT_DELETED;
import static com.epam.yoda.config.enums.EPageAttribute.MESSAGE;
import static com.epam.yoda.config.enums.EPageParameter.CHOSEN_ELEMENT_ID;
import static com.epam.yoda.config.enums.EPageParameter.CHOSEN_ITEM;

/**
 * @author Sergey Mikhluk.
 */
public class DeleteElementCommand implements ICommand {

    private static final Logger logger = Logger.getLogger(DeleteElementCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long currentElementId;

        try {
            logger.debug("execute() request.getParameter(CHOSEN_ELEMENT_ID.name()) = " + request.getParameter(CHOSEN_ELEMENT_ID.name()));
            currentElementId = Long.parseLong(request.getParameter(CHOSEN_ELEMENT_ID.name()));
        } catch (NumberFormatException e) {
            request.getSession().setAttribute(MESSAGE.name(), DictionaryManager.getInstance().getProperty(ELEMENT_NOT_DELETED));
            logger.error("execute() " + e);
            return ConfigManager.getInstance().getProperty(PAGE_MAIN_ADMIN);
        }

        String chosenItem = request.getParameter(CHOSEN_ITEM.name());
        logger.debug("execute() getParameter(CHOSEN_ITEM.name()) = " + chosenItem);
        boolean deleteSuccess;

        switch (chosenItem) {
            case "CATEGORY":
                deleteSuccess = DAOFactory.createCategoryDAO().delete(currentElementId);
                break;
            case "STATUS":
                deleteSuccess = DAOFactory.createStatusDAO().delete(currentElementId);
                break;
            case "ACTIVITY":
                deleteSuccess = DAOFactory.createActivityDAO().delete(currentElementId);
                break;
            case "USER":
                deleteSuccess = DAOFactory.createUserDAO().delete(currentElementId);
                break;
            default:
                throw new IllegalArgumentException();
        }

        if (deleteSuccess) {
            request.getSession().setAttribute(MESSAGE.name(), DictionaryManager.getInstance().getProperty(ELEMENT_DELETED));
        } else {
            request.getSession().setAttribute(MESSAGE.name(), DictionaryManager.getInstance().getProperty(ELEMENT_NOT_DELETED));
        }
        return ConfigManager.getInstance().getProperty(PAGE_MAIN_ADMIN);
    }
}
