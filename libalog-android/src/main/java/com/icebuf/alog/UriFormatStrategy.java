package com.icebuf.alog;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogStrategy;
import com.orhanobut.logger.LogcatLogStrategy;

import java.util.Iterator;
import java.util.Objects;


/**
 * <p>
 * RouteFormatStrategy
 * </p>
 * Description:
 *
 * <ul>
 *   <li>AppTag: 日志标签，长度应小于20字符，推荐应用名/应用ID/包名</li>
 *   <li>UriPath: 当前日志所属模块，推荐使用路径Uri的方式表示，方便过滤显示</li>
 *   <li>ThreadName: 当前线程名</li>
 *   <li>SimpleClassName: 类名简写</li>
 *   <li>method: 方法名</li>
 * </ul>
 *
 * <pre>
 *     AppTag:UriPath:[ThreadName]SimpleClassName.method():: Log message
 * </pre>
 *
 * @author tangjie
 * @version 1.0 on 2020/12/17.
 */
public class UriFormatStrategy implements FormatStrategy {

    private final String appTag;

    private final boolean isShowThreadName;

    private final LogStrategy logStrategy;

    private final TagRegistry registry;

    private UriFormatStrategy(@NonNull Builder builder) {
        this.appTag = builder.appTag;
        this.isShowThreadName = builder.isShowThreadName;
        this.logStrategy = builder.logStrategy;
        this.registry = builder.registry;
    }

    public static UriFormatStrategy.Builder newBuilder() {
        return new UriFormatStrategy.Builder();
    }

    @Override
    public void log(int priority, @Nullable String tag, @NonNull String message) {
        Objects.requireNonNull(message);
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        int offset = getStackOffset(trace);
        StackTraceElement element = trace[offset];
        String simpleClassName = getSimpleClassName(element.getClassName());
        String methodName = element.getMethodName();
        String uriPath = appTag + getPath(tag, element.getClassName());

        StringBuilder builder = new StringBuilder();
        builder.append(uriPath).append(": ");
        if(isShowThreadName) {
            builder.append('[')
                    .append(Thread.currentThread().getName())
                    .append(']');
        }
        builder.append(simpleClassName)
                .append(".")
                .append(methodName)
                .append("():: ")
                .append(message);
        logStrategy.log(priority, appTag, builder.toString());
    }

    private String getPath(String tag, String className) {
        if (TextUtils.isEmpty(className)) {
            return tag;
        }
        if (registry != null) {
            for (Iterator<TagMapper> it = registry.iterator(); it.hasNext(); ) {
                TagMapper map = it.next();
                tag = map.getTag(className);
                if (!TextUtils.isEmpty(tag)) {
                    return tag;
                }
            }
        }
        return getPackagePath(className);
    }

    private String getPackagePath(String className) {
        int index = className.lastIndexOf('.');
        if(index > 0) {
            return className.substring(0, index)
                    .replace(".", "/");
        }
        return className.replace(".", "/");
    }

    private String getSimpleClassName(String className) {
        int index = className.lastIndexOf('.');
        if(index >= 0 && index < className.length()) {
            return className.substring(index + 1);
        }
        return className;
    }

    /**
     * Determines the starting index of the stack trace, after method calls made by this class.
     *
     * @param trace the stack trace
     * @return the stack offset
     */
    private int getStackOffset(@NonNull StackTraceElement[] trace) {
        Objects.requireNonNull(trace);
        for (int i = 0; i < trace.length; i++) {
            StackTraceElement e = trace[i];
            String name = e.getClassName();
            if(name.equals(ALog.class.getName())) {
                return ++i;
            }
        }
        return -1;
    }

    public static class Builder {

        private LogStrategy logStrategy;
        private boolean isShowThreadName = true;
        private String appTag = BuildConfig.LIBRARY_PACKAGE_NAME;
        private TagRegistry registry;

        public Builder setLogStrategy(LogStrategy logStrategy) {
            this.logStrategy = logStrategy;
            return this;
        }

        public Builder setShowThreadName(boolean showThreadName) {
            isShowThreadName = showThreadName;
            return this;
        }

        public Builder setAppTag(String appTag) {
            this.appTag = appTag;
            return this;
        }

        public FormatStrategy build() {
            if(logStrategy == null) {
                logStrategy = new LogcatLogStrategy();
            }
            return new UriFormatStrategy(this);
        }

        public Builder setMapperRegistry(TagRegistry registry) {
            this.registry = registry;
            return this;
        }
    }
}
