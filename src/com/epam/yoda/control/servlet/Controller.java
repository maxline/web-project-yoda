package com.epam.yoda.control.servlet;

import com.epam.yoda.config.manager.ConfigManager;
import com.epam.yoda.config.manager.DictionaryManager;
import com.epam.yoda.control.command.DisplayHelper;
import com.epam.yoda.control.icommand.ICommand;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.yoda.config.enums.EConfigKey.PAGE_MAIN_ADMIN;
import static com.epam.yoda.config.enums.EConfigKey.PAGE_MAIN_USER;
import static com.epam.yoda.config.enums.EDictionaryKey.IO_EXCEPTION;
import static com.epam.yoda.config.enums.EDictionaryKey.SERVLET_EXCEPTION;
import static com.epam.yoda.config.enums.EPageAttribute.MESSAGE;
import static com.epam.yoda.config.enums.EPageAttribute.MESSAGE_ERROR;

/**
 * @author Sergey Mikhluk.
 */
public class Controller extends HttpServlet {

    private static final Logger logger = Logger.getLogger(Controller.class.getName());
    private final ControllerHelper controllerHelper = ControllerHelper.getInstance();

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = null;
        request.getSession().removeAttribute(MESSAGE.name());

        try {
            ICommand command = controllerHelper.getCommand(request);
            page = command.execute(request, response);

            if (page.equals(ConfigManager.getInstance().getProperty(PAGE_MAIN_USER))) {
                DisplayHelper.getInstance().prepareUserPageData(request);
            } else if (page.equals(ConfigManager.getInstance().getProperty(PAGE_MAIN_ADMIN))) {
                DisplayHelper.getInstance().prepareAdminPageData(request);
            }
        } catch (ServletException e) {
            logger.error(e);

            request.getSession().setAttribute(MESSAGE_ERROR.name(), DictionaryManager.getInstance().getProperty(SERVLET_EXCEPTION));
        } catch (IOException e) {
            logger.error(e);
            request.getSession().setAttribute(MESSAGE_ERROR.name(), DictionaryManager.getInstance().getProperty(IO_EXCEPTION));
        }

        logger.debug("processRequest().page = " + page);
        if (page != null) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}