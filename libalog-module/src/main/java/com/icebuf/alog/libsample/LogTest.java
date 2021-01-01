package com.icebuf.alog.libsample;

import com.icebuf.alog.ALog;
import com.icebuf.alog.ALogTag;

/**
 *
 * <p>
 * LogTest
 * </p>
 * Description:
 * LogTest
 *
 * @author tangjie
 * @version 1.0 on 2020/12/18.
 */
@ALogTag("/lib/sample")
public class LogTest {

    public static void show() {
        ALog.e("show haha");
    }
}
