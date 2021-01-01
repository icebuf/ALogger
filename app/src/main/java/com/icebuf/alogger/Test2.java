package com.icebuf.alogger;

import com.icebuf.alog.ALog;
import com.icebuf.alog.ALogTag;

/**
 *
 * <p>
 * Test1
 * </p>
 * Description:
 * Test1
 *
 * @author tangjie
 * @version 1.0 on 2020/12/18.
 */
//@GlideModule
@ALogTag("app/Test1")
public class Test2 {

    public static void log() {
        ALog.d(Test2.class.getName());
    }
}
