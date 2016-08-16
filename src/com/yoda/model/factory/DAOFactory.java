package com.yoda.model.factory;

import com.yoda.model.dao.*;
import com.yoda.model.idao.*;

/**
 * @author Sergey Mikhluk.
 */
public class DAOFactory {

    public static ITaskDAO createTaskDAO() {
        return new TaskDAO();
    }

    public static IActivityDAO createActivityDAO() {
        return new ActivityDAO();
    }

    public static ICategoryDAO createCategoryDAO() {
        return new CategoryDAO();
    }

    public static IStatusDAO createStatusDAO() {
        return new StatusDAO();
    }

    public static IUserTypeDAO createUserTypeDAO() {
        return new UserTypeDAO();
    }

    public static IUserDAO createUserDAO() {
        return new UserDAO();
    }
}
