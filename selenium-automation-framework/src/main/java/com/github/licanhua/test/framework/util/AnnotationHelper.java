package com.github.licanhua.test.framework.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author Canhua Li
 */
public class AnnotationHelper {
    public static<T> T getAnnotationDefault(Class<? extends Annotation> annotationClass, String element) throws Exception {
        Method method = annotationClass.getMethod(element);
        return((T)method.getDefaultValue());
    }
}
