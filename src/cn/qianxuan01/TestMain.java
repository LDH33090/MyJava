package cn.qianxuan01;


import cn.qianxuan01.RequestData.HttpRequestPost;

import java.util.ArrayList;

//测试主方法
public class TestMain {
    public static void main(String[] args) {
        //第一步 先创建链接redis方法的对象
        RequestRedis requestRedis = new RequestRedis();
        boolean b = requestRedis.requestRedis();
        System.out.println(b);

        //第二步 创建获取token类的对象，调用获取token的方法
        RedisResponseGetToken redisResponseGetToken = new RedisResponseGetToken();
        ArrayList<String> token = redisResponseGetToken.getToken();
        System.out.println("获取到的token的个数:"+token.size());
        System.out.println("列表的token："+token);


        System.out.println("=======================================================");

        //遍历列表
      /*  Iterator<String> iterator = token.iterator();
        while (iterator.hasNext()){
            String next = iterator.next();
            System.out.println(next);
        }*/
        //第三部分调用接口请求的方法，把获取到的token作为接口的请求头使用
        HttpRequestPost.getRequestMethod(token.size());
    }
}
