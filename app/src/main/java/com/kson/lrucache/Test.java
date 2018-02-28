package com.kson.lrucache;

import android.util.Log;
import android.util.LruCache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
public class Test {
    public static void main(String[] args){
//        LinkedHashMap<String,String> map=new LinkedHashMap<String, String>(0, 0.75f, true);
//        map.put("1","1");
//        map.put("2","2");
//        map.put("3","3");
//        map.put("1","4");
//        map.get("2");
//        Iterator<Map.Entry<String, String>> i = map.entrySet().iterator();
//        while (i.hasNext()) {
//            Map.Entry<String, String> e = i.next();
//            System.out.println("Entry:"+e.getKey() + " " + e.getValue());
//        }
        System.out.println("---"+(Integer.MAX_VALUE)+"");
        //
        //因为map.put("1","4")把原来的值覆盖了，不影响链表排序，
//        那么我们来看这个accessOrder开关，默认是false，翻译出来是存取顺序，开启了会发生什么


//        LinkedHashMap<String,Integer> map2=new LinkedHashMap<String, Integer>(0, 0.75f, true);
//        map2.put("语文", 1);
//        map2.put("数学", 2);
//        map2.put("英语", 3);
//        map2.put("历史", 4);
//        map2.put("政治", 5);
//        map2.put("地理", 6);
//        map2.put("生物", 7);
//        map2.put("化学", 8);
//        Iterator<Map.Entry<String, Integer>> i2 = map2.entrySet().iterator();
//        while (i2.hasNext()) {
//            Map.Entry<String, Integer> e = i2.next();
//            System.out.println("Entry:"+e.getKey() + " " + e.getValue());
//        }

//        System.out.println("=========");
//       HashMap<String,Integer> map3=new HashMap<>();
//        map3.put("语文", 1);
//        map3.put("数学", 2);
//        map3.put("英语", 3);
//        map3.put("历史", 4);
//        map3.put("政治", 5);
//        map3.put("地理", 6);
//        map3.put("生物", 7);
//        map3.put("化学", 8);
//        map3.put("化学", 9);
//        Iterator<Map.Entry<String, Integer>> i3 = map3.entrySet().iterator();
//        while (i3.hasNext()) {
//            Map.Entry<String, Integer> e = i3.next();
//            System.out.println("Entry:"+e.getKey() + " " + e.getValue());
//        }
    }

    public synchronized void get(){

    }

    public synchronized static void post(){

    }
}
