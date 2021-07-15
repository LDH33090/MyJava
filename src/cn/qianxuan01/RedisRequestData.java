package cn.qianxuan01;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;

//redis请求链接所用到的数据类
public class RedisRequestData {
    public static final String host = "10.234.6.43";  //redis服务器地址

    public static final int port = 6379; //redis服务器端口号

    public static final int databaseIndex =1 ;   //查找索引是1的库

    public static String cursor = ScanParams.SCAN_POINTER_START;   // 设置游标默认值是0

    //设定需要查找key所对应的value，value也就是所对应的token
    public static String selectKey = "lady:user:info:";

    //创建redis对象
    public Jedis jedis = new Jedis(host,port);

    ScanParams scanParams = new ScanParams();

}
