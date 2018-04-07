package com.cn.controller;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@RestController
public class WeiXinController {

    //这个token是自己随便生成的token
    public static final String TOKEN="gwm0818";

    //验证这个url是否正确
    @RequestMapping("/init")
    public void init(HttpServletRequest request, HttpServletResponse response){
        System.out.println("init...");
        //		timestamp	时间戳
        //		signature	微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
        System.out.println("init...");
        //		nonce	随机数
        //		echostr
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        String[] arrs = {WeiXinController.TOKEN,nonce,timestamp};
        Arrays.sort(arrs);
        StringBuffer sb = new StringBuffer();
        for(String a:arrs) {
            sb.append(a);
        }
        String sha1 = Sha1Degist.getString(sb.toString());
        System.out.println(sha1.equals(signature));
        if(sha1.equals(signature)) {
            try {
                response.getWriter().println(echostr);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping("/weixin")
    public String getWeiXin(){
        System.out.println("weixin...");

        CloseableHttpClient httpclient = HttpClients.createDefault();
        String url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx85933c449364be86&secret=3a6431e7845340dc71e2d315f6756cf9";
        String url2="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx85933c449364be86&redirect_uri=http://gongwm.free.ngrok.cc/callBack&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
        HttpGet httpGet = new HttpGet(url2);
        try {
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            int status=response1.getStatusLine().getStatusCode();
            System.out.println();
            //状态码在200到300之间包括便捷
            if(status>=200 && status<=300){
                HttpEntity entity = response1.getEntity();
                //这里需要借用EntityUtils.toString(Entity)
                String result = EntityUtils.toString(entity);
                System.out.println("result="+result);
                response1.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "hellow world!";
    }

    @RequestMapping("/callBack")
    public String callBack(){
        System.out.println("callBackcallBackcallBack...");
        System.out.println("callBackcallBackcallBack...");
        return "callBack";
    }
}
