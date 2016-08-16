package com.epam.yoda.model.daohelper;

import com.epam.yoda.config.enums.ESubFilter;
import com.epam.yoda.model.factory.DAOFactory;
import com.epam.yoda.model.idao.IActivityDAO;
import com.epam.yoda.model.idao.ICategoryDAO;
import com.epam.yoda.model.idao.IStatusDAO;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

import static com.epam.yoda.config.enums.EPageParameter.*;

/**
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
    private String taskNameFilter;
    private String orderBy;

    private final ICategoryDAO categoryDAO;
    private final IActivityDAO activityDAO;
    private final IStatusDAO statusDAO;

    private QueryFilter() {
        categoryDAO = DAOFactory.createCategoryDAO();
        activityDAO = DAOFactory.createActivityDAO();
        statusDAO = DAOFactory.createStatusDAO();
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

    //    /**
//     * Возвращает строку вида (0,1,..,n) для подстановки фильтра в запросе
//     * WHERE statusId IN(...), categoryId IN(...), activityId IN(...)
//     * Если ни один фильтр не задан (галочка не включена), то считается что включены все галочки.
//     *
//     * @param idsFromPageSubFilter Фильтр из формы
//     * @param idsFromDAO           когда фильтр пустой берем все ИД просто из ДАО
//     * @return
//     */
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
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Пример: для категорий FilterN [0,1,6], где в скобках id категорий которые попадут в select выборку фильтра при получении TaskDAO.getAsList()
     *
     * @param eSubFilters одна из таблиц по которой можно фильтровать например category, status, activity
     * @param subFilterId id элемента по которому отработает фильтр например для category (id=1, name= работа)
     * @param enabled   если "enabled" включаем subFilterId в фильтр
     */

    /**
     * index - порядковые номер статуса (0 - NEW, 1 - CLOSED ...)
     * value - 1 включен фильтр для данного статуса, 0 выключен
     */
    public void setFiltersFromPage(HttpServletRequest request) {
        categoryFilter.clear();
        statusFilter.clear();
        activityFilter.clear();
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