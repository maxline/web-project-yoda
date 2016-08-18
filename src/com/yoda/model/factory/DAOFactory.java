package com.yoda.model.factory;

import com.yoda.model.dao.*;
import com.yoda.model.idao.*;

/**
 * @author Sergey Mikhluk.
 */
public class DAOFactory {

    public static ITaskDAO createTaskDAO() {
        return TaskDAO.getInstance();
    }

    public static IActivityDAO createActivityDAO() {
        return new ActivityDAO();
    }

    public static ICategoryDAO createCategoryDAO() {
        return CategoryDAO.getInstance();
    }

    public static IStatusDAO createStatusDAO() {
        return StatusDAO.getInstance();
    }

    public static IUserTypeDAO createUserTypeDAO() {
        return UserTypeDAO.getInstance();
    }

    public static IUserDAO createUserDAO() {
        return UserDAO.getInstance();
    }
}