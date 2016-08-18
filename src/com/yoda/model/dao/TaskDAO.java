package com.yoda.model.dao;

import com.yoda.model.daohelper.QueryFilter;
import com.yoda.model.entities.Task;
import com.yoda.model.idao.ITaskDAO;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.yoda.config.enums.ESubFilter.*;

/**
 * @author Sergey Mikhluk.
 */
public class TaskDAO extends DAOBase implements ITaskDAO {

    private static final Logger logger = Logger.getLogger(TaskDAO.class.getName());
    private static volatile TaskDAO instance;
    private static final int INITIAL_CAPACITY = 20;
    //language=MySQL
    private static final String SELECT_ALL_TASKS = "SELECT * FROM task ORDER BY taskId";
    //language=MySQL
    private static final String SELECT_TASK_BY_ID = "SELECT * FROM task WHERE taskId=?";
    //language=MySQL
    private static final String INSERT_TASK = "INSERT INTO task" +
            "(name, content, deadline, categoryId, activityId, priority, statusId, userId) VALUES(?,?,?,?,?,?,?,?)";
    //language=MySQL
    private static final String UPDATE_TASK = "UPDATE task " +
            "SET name=?, content=?, deadline=?, categoryId=?, activityId=?, priority=?, statusId=?, userId=? WHERE taskId=?";
    //language=MySQL
    private static final String DELETE_TASK = "DELETE FROM task WHERE taskId = ?";

    public static TaskDAO getInstance() {
        TaskDAO localInstance = instance;
        if (localInstance == null) {
            synchronized (TaskDAO.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new TaskDAO();
                }
            }
        }
        return localInstance;
    }


    private Task getTask(ResultSet rs) throws SQLException {
        return new Task(
                rs.getLong("taskId"),
                rs.getString("name"),
                rs.getString("content"),
                rs.getInt("categoryId"),
                rs.getInt("activityId"),
                rs.getInt("priority"),
                rs.getInt("statusId"),
                rs.getInt("userId"),
                rs.getDate("deadline")
        );
    }

    @Override
    public List<Task> findAll() {
        logger.debug("findAll() SELECT_ALL_TASKS: " + SELECT_ALL_TASKS);

        List<Task> list = new ArrayList<>(INITIAL_CAPACITY);
        ResultSet rs = executeSelect(SELECT_ALL_TASKS);
        try {
            while (rs.next()) {
                Task task = getTask(rs);
                list.add(task);
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            close(rs);
        }
        return list;
    }


    /**
     * The method returns string "(?,?,..,?)" for IN clause which used in prepared statement query:
     * WHERE statusId IN(?,?,?,?,?,?), categoryId IN(?,?), activityId IN(?,?,?)
     */
    private String buildInClause(Set setIds) {
        StringBuilder result = new StringBuilder();
        result.append("(");
        for (int i = 0; i < setIds.size(); i++) {
            result.append("?").append(",");
        }
        result.delete(result.length() - 1, result.length());
        result.append(")");
        return result.toString();
    }

    @Override
    public List<Task> findByFilter(QueryFilter queryFilter) {
        Set<Long> categoriesIds = queryFilter.getIdsFromSubFilter(CATEGORY);
        Set<Long> statusesIds = queryFilter.getIdsFromSubFilter(STATUS);
        Set<Long> activitiesIds = queryFilter.getIdsFromSubFilter(ACTIVITY);
        Set<Long> usersIds = queryFilter.getIdsFromSubFilter(USER);

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM task WHERE categoryId IN ").append(buildInClause(categoriesIds));
        sql.append(" AND statusId IN ").append(buildInClause(statusesIds));
        sql.append(" AND activityId IN ").append(buildInClause(activitiesIds));
        if (usersIds != null) {
            sql.append(" AND userId IN ").append(buildInClause(usersIds));
        }
        sql.append(" AND name LIKE ?");
        sql.append(" ORDER BY ").append(queryFilter.getQueryStringFromSubFilter(ORDER_BY));

        List<Object> paramList = new ArrayList<>(INITIAL_CAPACITY);
        paramList.addAll(categoriesIds);
        paramList.addAll(statusesIds);
        paramList.addAll(activitiesIds);
        if (usersIds != null) {
            paramList.addAll(usersIds);
        }
        paramList.add(queryFilter.getQueryStringFromSubFilter(TASK_NAME));

        List<Task> list = new ArrayList<>(INITIAL_CAPACITY);
        ResultSet rs = executeSelect(sql.toString(), paramList);
        try {
            while (rs.next()) {
                Task task = getTask(rs);
                list.add(task);
            }
            logger.debug("TaskDAO.findByFilter(): " + sql);
        } catch (Exception e) {
            logger.error(e);
        }
        return list;
    }


    @Override
    public Task findById(long id) {
        ResultSet rs = executeSelect(SELECT_TASK_BY_ID, Collections.singletonList(id));

        try {
            if (rs.next()) {
                return getTask(rs);
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            close(rs);
        }
        return null;
    }

    @Override
    public boolean add(Task task) {

        return executeUpdate(INSERT_TASK, Arrays.asList(
                task.getName(),
                task.getContent(),
                task.getDeadline(),
                task.getCategoryId(),
                task.getActivityId(),
                task.getPriority(),
                task.getStatusId(),
                task.getUserId()
        ));
    }

    @Override
    public boolean update(Task task) {
        return executeUpdate(UPDATE_TASK, Arrays.asList(
                task.getName(),
                task.getContent(),
                task.getDeadline(),
                task.getCategoryId(),
                task.getActivityId(),
                task.getPriority(),
                task.getStatusId(),
                task.getUserId(),
                task.getTaskId()
        ));
    }

    @Override
    public boolean delete(long id) {
        return executeUpdate(DELETE_TASK,
                Collections.singletonList(id));
    }
}