package com.yoda.control.command;

import com.yoda.config.manager.ConfigManager;
import com.yoda.config.manager.DictionaryManager;
import com.yoda.control.icommand.ICommand;
import com.yoda.model.factory.DAOFactory;
import com.yoda.model.idao.ITaskDAO;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.yoda.config.enums.EConfigKey.PAGE_MAIN_USER;
import static com.yoda.config.enums.EDictionaryKey.TASK_DELETED;
import static com.yoda.config.enums.EDictionaryKey.TASK_NOT_DELETED;
import static com.yoda.config.enums.EPageAttribute.MESSAGE;
import static com.yoda.config.enums.EPageParameter.CHOSEN_ELEMENT_ID;

/**
 * The command provides deleting database record by chosen id for the Task entity
 *
 * @author Sergey Mikhluk.
 */
public class DeleteTaskCommand implements ICommand {

    private static final Logger logger = Logger.getLogger(DeleteTaskCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long currentTaskId;
        try {
            currentTaskId = Long.parseLong(request.getParameter(CHOSEN_ELEMENT_ID.name()));
            logger.error("execute() request.getParameter(CHOSEN_ELEMENT_ID.name()) = " + request.getParameter(CHOSEN_ELEMENT_ID.name()));
            ITaskDAO taskDAO = DAOFactory.createTaskDAO();
            if (taskDAO.delete(currentTaskId)) {
                request.getSession().setAttribute(MESSAGE.name(), DictionaryManager.getInstance().getProperty(TASK_DELETED));
            } else {
                request.getSession().setAttribute(MESSAGE.name(), DictionaryManager.getInstance().getProperty(TASK_NOT_DELETED));
            }
        } catch (NumberFormatException e) {
            request.getSession().setAttribute(MESSAGE.name(), DictionaryManager.getInstance().getProperty(TASK_NOT_DELETED));
            logger.error("execute() " + e);
        }

        return ConfigManager.getInstance().getProperty(PAGE_MAIN_USER);
    }
}
