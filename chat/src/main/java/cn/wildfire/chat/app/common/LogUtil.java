package cn.wildfire.chat.app.common;

import android.text.TextUtils;
import android.util.Log;

import java.util.Locale;

@SuppressWarnings({"unused", "WeakerAccess"})
public class LogUtil {
    public static final String customTagPrefix = "wayaya";
    private static final boolean isDebug = true;

    public LogUtil() {
    }

    private static String generateTag() {
        StackTraceElement caller = new Throwable().getStackTrace()[2];
        String tag = "%s.%s(Line:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(Locale.CHINA, tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        tag = TextUtils.isEmpty(customTagPrefix) ? tag : customTagPrefix + ":" + tag;
        return tag;
    }

    private static String generateTag(String prefix) {
        StackTraceElement caller = new Throwable().getStackTrace()[2];
        String tag = "%s.%s(Line:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(Locale.CHINA, tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        tag = TextUtils.isEmpty(prefix) ? tag : prefix + ":" + tag;
        return tag;
    }

    public static void d(String content) {
        if (!isDebug) return;
        String tag = generateTag();

        Log.d(tag, content);
    }

    public static void d(String tag, String content) {
        if (!isDebug) return;
        String tag1 = generateTag(tag);

        Log.d(tag1, content);
    }

    public static void d(String content, Throwable tr) {
        if (!isDebug) return;
        String tag = generateTag();

        Log.d(tag, content, tr);
    }

    public static void e(String content) {
        if (!isDebug) return;
        String tag = generateTag();

        Log.e(tag, content);
    }

    public static void e(String content, Throwable tr) {
        if (!isDebug) return;
        String tag = generateTag();

        Log.e(tag, content, tr);
    }

    public static void i(String content) {
        if (!isDebug) return;
        String tag = generateTag();

        Log.i(tag, content);
    }

    public static void i(String content, Throwable tr) {
        if (!isDebug) return;
        String tag = generateTag();

        Log.i(tag, content, tr);
    }

    public static void v(String content) {
        if (!isDebug) return;
        String tag = generateTag();

        Log.v(tag, content);
    }

    public static void v(String content, Throwable tr) {
        if (!isDebug) return;
        String tag = generateTag();

        Log.v(tag, content, tr);
    }

    public static void w(String content) {
        if (!isDebug) return;
        String tag = generateTag();

        Log.w(tag, content);
    }

    public static void w(String content, Throwable tr) {
        if (!isDebug) return;
        String tag = generateTag();

        Log.w(tag, content, tr);
    }

    public static void w(Throwable tr) {
        if (!isDebug) return;
        String tag = generateTag();

        Log.w(tag, tr);
    }

    public static void wtf(String content) {
        if (!isDebug) return;
        String tag = generateTag();

        Log.wtf(tag, content);
    }

    public static void wtf(String content, Throwable tr) {
        if (!isDebug) return;
        String tag = generateTag();

        Log.wtf(tag, content, tr);
    }

    public static void wtf(Throwable tr) {
        if (!isDebug) return;
        String tag = generateTag();

        Log.wtf(tag, tr);
    }


    /**
     * 打印调试日志
     *
     * @param tag     日志标记
     * @param content 打印内容
     * @param args    不确定参数
     */
    public static void printD(String tag, String content, Object... args) {
        Log.d(tag, getContent(content, 4, args));
    }

    /**
     * 测试函数 打印调用栈信息
     *
     * @param content 打印内容
     * @param args    不确定参数
     */
    public static void printDTest(String content, Object... args) {
        for (int i = 0; i < Thread.currentThread().getStackTrace().length; i++) {
            String realContent = getContent(content, i, args);

            // Log.d("default", realContent);
            System.out.println("default:" + realContent);  // 用于单元测试与调试
        }
    }

    /**
     * 获取打印内容
     *
     * @param msg   打印消息
     * @param place 栈索引位置
     * @param args  不确定参数
     * @return 字符串
     */
    private static String getContent(String msg, int place, Object... args) {
        try {
            String sourceLinks = getNameFromTrace(Thread.currentThread().getStackTrace(), place);
            return sourceLinks + String.format(msg, args);
        } catch (Throwable throwable) {
            return msg;
        }
    }

    /**
     * 获取打印格式
     *
     * @param traceElements 栈元素
     * @param place         元素索引位置
     * @return 模板字符串
     */
    private static String getNameFromTrace(StackTraceElement[] traceElements, int place) {
        StringBuilder taskName = new StringBuilder();
        if (traceElements != null && traceElements.length > place) {
            StackTraceElement traceElement = traceElements[place];
            taskName.append(traceElement.getMethodName());
            taskName.append("(").append(traceElement.getFileName()).append(":").append(traceElement.getLineNumber()).append(")");
        }
        return taskName.toString();
    }

}
