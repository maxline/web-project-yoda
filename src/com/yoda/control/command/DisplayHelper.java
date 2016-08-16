package com.yoda.control.command;

import com.yoda.config.manager.ConfigManager;
import com.yoda.config.manager.DictionaryManager;
import com.yoda.model.daohelper.QueryFilter;
import com.yoda.model.entities.Activity;
import com.yoda.model.entities.Category;
import com.yoda.model.entities.Status;
import com.yoda.model.entities.User;
import com.yoda.model.factory.DAOFactory;
import com.yoda.model.idao.*;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import static com.yoda.config.enums.EConfigKey.*;
import static com.yoda.config.enums.ELocale.en_US;
import static com.yoda.config.enums.EPageAttribute.*;
import static com.yoda.config.enums.EPageParameter.CHOSEN_ELEMENT_ID;
import static com.yoda.config.enums.EPageParameter.CHOSEN_ITEM;

/**
 * The Class provides methods that deliver all needed data for jsp pages
 * (mainUser.jsp, mainAdmin.jsp).
 *
 * Also the Class provides method that define which page will be shown based on
 * information if user is logged in or not, and the type of user (ordinary user or admin).
 *
 * @author Sergey Mikhluk.
 */
public class DisplayHelper {

    private static final Logger logger = Logger.getLogger(DisplayHelper.class.getName());
    private static volatile DisplayHelper instance = null;

    private final ITaskDAO taskDAO;
    private final ICategoryDAO categoryDAO;
    private final IActivityDAO activityDAO;
    private final IStatusDAO statusDAO;
    private final IUserDAO userDAO;
    private final IUserTypeDAO userTypeDAO;
    private final QueryFilter queryFilter;

    private static final int ADMIN_USER_TYPE_ID = 1;

    private DisplayHelper() {
        taskDAO = DAOFactory.createTaskDAO();
        categoryDAO = DAOFactory.createCategoryDAO();
        activityDAO = DAOFactory.createActivityDAO();
        statusDAO = DAOFactory.createStatusDAO();
        userDAO = DAOFactory.createUserDAO();
        userTypeDAO = DAOFactory.createUserTypeDAO();
        queryFilter = QueryFilter.getInstance();
    }

    private boolean isUserLoggedIn(HttpServletRequest request) {
        logger.debug("isUserLoggedIn() getSession().getAttribute(USER.name()) = " + request.getSession().getAttribute(USER.name()));
        if (request.getSession().getAttribute(USER.name()) != null) {
            return true;
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(USER.name())) {
                    logger.debug("isUserLoggedIn() cookie.getValue() = " + cookie.getValue());
                    User user = userDAO.findByLogin(cookie.getValue());
                    request.getSession().setAttribute(USER.name(), user);
                    return true;
                }
            }
        }
        return false;
    }

    String definePage(HttpServletRequest request) {
        ConfigManager config = ConfigManager.getInstance();

        if (isUserLoggedIn(request)) {
            User user = (User) request.getSession().getAttribute(USER.name());
            if (user.getUserTypeId() == ADMIN_USER_TYPE_ID) {
                return config.getProperty(PAGE_MAIN_ADMIN);
            } else {
                return config.getProperty(PAGE_MAIN_USER);
            }
        } else {
            return config.getProperty(PAGE_LOGIN);
        }
    }

    public static DisplayHelper getInstance() {
        DisplayHelper localInstance = instance;
        if (localInstance == null) {
            synchronized (DisplayHelper.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DisplayHelper();
                }
            }
        }
        return localInstance;
    }

    private void setCommonAttributes(HttpServletRequest request) {
        if (request.getSession().getAttribute(DICTIONARY_BUNDLE.name()) == null) {
            request.getSession().setAttribute(DICTIONARY_BUNDLE.name(), DictionaryManager.getInstance().getBundleData(en_US));
        }
        request.getSession().setAttribute(CATEGORIES_MAP.name(), categoryDAO.findAll());
        request.getSession().setAttribute(ACTIVITIES_MAP.name(), activityDAO.findAll());
        request.getSession().setAttribute(STATUSES_MAP.name(), statusDAO.findAll());
    }

    public void prepareUserPageData(HttpServletRequest request) {
        setCommonAttributes(request);

        queryFilter.setFiltersFromPage(request);
        request.getSession().setAttribute(TASKS_LIST.name(), taskDAO.findByFilter(queryFilter));

        String chosenTaskId = request.getParameter(CHOSEN_ELEMENT_ID.name());
        logger.debug("prepareUserPageData() getParameter(CHOSEN_ELEMENT_ID.name()) = " + chosenTaskId);
        if (chosenTaskId != null && !chosenTaskId.equals("")) {
            request.getSession().setAttribute(CURRENT_TASK.name(), taskDAO.findById(Long.parseLong(chosenTaskId)));
        }
    }

    public void prepareAdminPageData(HttpServletRequest request) {
        setCommonAttributes(request);

        request.getSession().setAttribute(USER_TYPES_MAP.name(), userTypeDAO.findAll());
        request.getSession().setAttribute(USERS_LIST.name(), userDAO.findAll());

        String chosenItem = request.getParameter(CHOSEN_ITEM.name());
        logger.debug("prepareAdminPageData() getParameter(CHOSEN_ITEM.name()) = " + chosenItem);
        if (chosenItem == null || chosenItem.equals("")) {
            return;
        }

        try {
            Long chosenElementId = Long.parseLong(request.getParameter(CHOSEN_ELEMENT_ID.name()));
            logger.debug("prepareAdminPageData() getParameter(CHOSEN_ELEMENT_ID.name()) = " + chosenElementId);
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