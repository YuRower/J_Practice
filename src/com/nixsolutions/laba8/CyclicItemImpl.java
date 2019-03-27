package com.nixsolutions.laba8;

import java.io.Serializable;

import interfaces.task8.CyclicItem;

public class CyclicItemImpl implements CyclicItem, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -365445161587801857L;
    private Object value;
    private transient Object temp;
    private CyclicItem next;

    public CyclicItemImpl(Object value, Object temp) {
        this.value = value;
        this.temp = temp;

    }

    public CyclicItemImpl(Object value) {
        this.value = value;

    }

    public CyclicItemImpl() {
        next = this;
    }

    @Override
    public Object getTemp() {
        return temp;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public CyclicItem nextItem() {
        return next;
    }

    @Override
    public void setNextItem(CyclicItem next) {
        this.next = next;
    }

    @Override
    public void setTemp(Object temp) {
        this.temp = temp;
    }

    @Override
    public void setValue(Object value) {
        this.value = value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof CyclicItemImpl)) {
            return false;
        }
        CyclicItemImpl other = (CyclicItemImpl) obj;
        if (value == null) {
            if (other.value != null) {
                return false;
            }
        } else if (!value.equals(other.value)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        if (temp != null) {
            return value + " - " + temp;
        } else {
            return value + " ";
        }
    }

}
