package com.nixsolutions.listeners;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class RequestListener implements ServletRequestListener {

    private static final Logger LOG = Logger.getLogger(RequestListener.class);

    public void requestDestroyed(ServletRequestEvent arg0) {
        // LOG.info("request initialized, request by: " +
        // arg0.getServletRequest().getRemoteAddr());
        // LOG.info("request initialized, servlet context name: " +
        // arg0.getServletContext().getServletContextName());
        // LOG.info("request initialized, info about server: " +
        // arg0.getServletContext().getServerInfo());
        HttpServletRequest req = (HttpServletRequest) arg0.getServletRequest();
        String uri = "request Initialized for " + req.getRequestURI();
        String id = "request Initialized with ID="
                + req.getRequestedSessionId();
        LOG.info(uri + "\n" + id);

    }

    public void requestInitialized(ServletRequestEvent arg0) {
        LOG.info("Request destroyed");

    }

}
