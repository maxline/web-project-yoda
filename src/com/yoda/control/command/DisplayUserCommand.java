package com.yoda.control.command;

import com.yoda.config.manager.ConfigManager;
import com.yoda.control.icommand.ICommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.yoda.config.enums.EConfigKey.PAGE_LOGIN;
import static com.yoda.config.enums.EConfigKey.PAGE_MAIN_USER;
import static com.yoda.config.enums.EPageAttribute.USER;

/**
 * @author Sergey Mikhluk.
 */
public class DisplayUserCommand implements ICommand {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page;
        if (request.getSession().getAttribute(USER.name()) == null) {
            page = ConfigManager.getInstance().getProperty(PAGE_LOGIN);
        } else {
            page = ConfigManager.getInstance().getProperty(PAGE_MAIN_USER);
        }
        return page;
    }
}
