package cn.jbit.smbms.util;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期转化类
 */
public class StringToDateConvert implements Converter<String,Date> {

    //时间转化为date格式
    private String datePattern;

    /**
     * 构造函数
     * @param datePattern
     */
    public StringToDateConvert(String datePattern) {
        this.datePattern = datePattern;
    }

    public StringToDateConvert() {
    }

    /**
     * 转化
     * @param s
     * @return
     */
    public Date convert(String s) {
        Date date=null;
        try {
            date=new SimpleDateFormat(datePattern).parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
