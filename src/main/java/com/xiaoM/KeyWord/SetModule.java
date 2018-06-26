package com.xiaoM.KeyWord;

import com.xiaoM.Utils.Location;
import com.xiaoM.Utils.Log;

import java.util.Map;

public class SetModule {
    private Log log = new Log(this.getClass());

    public boolean setPsaram(String TestCategory, Map<String, Object> returnMap, Location location) {
        String key = location.getValue();
        String value = location.getParameter();
        returnMap.put(key, value);
        log.info(TestCategory + "：赋值 [ 参数：" + key + " 值：" + value + " ]");
        return true;
    }
}
