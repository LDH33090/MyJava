package cn.qianxuan01;

import redis.clients.jedis.ScanResult;

import java.util.ArrayList;
import java.util.List;

//redis获取token类 继承了redis链接所用数据类
public class RedisResponseGetToken extends RedisRequestData {

    //设定长度为100的数组存放在redis中获取到的token
    //public static String[] tokenArray = new String[100];
     ArrayList<String> tokenArray = new ArrayList<>(100);

    //redis请求成功后，获取token的方法
    public ArrayList<String> getToken() {
        //创建读取token的对象

      /*  //创建redis请求链接的类，获取token需要先链接redis
        RequestRedis requestRedis = new RequestRedis();
        //调用链接redis请求链接的方法
        requestRedis.requestRedis();*/
        jedis.select(databaseIndex); //绑定要查看的库
        //测试是否链接成功
        System.out.println("链接:" + jedis.ping());

        //设定100，表示分页读取中，每页有1000条数据
        scanParams.count(1000);

        String oldStr;
        String token = null;

        while (true){
            //使用scan命令获取数据，使用cursor游标记录位置，下次循环使用
            ScanResult<String> scanResult = jedis.scan(cursor, scanParams);
            cursor = scanResult.getStringCursor();// 返回0 说明遍历完成
            List<String> list = scanResult.getResult();
            long t1 = System.currentTimeMillis();
            for(int m = 0;m < list.size();m++){
                if (list.get(m).contains(selectKey)) {
                    oldStr = list.get(m);
                    //获取到的token，redis使用String类型 = key:value，把所有字符串中包含lady:user:info:的字符换成成空字符
                    token = oldStr.replace("lady:user:info:", "");
                    //System.out.println("拿到第：" + m + "个token信息,::::::" + token);
                    if (token.length() != 0 && tokenArray.size() <=100)  {
                        tokenArray.add(token);
                    }
                }

            }
          /*  long t2 = System.currentTimeMillis();
            System.out.println("获取" + list.size()
                    + "条数据，耗时: " + (t2-t1) + "毫秒,cursor:" + cursor);*/
            if ("0".equals(cursor)){
                break;
            }
        }

       /* System.out.println("数组长度"+tokenArray.size());
        for (int i = 0; i < tokenArray.size(); i++) {
            System.out.println("数组第："+i+"个token：：：："+tokenArray.get(i));
        }*/
            return  tokenArray;
    }
}
