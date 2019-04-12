package com.nixsolutions.listeners;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

public class SessionListener
        implements HttpSessionAttributeListener, HttpSessionListener {

    private static final Logger LOG = Logger.getLogger(SessionListener.class);

    public SessionListener() {
    }

    public void attributeRemoved(HttpSessionBindingEvent event) {
        LOG.debug("Attribute removed. Name --> " + event.getName()
                + ". Value --> " + event.getValue());
    }

    public void attributeAdded(HttpSessionBindingEvent event) {
        LOG.debug("Attribute added. Name --> " + event.getName()
                + ". Value --> " + event.getValue());

    }

    public void sessionCreated(HttpSessionEvent event) {
        LOG.debug("Session created.");
    }

    public void attributeReplaced(HttpSessionBindingEvent event) {
        LOG.debug("Attribute replaced. Name --> " + event.getName()
                + ". Value --> " + event.getValue());
    }

    public void sessionDestroyed(HttpSessionEvent event) {
        LOG.debug("Session Destroyed.");
    }
}
