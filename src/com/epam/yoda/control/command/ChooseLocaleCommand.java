package com.epam.yoda.control.command;

import com.epam.yoda.config.manager.DictionaryManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.yoda.config.enums.ELocale.EN;
import static com.epam.yoda.config.enums.ELocale.RU;
import static com.epam.yoda.config.enums.EPageAttribute.DICTIONARY_BUNDLE;
import static com.epam.yoda.config.enums.EPageParameter.CHOSEN_LOCALE;

/**
 * @author Sergey Mikhluk.
 */
public class ChooseLocaleCommand implements ICommand {

    private static final Logger logger = Logger.getLogger(ChooseLocaleCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("execute() request.getParameter(CHOSEN_LOCALE.name()) = " + request.getParameter(CHOSEN_LOCALE.name()));
        if (RU.name().equals(request.getParameter(CHOSEN_LOCALE.name()))) {
            request.getSession().setAttribute(DICTIONARY_BUNDLE.name(), DictionaryManager.getInstance().getBundleData(RU));
        } else {
            request.getSession().setAttribute(DICTIONARY_BUNDLE.name(), DictionaryManager.getInstance().getBundleData(EN));
        }
        return DefinePageHelper.definePage(request);
    }
}
