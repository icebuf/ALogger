package com.icebuf.alog;


import java.util.HashMap;
import java.util.Map;

/**
 *
 * <p>
 * ALogTagMapper
 * </p>
 * Description:
 * ALogTagMapper
 *
 * @author tangjie
 * @version 1.0 on 2020/12/17.
 */
public class TagMapper {

    private final Map<String, String> tagMap = new HashMap<>();

    private final Map<String, String> pkgMap = new HashMap<>();

    private final Map<String, String> moduleMap = new HashMap<>();

    public void putTag(String key, String tag) {
        tagMap.put(key, tag);
    }

    public String getTag(String key) {
        return tagMap.get(key);
    }

    public void putPackage(String pkgName, String tag) {
        pkgMap.put(pkgName, tag);
    }

    public String getPackageTag(String key) {
        return pkgMap.get(key);
    }

    public void putModule(String moduleName, String tag) {
        moduleMap.put(moduleName, tag);
    }

    public String getModuleTag(String key) {
        return moduleMap.get(key);
    }
}
