package com.epam.yoda.control.command;

import com.epam.yoda.config.manager.DictionaryManager;
import com.epam.yoda.model.entities.Activity;
import com.epam.yoda.model.entities.Category;
import com.epam.yoda.model.entities.Status;
import com.epam.yoda.model.entities.User;
import com.epam.yoda.model.factory.DAOFactory;
import com.epam.yoda.model.idao.*;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.epam.yoda.config.enums.ELocale.EN;
import static com.epam.yoda.config.enums.EPageAttribute.*;
import static com.epam.yoda.config.enums.EPageParameter.CHOSEN_ELEMENT_ID;
import static com.epam.yoda.config.enums.EPageParameter.CHOSEN_ITEM;

/**
 * @author Sergey Mikhluk.
 */
public class DisplayAdminHelper {
    private static final Logger logger = Logger.getLogger(DisplayAdminHelper.class.getName());
    private static volatile DisplayAdminHelper instance = null;
    private final ICategoryDAO categoryDAO;
    private final IActivityDAO activityDAO;
    private final IStatusDAO statusDAO;
    private final IUserDAO userDAO;
    private final IUserTypeDAO userTypeDAO;

    private DisplayAdminHelper() {
        categoryDAO = DAOFactory.createCategoryDAO();
        activityDAO = DAOFactory.createActivityDAO();
        statusDAO = DAOFactory.createStatusDAO();
        userDAO = DAOFactory.createUserDAO();
        userTypeDAO = DAOFactory.createUserTypeDAO();
    }

    public static DisplayAdminHelper getInstance() {
        DisplayAdminHelper localInstance = instance;
        if (localInstance == null) {
            synchronized (DisplayAdminHelper.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DisplayAdminHelper();
                }
            }
        }
        return localInstance;
    }

    public void prepareFilteredData(HttpServletRequest request) {
        if (request.getSession().getAttribute(DICTIONARY_BUNDLE.name()) == null) {
            request.getSession().setAttribute(DICTIONARY_BUNDLE.name(), DictionaryManager.getInstance().getBundleData(EN));
        }

        request.getSession().setAttribute(CATEGORIES_MAP.name(), categoryDAO.findAll());
        request.getSession().setAttribute(ACTIVITIES_MAP.name(), activityDAO.findAll());
        request.getSession().setAttribute(STATUSES_MAP.name(), statusDAO.findAll());
        request.getSession().setAttribute(USER_TYPES_MAP.name(), userTypeDAO.findAll());
        request.getSession().setAttribute(USERS_LIST.name(), userDAO.findAll());

        String chosenItem = request.getParameter(CHOSEN_ITEM.name());
        logger.debug("prepareFilteredData() getParameter(CHOSEN_ITEM.name()) = " + chosenItem);

        if (chosenItem == null || chosenItem.equals("")) {
            return;
        }

        try {
            Long chosenElementId = Long.parseLong(request.getParameter(CHOSEN_ELEMENT_ID.name()));
            logger.debug("prepareFilteredData() getParameter(CHOSEN_ELEMENT_ID.name()) = " + chosenElementId);
            switch (chosenItem) {
                case "CATEGORY":
                    request.getSession().setAttribute(CURRENT_ELEMENT.name(), categoryDAO.findById(chosenElementId));
                    break;
                case "STATUS":
                    request.getSession().setAttribute(CURRENT_ELEMENT.name(), statusDAO.findById(chosenElementId));
                    break;
                case "ACTIVITY":
                    request.getSession().setAttribute(CURRENT_ELEMENT.name(), activityDAO.findById(chosenElementId));
                    break;
                case "USER":
                    request.getSession().setAttribute(CURRENT_ELEMENT.name(), userDAO.findById(chosenElementId));
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        } catch (NumberFormatException e) {
            switch (chosenItem) {
                case "CATEGORY":
                    request.getSession().setAttribute(CURRENT_ELEMENT.name(), new Category());
                    break;
                case "STATUS":
                    request.getSession().setAttribute(CURRENT_ELEMENT.name(), new Status());
                    break;
                case "ACTIVITY":
                    request.getSession().setAttribute(CURRENT_ELEMENT.name(), new Activity());
                    break;
                case "USER":
                    request.getSession().setAttribute(CURRENT_ELEMENT.name(), new User());
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }
    }
}