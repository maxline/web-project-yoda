package com.yoda.control.servlet;

import com.yoda.control.command.*;
import com.yoda.control.icommand.ICommand;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

import static com.yoda.config.enums.ECommand.*;
import static com.yoda.config.enums.EPageParameter.COMMAND;

/**
 * @author Sergey Mikhluk.
 */
class ControllerHelper {
    private static final Logger logger = Logger.getLogger(ControllerHelper.class.getName());
    private static volatile ControllerHelper instance;
    private final HashMap<String, ICommand> commands = new HashMap<>();

    private ControllerHelper() {
        commands.put(LOGIN.name(), new LoginCommand());
        commands.put(LOGOUT.name(), new LogoutCommand());
        commands.put(DISPLAY_USER.name(), new DisplayUserCommand());
        commands.put(DISPLAY_ADMIN.name(), new DisplayAdminCommand());
        commands.put(CHOOSE_LOCALE.name(), new ChooseLocaleCommand());
        commands.put(ADD_TASK.name(), new AddTaskCommand());
        commands.put(DELETE_TASK.name(), new DeleteTaskCommand());
        commands.put(UPDATE_TASK.name(), new UpdateTaskCommand());
        commands.put(ADD_ELEMENT.name(), new AddElementCommand());
        commands.put(UPDATE_ELEMENT.name(), new UpdateElementCommand());
        commands.put(DELETE_ELEMENT.name(), new DeleteElementCommand());
    }

    ICommand getCommand(HttpServletRequest request) {
        ICommand command = commands.get(request.getParameter(COMMAND.name()));
        logger.debug("getCommand() getParameter(COMMAND.name()): " + request.getParameter(COMMAND.name()));

        if (command == null) {
            command = new MissingCommand();
        }
        return command;
    }

    public static ControllerHelper getInstance() {
        ControllerHelper localInstance = instance;
        if (localInstance == null) {
            synchronized (ControllerHelper.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ControllerHelper();
                }
            }
        }
        return localInstance;
    }
}