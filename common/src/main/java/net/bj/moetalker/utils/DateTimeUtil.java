package net.bj.moetalker.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 时间工具类
 * Created by Neko-T4 on 2018/1/11.
 */

public class DateTimeUtil {
    public static final SimpleDateFormat FORMAT = new SimpleDateFormat("yy-MM-dd", Locale.ENGLISH);

    /**
     *
     * 获取一个简单的时间字符串
     * @param date Date
     * @return 时间字符串
     */
    public static String getSampleDate(Date date){
        return FORMAT.format(date);
    }

}
