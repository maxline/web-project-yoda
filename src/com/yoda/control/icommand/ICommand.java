package com.yoda.control.icommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The Command pattern turns the request itself into an object.
 * <p>
 * The Command interface, declares an interface for executing operations.
 * This interface includes an abstract execute operation. Each concrete Command class
 * specifies a receiver-action pair by storing the Receiver as an instance variable.
 * It provides different implementations of the execute() method to invoke the request.
 * The Receiver has the knowledge required to carry out the request.
 *
 * @author Sergey Mikhluk.
 */
public interface ICommand {

    /**
     * The abstract method execute() simply calls the action on the receiver of the request.
     */
    String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
