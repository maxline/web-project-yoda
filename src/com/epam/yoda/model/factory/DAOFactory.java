package com.epam.yoda.model.factory;

import com.epam.yoda.model.dao.*;
import com.epam.yoda.model.idao.*;

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
