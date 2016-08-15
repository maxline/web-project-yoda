package com.epam.yoda.control.command;

import com.epam.yoda.model.entities.User;

import javax.servlet.http.HttpServletRequest;

import static com.epam.yoda.config.enums.EConfigKey.PAGE_MAIN_ADMIN;
import static com.epam.yoda.config.enums.EConfigKey.PAGE_MAIN_USER;
import static com.epam.yoda.config.enums.EPageAttribute.USER;
import static com.epam.yoda.config.manager.ConfigManager.getInstance;
import static com.epam.yoda.control.command.LoginCommand.ADMIN_USER_TYPE_ID;

/**
 * @author Sergey Mikhluk.
 */
public class DefinePageHelper {
    public static String definePage(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(USER.name());

        return user.getUserTypeId() == ADMIN_USER_TYPE_ID ?
                getInstance().getProperty(PAGE_MAIN_ADMIN) :
                getInstance().getProperty(PAGE_MAIN_USER);
    }
}
