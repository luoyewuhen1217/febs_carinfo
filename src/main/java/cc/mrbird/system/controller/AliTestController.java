package cc.mrbird.system.controller;

import cc.mrbird.common.util.pay.PayUtil;

public class AliTestController {
    public static void main(String[] args) {

        String str = PayUtil.alipay("2313131", "0.01", "hehe", "haha");
        System.out.println(str);
    }
}
