package com.yoda.model.daohelper;

import com.yoda.config.enums.ESubFilter;
import com.yoda.model.factory.DAOFactory;
import com.yoda.model.idao.IActivityDAO;
import com.yoda.model.idao.ICategoryDAO;
import com.yoda.model.idao.IStatusDAO;
import com.yoda.model.idao.IUserDAO;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

import static com.yoda.config.enums.EPageParameter.*;

/**
 * The Class provides methods for setting filter and order which used by TaskDAO
 * and helps generate database query for receiving list of task.
 *
 * @author Sergey Mikhluk.
 */
public class QueryFilter {

    private static final Logger logger = Logger.getLogger(QueryFilter.class.getName());
    private static volatile QueryFilter instance;

    private static final String DEFAULT_ORDER = "taskId";
    private static final String ENABLED = "enabled";
    private static final int SUB_FILTER_INITIAL_CAPACITY = 8;

    private final Set<Long> categoryFilter = new HashSet<>(SUB_FILTER_INITIAL_CAPACITY);
    private final Set<Long> activityFilter = new HashSet<>(SUB_FILTER_INITIAL_CAPACITY);
    private final Set<Long> statusFilter = new HashSet<>(SUB_FILTER_INITIAL_CAPACITY);
    private final Set<Long> userFilter = new HashSet<>(SUB_FILTER_INITIAL_CAPACITY);
    private String taskNameFilter;
    private String orderBy;

    private final ICategoryDAO categoryDAO;
    private final IActivityDAO activityDAO;
    private final IStatusDAO statusDAO;
    private final IUserDAO userDAO;

    private QueryFilter() {
        categoryDAO = DAOFactory.createCategoryDAO();
        activityDAO = DAOFactory.createActivityDAO();
        statusDAO = DAOFactory.createStatusDAO();
        userDAO = DAOFactory.createUserDAO();
    }

    public static QueryFilter getInstance() {
        QueryFilter localInstance = instance;
        if (localInstance == null) {
            synchronized (QueryFilter.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new QueryFilter();
                }
            }
        }
        return localInstance;
    }

    public String getQueryStringFromSubFilter(ESubFilter eSubFilter) {
        switch (eSubFilter) {
            case TASK_NAME:
                return taskNameFilter == null ? "%%" : "%" + taskNameFilter + "%";
            case ORDER_BY:
                return orderBy == null ? DEFAULT_ORDER : orderBy;
            default:
                throw new IllegalArgumentException();
        }
    }

    public Set<Long> getIdsFromSubFilter(ESubFilter eSubFilter) {
        switch (eSubFilter) {
            case CATEGORY:
                return (categoryFilter.isEmpty()) ? categoryDAO.findAll().keySet() : categoryFilter;
            case STATUS:
                return (statusFilter.isEmpty()) ? statusDAO.findAll().keySet() : statusFilter;
            case ACTIVITY:
                return (activityFilter.isEmpty()) ? activityDAO.findAll().keySet() : activityFilter;
            case USER:
                return (userFilter.isEmpty()) ? null : userFilter;
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * The method transform the checked checkboxes on the mainUser page (categories, statuses, activities)
     * to the list of ids (categories, statuses, activities) that will be used in prepared statement query
     * IN clause: WHERE statusId IN(?,?,?,?,?,?), categoryId IN(?,?), activityId IN(?,?,?)
     */
    public void setFiltersFromPage(HttpServletRequest request) {
        categoryFilter.clear();
        statusFilter.clear();
        activityFilter.clear();
        userFilter.clear();
        taskNameFilter = null;
        orderBy = null;
        String enabled;

        for (long id : categoryDAO.findAll().keySet()) {
            enabled = request.getParameter(SUB_FILTER_CATEGORY.name() + id);
            if (ENABLED.equals(enabled)) {
                categoryFilter.add(id);
            }
        }

        for (long id : statusDAO.findAll().keySet()) {
            enabled = request.getParameter(SUB_FILTER_STATUS.name() + id);
            if (ENABLED.equals(enabled)) {
                statusFilter.add(id);
            }
        }

        for (long id : activityDAO.findAll().keySet()) {
            enabled = request.getParameter(SUB_FILTER_ACTIVITY.name() + id);
            if (ENABLED.equals(enabled)) {
                activityFilter.add(id);
            }
        }

        for (long id : userDAO.findAllAsMap().keySet()) {
            enabled = request.getParameter(SUB_FILTER_USER.name() + id);
            if (ENABLED.equals(enabled)) {
                userFilter.add(id);
            }
        }

        setTaskNameFilter(request.getParameter(SUB_FILTER_TASK_NAME.name()));
        setOrderBy(request.getParameter(SUB_FILTER_ORDER_BY.name()));
    }

    private void setTaskNameFilter(String taskNameFilter) {
        this.taskNameFilter = (taskNameFilter == null) ? "" : taskNameFilter;
    }

    private void setOrderBy(String orderBy) {
        this.orderBy = (orderBy == null) ? DEFAULT_ORDER : orderBy;
    }
}