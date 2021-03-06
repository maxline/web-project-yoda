package com.yoda.control.command;

import com.yoda.config.manager.ConfigManager;
import com.yoda.control.icommand.ICommand;
import com.yoda.model.entities.Task;
import com.yoda.model.factory.DAOFactory;
import com.yoda.model.idao.ITaskDAO;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static com.yoda.config.enums.EConfigKey.PAGE_MAIN_USER;
import static com.yoda.config.enums.EDictionaryKey.TASK_NOT_UPDATED;
import static com.yoda.config.enums.EDictionaryKey.TASK_UPDATED;
import static com.yoda.config.enums.EPageAttribute.DICTIONARY_BUNDLE;
import static com.yoda.config.enums.EPageAttribute.MESSAGE;
import static com.yoda.config.enums.EPageParameter.*;

/**
 * The command provides updating database record by chosen id for the Task entity.
 *
 * @author Sergey Mikhluk.
 */
public class UpdateTaskCommand implements ICommand {

    private static final Logger logger = Logger.getLogger(UpdateTaskCommand.class.getName());
    private final ITaskDAO taskDAO = DAOFactory.createTaskDAO();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long currentTaskId;
        @SuppressWarnings("unchecked")
        Map<String, String> dictionaryBundle = (Map<String, String>) request.getSession().getAttribute(DICTIONARY_BUNDLE.name());

        try {
            currentTaskId = Long.parseLong(request.getParameter(CHOSEN_ELEMENT_ID.name()));
        } catch (NumberFormatException e) {

            request.getSession().setAttribute(MESSAGE.name(), dictionaryBundle.get(TASK_NOT_UPDATED.name()));
            logger.error(e);
            return ConfigManager.getInstance().getProperty(PAGE_MAIN_USER);
        }
logger.debug("execute request.getParameter(CURRENT_TASK_USER.name()) = " + request.getParameter(CURRENT_TASK_USER.name()));
        Task task = new Task(currentTaskId,
                request.getParameter(CURRENT_TASK_NAME.name()),
                request.getParameter(CURRENT_TASK_CONTENT.name()),
                request.getParameter(CURRENT_TASK_CATEGORY.name()),
                request.getParameter(CURRENT_TASK_ACTIVITY.name()),
                request.getParameter(CURRENT_TASK_PRIORITY.name()),
                request.getParameter(CURRENT_TASK_STATUS.name()),
                request.getParameter(CURRENT_TASK_USER.name()),
                request.getParameter(CURRENT_TASK_DEADLINE.name()));

        if (taskDAO.update(task)) {

            request.getSession().setAttribute(MESSAGE.name(), dictionaryBundle.get(TASK_UPDATED.name()));
        } else {
            request.getSession().setAttribute(MESSAGE.name(), dictionaryBundle.get(TASK_NOT_UPDATED.name()));
        }

        return ConfigManager.getInstance().getProperty(PAGE_MAIN_USER);
    }
}
