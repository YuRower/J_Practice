package com.nixsolutions.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.nixsolutions.entity.User;
import com.nixsolutions.logic.ApplicationLogic;
import com.nixsolutions.logic.UserLogic;
import com.nixsolutions.util.Path;
import com.nixsolutions.util.UserFormProccess;

public class UpdateUserServlet extends HttpServlet {
    private static final Logger LOGGER = Logger
            .getLogger(UpdateUserServlet.class);
    private static final long serialVersionUID = 1L;
    private ApplicationLogic userLogic = new UserLogic();

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        LOGGER.debug("Servlet start");

        HttpSession session = request.getSession();

        User user = UserFormProccess.proccessForm(request);

        LOGGER.info("User login --> " + user.getLogin());

        userLogic.update(user);

        LOGGER.debug("Updated user --> " + user);


        session.setAttribute("action_command", "Update command");
        
        LOGGER.debug("Redirect to  --> " + request.getContextPath() + Path.STATUS_PAGE);

        response.sendRedirect(request.getContextPath() + Path.STATUS_PAGE);
        
        LOGGER.debug("Servlet  finished");


    }
}
