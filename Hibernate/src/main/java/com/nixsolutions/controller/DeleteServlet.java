package com.nixsolutions.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.nixsolutions.logic.ApplicationLogic;
import com.nixsolutions.logic.UserLogic;
import com.nixsolutions.util.Path;

public class DeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Logger LOGGER = Logger.getLogger(DeleteServlet.class);
    private ApplicationLogic userLogic = new UserLogic();

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        LOGGER.trace("Servlet start");

        HttpSession session = request.getSession();

        String login = request.getParameter("login");

        LOGGER.debug("Request parameter: login --> " + login);

        userLogic.remove(login);
        
        LOGGER.info("User removed by login --> " + login);

        session.setAttribute("action_command", "User delete command");
        
        
        LOGGER.debug("Redirect to  --> " + request.getContextPath() + Path.STATUS_PAGE);

        response.sendRedirect(request.getContextPath() + Path.STATUS_PAGE);
        
        LOGGER.trace("Servlet finished");


    }

}
