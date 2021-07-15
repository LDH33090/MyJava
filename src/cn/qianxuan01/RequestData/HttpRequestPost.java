package cn.qianxuan01.RequestData;


import cn.belle.qianxuan01.RedisResponseGetToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

//接口请求类
public class HttpRequestPost extends UrlRequestData {
    //接口的host一样，不同接口的path，设置接口请求的path
    private static String path = "/order-system-lady/o/lady/init";
    //https://show-test.belle.net.cn/order-system-lady/o/lady/init


    public static String getRequestMethod(int tokenSize) {

        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        String str = null;
        Random random = new Random();//创建获取随机数的对象，方便往ArrayList读取token的下标随机获取
        try {
            URL u = new URL(host + path);
            connection = (HttpURLConnection) u.openConnection();
            connection.setRequestProperty("content-type", "application/json;charset=utf-8");
            //把获取到的token作为接口的请求头，获取到的token放在ArrayList中，创建RedisResponseGetToken对象，
            // RedisResponseGetToken对象调用获取token的方法，再根据ArrayList的get(下标)随机获取token
            connection.setRequestProperty("Token", new RedisResponseGetToken().getToken().get(random.nextInt(tokenSize)));
            connection.setRequestMethod("POST");

            str = "{\n" +
                    "\t\"extra_data\": {\n" +
                    "\t\t\"commodityList\": [{\n" +
                    "\t\t\t\"commodityName\": \"Belle/百丽童鞋2015春季新款专柜同款PU粉色女小童运动鞋91567\",\n" +
                    "\t\t\t\"imgPath\": \"null?imageView2/5/w/80/h/80\",\n" +
                    "\t\t\t\"skuId\": \"893\",\n" +
                    "\t\t\t\"skuNum\": 1,\n" +
                    "\t\t\t\"product_id\": \"5546\",\n" +
                    "\t\t\t\"sale_id\": \"293\",\n" +
                    "\t\t\t\"commodityId\": \"20d4865c56d611ea98e56c92bf479df7\",\n" +
                    "\t\t\t\"brandNo\": \"BL01\",\n" +
                    "\t\t\t\"code\": \"BRO91567DK1A35\",\n" +
                    "\t\t\t\"itemNo\": \"20150827044037\"\n" +
                    "\t\t}],\n" +
                    "\t\t\"shareId\": \"216cb60d5ff9406aa22017f5bb6c9ddb\"\n" +
                    "\t}\n" +
                    "}";
              //请求body使用数据流输出
            connection.setDoOutput(true);
            try(OutputStream outputStream = connection.getOutputStream()) {
                outputStream.write(requestData.getBytes("utf-8"));
            }
                 //打印响应码
            int responseCode = connection.getResponseCode();
            System.out.println(responseCode);
            is = connection.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            while ((str = br.readLine()) != null) {
                str = new String(str.getBytes(), "UTF-8");
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            connection.disconnect();
        }
        return str;
    }
}
