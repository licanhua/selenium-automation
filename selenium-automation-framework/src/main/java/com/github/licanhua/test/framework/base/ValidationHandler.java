package com.github.licanhua.test.framework.base;

/**
 * @author Canhua Li
 */
public interface ValidationHandler {
    public void setNextHandler(ValidationHandler handler);
    public void validate();
}
