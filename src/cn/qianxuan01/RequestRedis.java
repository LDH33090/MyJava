package cn.qianxuan01;

//请求redis的类  RequestRedis继承redis请求链接的参数类 RedisRequestData
public class RequestRedis extends RedisRequestData{

    public boolean requestRedis(){
        jedis.select(databaseIndex); //绑定要查看的库
        //测试是否链接成功
        System.out.println("链接:" + jedis.ping());

        //设定100，表示分页读取中，每页有100条数据
        scanParams.count(1000);
        return true;
    }
}
