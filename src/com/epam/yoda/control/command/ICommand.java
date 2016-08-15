/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.yoda.control.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Sergey Mikhluk.
 */
public interface ICommand {

    String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
