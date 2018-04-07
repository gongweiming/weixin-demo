package com.cn.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha1Degist {

    public static String getString(String str){

        //首先需要一个容器装在所有字符转化成的字节数组
        StringBuffer sb=new StringBuffer();

        //使sha1的加密方式
        try {
            //这里也可以换成MD5加密
            MessageDigest digest=MessageDigest.getInstance("sha1");
            //调用这个update方法每次更新这个编译
            digest.update(str.getBytes());

            //调用digest转化成功字节嘛
            byte[] msg = digest.digest();

            //讲所有的字符转化成字节码
            for(byte b:msg){
                //讲字节转化为16格式
                sb.append(String.format("%02x",b));
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(getString("gongweiming"));
    }
}
