package com.yoda.control.command;

import com.yoda.control.icommand.ICommand;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * When the Controller was invoked without any command (e.g. for the first time application was started)
 * the MissingCommand is invoked.
 *
 * @author Sergey Mikhluk.
 */
public class MissingCommand implements ICommand {
    private static final Logger logger = Logger.getLogger(MissingCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return DisplayHelper.getInstance().definePage(request);
    }
}