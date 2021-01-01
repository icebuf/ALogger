package com.icebuf.alog;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * <p>
 * ALogTag
 * </p>
 * Description:
 * ALogTag
 *
 *
 * @author tangjie
 * @version 1.0 on 2020/12/17.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface ALogTag {

    String value();
}
