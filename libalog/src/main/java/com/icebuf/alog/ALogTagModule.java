package com.icebuf.alog;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * ALogTagModule2
 * </p>
 * Description: ALogTagModule2
 * <p>
 * E-mail：bflyff@hotmail.com
 *
 * @author tangjie
 * @version 1.0 on 2020/12/26
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface ALogTagModule {

    String value();
}
