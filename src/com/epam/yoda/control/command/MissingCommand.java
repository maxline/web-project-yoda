package com.epam.yoda.control.command;

import com.epam.yoda.control.icommand.ICommand;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Sergey Mikhluk.
 */
public class MissingCommand implements ICommand {
    private static final Logger logger = Logger.getLogger(MissingCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return DisplayHelper.getInstance().definePage(request);
    }
}