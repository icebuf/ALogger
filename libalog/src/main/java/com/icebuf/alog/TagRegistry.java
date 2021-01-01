package com.icebuf.alog;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * <p>
 * ALogTagRegistery
 * </p>
 * Description:
 * ALogTagRegistery
 *
 * @author tangjie
 * @version 1.0 on 2020/12/21.
 */
public class TagRegistry {

    private final Set<TagMapper> mapperMap = new HashSet<>();

    public void register(TagMapper mapper) {
        mapperMap.add(mapper);
    }

    public void unregister(TagMapper mapper) {
        mapperMap.remove(mapper);
    }


    public Iterator<TagMapper> iterator() {
        return mapperMap.iterator();
    }
}
