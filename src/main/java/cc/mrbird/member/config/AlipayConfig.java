package cc.mrbird.member.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileWriter;
import java.io.IOException;

@Configuration
public class AlipayConfig {

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016092000553510";

    // 商户私钥，您的PKCS8格式RSA2私钥
//    public static String merchant_private_key = "AszmHwck5i6dCHapJMMOLQ==";
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCVea3L3Ubxey39m8PoCQdilNCTwhB11eWkYHqGUdQOgpA/ObouzsGFwVEbNZjaoEaX/GJn2N1Amenmgs5KF3UC1wMOS2HhYuq6DwLKC3eeavPz7em5GMJWG6aXex2hd/OS5wuM+9sbxngwRyTNrWFDCK/q5F9MK7BM50fIpgpi/0ysqIdjcT1H7BeZJ1jgf/2XLfwC7W5t9yax9/ZUhoaYtFfAsd2qoiSxr1fuQiHLo/sZbBIOowsIeea8V1SfmSmo+Fa2JX+yTUdsl909OTsNO9ovYFr9neDNrziJfSBZKuK+ByQ4lMkGQ/pqA8eAATI7d1Y/9INwAHCqF5OvmxZFAgMBAAECggEANSmiy6g2zDpAYDvfUI6thr1g9byG+DIMTAtYaJ9/6W08epBAjNdScw0PmLrWU6O/l9zW7xJtlsVnMGoDjsyK/GiWmKXs+SmiTGx8VHoBoGFvxKkwgHmy3MdW1/ec0UoFYpE0RX9qLZXIN3uGNnTc6+eSEdwJnODJAoEbwdx0Q0L10PCd74uNWDDnSKONhMyhpsBQvG88SDWAQFRBuyb8SVcoDYoIO3D9/JFb2tdmGKPj5ycU7YkOvJKjAwPuL7QY4SnDpx4RoDds9+hBoSFlTCMy0JQJ0NGS8/6ep2qy+ngkelUCz+dFzZPND0liTmv+oxA1++AvcJEqkvPhGOVpnQKBgQDwnnXh7O29Q0SQyomIWLYFJx455v3vClozS3ZifYgRQpLNVUnqrRiuiYJfBieXm9HWh0e6sMXe53vSo1ZUeuCeHSYwPOyu8JCXL0zzVV+uOPB93Qm0C0oVXlTyBQdD+IOiblXZ8QeI7nccheL0sWx52s7Em537k1QhOzfCXIDhGwKBgQCfB7livXrNhvaszUoRgFR1q90cXoOa2jg4wlm1f3PZfFuJd6ap+JH6xOL52VcDIT8p+VZYb6rYh/8xPTRBZZJZ6WNS9JtQETVpBdOcmpiPSJj/yEY3qJseuRA+Y8KuiHymMbyiRnl5MdKz4XGKh96tpYt2uNG45G5rkeeanxC8HwKBgFJHPTQWGm1fdTC1sTXIb+VvLOzLygVVWuazZTXkiwSr6RsBkRcIPaphJw1rQ1qaU7Sx6ZszG1ZPfY3XgIu+wNQQT6CrFJv1ctvftsZ2aRNgMQw46zp/qcOI0C85/PQHJKwEYqL1wofWUzCTr7nQebFpWs7sD6eZB2mdoEF79C4FAoGAHFycS2fPhugTBi52jzh716CXBoA0qGFStYyzwxK/7oGnMZUNPDMAleCXOkfwpLZBNZMqOwtvRL68XGO19hPPTgm5lQnaySTIibpoSv2nR6aC4tss6rA/OfN3Wikgh1AAKwOjyK0Y7Oxdz4bdYP9qTz2+6aV8gaT7rGOREppbubECgYEAiFnRSSN4qb6AhiudH7gadtEkQr39KMB9zqAm2UpUqvFIryw2Ql9ovK9EZZRnyCNyJiTk/hUeFFOvL7FJHcTk8EQP1HzLqbumJRkhlUssJq+P9uhBmo9+unk47Vz4Z525GlEvzyLmln3G/Jdjtu4wn9cYgpKo/otGQfety92ocN4=";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArjTbrbnU9xKIPOevR10QqneM4nz7ytn+eZK0hL6FpDgSTTxJive8cKNgaBodo/ZzIRW3pxi/qWwL37z0pVAPKwF67w//91i2MgsV/SyXveSKADmd0GYEOcm6g0WHR4jhCumXC0zdppNq65wWy2phTniMKXXgkLEzDDcf1Sd2P22WPzPMHVILTW0ELPfzN4puQWOdhLfkgyZHPZ80F04m0a6QRm0kknR39NbLcZRv+pUX4HuIMivIjliDo2Unq4dy4vw/xK35RfPJKVCfZ7ArXZGIVlBHZt8MSWjeST3aFg6pM/pxbKzvjOv7a4Y4gTSUa6RshrdR/N4RIE52CZh6PwIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://qiweb.shangyixx.com/aliPay/notifyUrl";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://qiweb.shangyixx.com/aliPay/returnUrl";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "d:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
    @Bean
    AlipayClient alipayClient() {
        return new DefaultAlipayClient
                (gatewayUrl, app_id, merchant_private_key, "json", "utf-8", alipay_public_key, sign_type);
    }


    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

