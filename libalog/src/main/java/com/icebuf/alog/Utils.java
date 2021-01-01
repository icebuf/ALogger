package com.icebuf.alog;

/**
 *
 * <p>
 * Utils
 * </p>
 * Description:
 * Utils
 *
 * @author tangjie
 * @version 1.0 on 2020/12/18.
 */
class Utils {

    public static String getSimpleName(String className) {
        int index = className.lastIndexOf('.');
        if(index >= 0 && index < className.length()) {
            return className.substring(index + 1);
        }
        return className;
    }

    public static String getPackageName(String className) {
        int index = className.lastIndexOf('.');
        if(index > 0) {
            return className.substring(0, index);
        }
        return "";
    }

    public static boolean isEmpty(String text) {
        return text == null || "".equals(text);
    }
}
