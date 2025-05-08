package top.yuxs.resourcelibrarysystem;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;

public class Hutool {
    public static void main(String[] args) {
        String result1= HttpUtil.get("/html");
        String now = DateUtil.now();
        System.out.println(now);
    }
}
