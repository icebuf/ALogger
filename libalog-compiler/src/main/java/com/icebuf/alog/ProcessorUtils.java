package com.icebuf.alog;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

/**
 *
 * <p>
 * ProcessorUtils
 * </p>
 * Description:
 * ProcessorUtils
 *
 * @author tangjie
 * @version 1.0 on 2020/12/18.
 */
public class ProcessorUtils {

    public static String getEnvPackageName(RoundEnvironment roundEnv) {
        Set<? extends Element> rootElements = roundEnv.getRootElements();
        for (Element element : rootElements) {
            if (element.getSimpleName().contentEquals("BuildConfig")
                    && (element instanceof TypeElement)) {
                TypeElement typeElement = (TypeElement) element;
                String pkgName = getConstantString(typeElement, "APPLICATION_ID");
                if(Utils.isEmpty(pkgName)) {
                    pkgName = getConstantString(typeElement, "LIBRARY_PACKAGE_NAME");
                }
                return pkgName;
            }
        }
        return "";
    }

    public static String getConstantString(TypeElement typeElement, String simpleName) {
        Object obj = getConstantValue(typeElement, simpleName);
        if(obj instanceof String) {
            return (String) obj;
        }
        return "";
    }

    public static Object getConstantValue(TypeElement typeElement, String simpleName) {
        List<? extends Element> enclosedElements = typeElement.getEnclosedElements();
        for (Element element : enclosedElements) {
            if(element.getSimpleName().contentEquals(simpleName)
                    && (element instanceof VariableElement)) {
                VariableElement variableElement = (VariableElement) element;
                if(variableElement.getSimpleName().contentEquals(simpleName)) {
                    return variableElement.getConstantValue();
                }
            }
        }
        return null;
    }

    public static void close(Closeable ... closeables) {
        if(closeables != null) {
            for (Closeable c : closeables) {
                if(c != null) {
                    try {
                        c.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static String firstCharUpper(String str) {
        // 进行字母的ascii编码前移，效率要高于截取字符串进行转换的操作
        char[] cs = str.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }

    public static String getModuleName(ProcessingEnvironment env, String key) {
        String moduleName = env.getOptions().get(key);
        if(moduleName == null) {
            return "";
        }
        return moduleName.replace("-", "_");
    }
}
