package com.nixsolutions.laba11;

import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.LogManager;
import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nixsolutions.laba5.ArrayCollectionImpl;

import interfaces.logging.LoggingArrayCollection;

public class LoggingArrayCollectionImpl<E> extends ArrayCollectionImpl<E>
        implements LoggingArrayCollection<E> {
    static {
        new DOMConfigurator().doConfigure(
                "src/com/nixsolutions/laba11/log4j.xml",
                LogManager.getLoggerRepository());
    }
    private static final Logger LOG = LoggerFactory
            .getLogger(LoggingArrayCollectionImpl.class);

    @Override
    public Object[] getArray() {
        LOG.trace("method getArray invocation");
        return super.getArray();
    }

    @Override
    public void setArray(E[] arg0) {
        LOG.trace("method getArray invocation");
        try {
            super.setArray(arg0);
        } catch (Exception ex) {
            LOG.error("error occured in setArray method", ex);
            throw ex;
        }
    }

    @Override
    public int size() {
        LOG.trace("method size invocation");
        return super.size();

    }

    @Override
    public boolean isEmpty() {
        LOG.trace("method isEmpty invocation");
        return super.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        LOG.trace("method contains invocation");
        return super.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        LOG.trace("method iterator invocation");
        return super.iterator();
    }

    @Override
    public Object[] toArray() {
        LOG.trace("method toArray invocation");
        return super.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        LOG.trace("typed method toArray invocation");
        return super.toArray(a);
    }

    @Override
    public boolean add(E e) {
        LOG.trace("method add invocation");
        return super.add(e);
    }

    @Override
    public boolean remove(Object o) {
        LOG.trace("method remove invocation");
        return super.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        LOG.trace("method containsAll invocation");
        try {
            return super.containsAll(c);
        } catch (Exception ex) {
            LOG.error("error occured in containsAll method", ex);
            throw ex;
        }
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        LOG.trace("method addAll invocation");
        try {
            return super.addAll(c);
        } catch (Exception ex) {
            LOG.error("error occured in addAll method", ex);
            throw ex;
        }
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        LOG.trace("method removeAll invocation");
        try {
            return super.removeAll(c);
        } catch (Exception ex) {
            LOG.error("error occured in removeAll method", ex);
            throw ex;
        }
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        LOG.trace("method retainAll invocation");
        try {
            return super.retainAll(c);
        } catch (Exception ex) {
            LOG.error("error occured in retainAll method", ex);
            throw ex;
        }
    }

    @Override
    public void clear() {
        LOG.trace("method clear invocation");
        super.clear();
    }

    @Override
    public Logger getLogger() {
        return LOG;
    }

}
