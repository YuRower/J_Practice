package com.nixsolutions.controller;

import java.io.IOException;
import java.util.List;

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

public class UpdateUserListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(UpdateUserListServlet.class);

    ApplicationLogic userLogic = new UserLogic();

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        LOGGER.trace("Command start");

        HttpSession session = request.getSession();
        
        List<User> userList = userLogic.findAll();
        
        session.setAttribute("userList", userList);

        LOGGER.trace("Set the session attribute: userList --> " + userList);

        request.getRequestDispatcher(Path.ADMIN_PAGE).forward(request,
                response);
        
        LOGGER.trace("Forward address --> " + Path.ADMIN_PAGE);
        
        LOGGER.trace("Command finish");


    }

}
