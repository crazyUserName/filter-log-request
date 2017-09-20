package cn.learncoding.util;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by kou on 2017/9/7.
 */
public class JsonUtil {


   public static String toJson(Object object){
      return JSONObject.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
   }

   public static <T> T toObject(String json, Class<T> clazz){
      return JSONObject.parseObject(json, clazz);
   }
}
