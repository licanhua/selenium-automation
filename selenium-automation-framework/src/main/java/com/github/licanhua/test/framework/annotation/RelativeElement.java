package com.github.licanhua.test.framework.annotation;

import java.lang.annotation.*;

/**
 * @author Canhua Li
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD})
public @interface RelativeElement {
}
