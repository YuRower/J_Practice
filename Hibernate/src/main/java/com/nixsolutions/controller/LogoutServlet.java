package com.nixsolutions.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.nixsolutions.util.Path;

public class LogoutServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(LogoutServlet.class);

    public LogoutServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	
        LOGGER.debug("Servlet start");

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        LOGGER.trace("Forward address --> " + request.getContextPath()
                + Path.WELCOME_PAGE);
        
        response.sendRedirect(request.getContextPath() + Path.WELCOME_PAGE);
        
        LOGGER.debug("Servlet finished");


    }

}
