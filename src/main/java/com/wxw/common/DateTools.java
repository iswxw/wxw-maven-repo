package com.wxw.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @desp 日期工具类
 * @author: wxw
 * @date: 2020-12-10-22:53
 */
public class DateTools {

    public String Date2String(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        return dateFormat.format(date);
    }
}
