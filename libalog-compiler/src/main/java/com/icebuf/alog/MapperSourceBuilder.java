package com.icebuf.alog;

import java.util.Locale;
import java.util.Map;

/**
 *
 * <p>
 * AppLogTagMapper
 * </p>
 * Description:
 * AppLogTagMapper
 *
 * @author tangjie
 * @version 1.0 on 2020/12/18.
 */
public class MapperSourceBuilder {

    public static final String TEMPLATE_SOURCE = "package %s;\n" +
            "\n" +
            "%s\n" +
            "public class %s {\n" +
            "\n" +
            "\t@Override\n" +
            "\tprotected void internalInit(Map<String, String> tagMap) {\n" +
            "%s\n" +
            "\t}\n" +
            "}";

    public static final String PUT_MAP = "\t\ttagMap.put(\"%s\", \"%s\");\n";


    private String packageName;

    private Class<?>[] imports;

    private String extendName;

    private String[] implNames;

    private String name;

    private Map<String, String> tagMap;

    public MapperSourceBuilder() {

    }

    public MapperSourceBuilder setPackage(String packageName) {
        this.packageName = packageName;
        return this;
    }

    public MapperSourceBuilder setImports(Class<?> ... imports) {
        this.imports = imports;
        return this;
    }

    public MapperSourceBuilder setClassName(String name) {
        this.name = name;
        return this;
    }

    public MapperSourceBuilder setExtendName(String extendName) {
        this.extendName = extendName;
        return this;
    }

    public MapperSourceBuilder setImplNames(Class<?> ... impls) {
        if(impls != null) {
            this.implNames = new String[impls.length];
            for (int i = 0; i < impls.length; i++) {
                Class<?> c = impls[i];
                if(c != null) {
                    implNames[i] = c.getSimpleName();
                }
            }
        }
        return this;
    }

    public MapperSourceBuilder setImplNames(String... implNames) {
        this.implNames = implNames;
        return this;
    }

    public MapperSourceBuilder setTagMap(Map<String, String> tagMap) {
        this.tagMap = tagMap;
        return this;
    }

    public String build() {
        String importStr = getImportString();
        String clazzHead = getClassHead();
        String methodBody = getMethodString();
        return String.format(Locale.getDefault(), TEMPLATE_SOURCE, packageName,
                importStr.toString(), clazzHead, methodBody.toString());
    }

    private String getMethodString() {
        if(tagMap == null || tagMap.isEmpty()) {
            return "";
        }
        StringBuilder methodBody = new StringBuilder();
        for (Map.Entry<String, String> entry : tagMap.entrySet()) {
            methodBody.append(String.format(Locale.getDefault(),
                    PUT_MAP, entry.getKey(), entry.getValue()));
        }
        return methodBody.toString();
    }

    private String getClassHead() {
        StringBuilder clazzHead = new StringBuilder(name);
        if(!Utils.isEmpty(extendName)) {
            clazzHead.append(" extends ").append(extendName);
        }
        if (implNames != null && implNames.length > 0) {
            boolean hasPrefix = false;
            for (String implName : implNames) {
                if (!Utils.isEmpty(implName)) {
                    if (!hasPrefix) {
                        clazzHead.append(" implements ");
                        hasPrefix = true;
                    }
                    clazzHead.append(implName);
                }
            }
        }
        return clazzHead.toString();
    }

    private String getImportString() {
        if (imports == null) {
            return "";
        }
        StringBuilder importStr = new StringBuilder();
        for (Class<?> clazz : imports) {
            if(clazz != null) {
                importStr.append("import ")
                        .append(clazz.getName())
                        .append(";\n");
            }
        }
        return importStr.toString();
    }
}
