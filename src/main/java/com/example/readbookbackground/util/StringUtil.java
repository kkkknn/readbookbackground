package com.example.readbookbackground.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    // 字符串SQL注入检测
    public static boolean containsSqlInjection(String str){
        if(str==null||str.isEmpty()){
            return false;
        }
        Pattern pattern= Pattern.compile("\\b(and|exec|insert|select|drop|grant|alter|delete|update|count|chr|mid|master|truncate|char|declare|or)\\b|(\\*|;|\\+|'|%)");
        Matcher matcher=pattern.matcher(str.toLowerCase());
        return matcher.find();
    }

    //Unicode转中文方法
    public static String unicodeToCn(String unicode) {
        /** 以 \ u 分割，因为java注释也能识别unicode，因此中间加了一个空格*/
        String[] strs = unicode.split("\\\\u");
        String returnStr = "";
        // 由于unicode字符串以 \ u 开头，因此分割出的第一个字符是""。
        for (int i = 1; i < strs.length; i++) {
            returnStr += (char) Integer.valueOf(strs[i], 16).intValue();
        }
        return returnStr;
    }
}
