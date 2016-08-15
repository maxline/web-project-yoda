package com.epam.yoda.control.command;

import com.epam.yoda.config.manager.DictionaryManager;
import com.epam.yoda.model.daohelper.QueryFilter;
import com.epam.yoda.model.factory.DAOFactory;
import com.epam.yoda.model.idao.IActivityDAO;
import com.epam.yoda.model.idao.ICategoryDAO;
import com.epam.yoda.model.idao.IStatusDAO;
import com.epam.yoda.model.idao.ITaskDAO;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.epam.yoda.config.enums.ELocale.EN;
import static com.epam.yoda.config.enums.EPageAttribute.*;
import static com.epam.yoda.config.enums.EPageParameter.CHOSEN_ELEMENT_ID;

/**
 * @author Sergey Mikhluk.
 */
public class DisplayUserHelper {

    private static final Logger logger = Logger.getLogger(DisplayUserHelper.class.getName());
    private static volatile DisplayUserHelper instance = null;
    private final ITaskDAO taskDAO;
    private final ICategoryDAO categoryDAO;
    private final IActivityDAO activityDAO;
    private final IStatusDAO statusDAO;
    private final QueryFilter queryFilter;

    private DisplayUserHelper() {
        taskDAO = DAOFactory.createTaskDAO();
        categoryDAO = DAOFactory.createCategoryDAO();
        activityDAO = DAOFactory.createActivityDAO();
        statusDAO = DAOFactory.createStatusDAO();
        queryFilter = QueryFilter.getInstance();
    }

    public static DisplayUserHelper getInstance() {
        DisplayUserHelper localInstance = instance;
        if (localInstance == null) {
            synchronized (DisplayUserHelper.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DisplayUserHelper();
                }
            }
        }
        return localInstance;
    }

    public void prepareFilteredData(HttpServletRequest request) {
        request.getSession().setAttribute(CATEGORIES_MAP.name(), categoryDAO.findAll());
        request.getSession().setAttribute(ACTIVITIES_MAP.name(), activityDAO.findAll());
        request.getSession().setAttribute(STATUSES_MAP.name(), statusDAO.findAll());

        if (request.getSession().getAttribute(DICTIONARY_BUNDLE.name()) == null) {
            request.getSession().setAttribute(DICTIONARY_BUNDLE.name(), DictionaryManager.getInstance().getBundleData(EN));
        }

        queryFilter.setFiltersFromPage(request);
        request.getSession().setAttribute(TASKS_LIST.name(), taskDAO.findByFilter(queryFilter));

        String chosenTaskId = request.getParameter(CHOSEN_ELEMENT_ID.name());
        logger.debug("prepareFilteredData() getParameter(CHOSEN_ELEMENT_ID.name()) = " + chosenTaskId);
        if (chosenTaskId != null && !chosenTaskId.equals("")) {
            request.getSession().setAttribute(CURRENT_TASK.name(), taskDAO.findById(Long.parseLong(chosenTaskId)));
        }
    }
}