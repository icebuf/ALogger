package com.icebuf.alog;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;

/**
 * <p>
 * ALog
 * </p>
 * Description:
 * ALog
 *
 * @author tangjie
 * @version 1.0 on 2020/12/17.
 */
public class ALog {

    /**
     * Init.
     *
     * @param context the context
     * @param module  the module
     */
    public static void init(Context context, TagInitializer module) {
        TagRegistry registry = new TagRegistry();
        module.init(registry);
        FormatStrategy strategy = UriFormatStrategy.newBuilder()
                .setMapperRegistry(registry)
                .setAppTag(context.getPackageName())
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(strategy));
    }

    /**
     * Init.
     *
     * @param context  the context
     * @param strategy the strategy
     */
    public static void init(Context context, UriFormatStrategy strategy) {
        Logger.addLogAdapter(new AndroidLogAdapter(strategy));
    }

    /**
     * D.
     *
     * @param message the message
     * @param args    the args
     */
    public static void d(@NonNull String message, @Nullable Object... args) {
        Logger.d(message, args);
    }

    /**
     * D.
     *
     * @param object the object
     */
    public static void d(@Nullable Object object) {
        Logger.d(object);
    }

    /**
     * E.
     *
     * @param message the message
     * @param args    the args
     */
    public static void e(@NonNull String message, @Nullable Object... args) {
        Logger.e(null, message, args);
    }

    /**
     * E.
     *
     * @param throwable the throwable
     * @param message   the message
     * @param args      the args
     */
    public static void e(@Nullable Throwable throwable, @NonNull String message, @Nullable Object... args) {
        Logger.e(throwable, message, args);
    }

    /**
     * .
     *
     * @param message the message
     * @param args    the args
     */
    public static void i(@NonNull String message, @Nullable Object... args) {
        Logger.i(message, args);
    }

    /**
     * V.
     *
     * @param message the message
     * @param args    the args
     */
    public static void v(@NonNull String message, @Nullable Object... args) {
        Logger.v(message, args);
    }

    /**
     * W.
     *
     * @param message the message
     * @param args    the args
     */
    public static void w(@NonNull String message, @Nullable Object... args) {
        Logger.w(message, args);
    }

    /**
     * Tip: Use this for exceptional situations to log
     * ie: Unexpected errors etc
     *
     * @param message the message
     * @param args    the args
     */
    public static void wtf(@NonNull String message, @Nullable Object... args) {
        Logger.wtf(message, args);
    }

    /**
     * Formats the given json content and print it
     *
     * @param json the json
     */
    public static void json(@Nullable String json) {
        Logger.json(json);
    }

    /**
     * Formats the given xml content and print it
     *
     * @param xml the xml
     */
    public static void xml(@Nullable String xml) {
        Logger.xml(xml);
    }


}
