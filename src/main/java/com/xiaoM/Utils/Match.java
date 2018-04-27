package com.xiaoM.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Match {
    public static String getKey(String key){
        String Key = null;
        Pattern r = Pattern.compile("(?<=\\$\\{)(.+?)(?=})");
        Matcher m = r.matcher(key);
        while(m.find()){
            Key = m.group();
        }
        return Key;
    }

    public static List<String> getKeys (String target){
        List<String> keys = new ArrayList<>();
        Pattern r = Pattern.compile("(?<=\\$\\{)(.+?)(?=})");
        Matcher m = r.matcher(target);
        while(m.find()){
            keys.add(m.group());
        }
        return keys;
    }

    public static String replaceKeys (Map<String,Object> returnMap,String target){
        List<String> keys = getKeys(target);
        int keyNum = keys.size();
        for(int i=0;i<keyNum;i++){
            String A = "${"+keys.get(i)+"}";
            String B = returnMap.get(keys.get(i)).toString();
            target =  target.replace(A,B);
        }
        return target;
    }

    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    public static boolean isPictureric(String str){
        if( !str.toLowerCase().contains(".jpg") ){
            return false;
        }
        return true;
    }

    public static String getText(String str_0,String str_1){
        Pattern pattern = Pattern.compile(str_1);
        Matcher matcher = pattern.matcher(str_0);
        if (matcher.find()){
           return matcher.group();
        }
        return null;
    }
}
