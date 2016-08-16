package com.yoda.control.command;

import com.yoda.config.manager.DictionaryManager;
import com.yoda.control.icommand.ICommand;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.yoda.config.enums.ELocale.en_US;
import static com.yoda.config.enums.ELocale.ru_RU;
import static com.yoda.config.enums.EPageAttribute.DICTIONARY_BUNDLE;
import static com.yoda.config.enums.EPageParameter.CHOSEN_LOCALE;

/**
 * The command provides change of UI language from dictionary bundle.
 *
 * @author Sergey Mikhluk.
 */
public class ChooseLocaleCommand implements ICommand {

    private static final Logger logger = Logger.getLogger(ChooseLocaleCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("execute() request.getParameter(CHOSEN_LOCALE.name()) = " + request.getParameter(CHOSEN_LOCALE.name()));
        if (ru_RU.name().equals(request.getParameter(CHOSEN_LOCALE.name()))) {
            request.getSession().setAttribute(DICTIONARY_BUNDLE.name(), DictionaryManager.getInstance().getBundleData(ru_RU));
        } else {
            request.getSession().setAttribute(DICTIONARY_BUNDLE.name(), DictionaryManager.getInstance().getBundleData(en_US));
        }
        return DisplayHelper.getInstance().definePage(request);
    }
}
