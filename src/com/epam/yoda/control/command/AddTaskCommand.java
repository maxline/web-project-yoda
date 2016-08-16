package com.epam.yoda.control.command;

import com.epam.yoda.config.enums.EPageAttribute;
import com.epam.yoda.config.manager.ConfigManager;
import com.epam.yoda.config.manager.DictionaryManager;
import com.epam.yoda.control.icommand.ICommand;
import com.epam.yoda.model.entities.Task;
import com.epam.yoda.model.factory.DAOFactory;
import com.epam.yoda.model.idao.ITaskDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.yoda.config.enums.EConfigKey.PAGE_MAIN_USER;
import static com.epam.yoda.config.enums.EDictionaryKey.TASK_ADDED;
import static com.epam.yoda.config.enums.EDictionaryKey.TASK_NOT_ADDED;
import static com.epam.yoda.config.enums.EPageParameter.*;

/**
 * @author Sergey Mikhluk.
 */
public class AddTaskCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Task task = new Task(-1L,
                request.getParameter(CURRENT_TASK_NAME.name()),
                request.getParameter(CURRENT_TASK_CONTENT.name()),
                request.getParameter(CURRENT_TASK_CATEGORY.name()),
                request.getParameter(CURRENT_TASK_ACTIVITY.name()),
                request.getParameter(CURRENT_TASK_PRIORITY.name()),
                request.getParameter(CURRENT_TASK_STATUS.name()),
                request.getParameter(CURRENT_TASK_DEADLINE.name()));

        ITaskDAO taskDAO = DAOFactory.createTaskDAO();
        if (taskDAO.add(task)) {
            request.getSession().setAttribute(EPageAttribute.MESSAGE.name(), DictionaryManager.getInstance().getProperty(TASK_ADDED));
        } else {
            request.getSession().setAttribute(EPageAttribute.MESSAGE.name(), DictionaryManager.getInstance().getProperty(TASK_NOT_ADDED));
        }

        return ConfigManager.getInstance().getProperty(PAGE_MAIN_USER);
    }
}
